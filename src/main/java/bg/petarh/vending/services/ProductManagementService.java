package bg.petarh.vending.services;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.entities.ProductInventory;
import bg.petarh.vending.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides descriptive(non-operative) information about the products. <br/>
 * For managing products(operative) see {@link ProductOperationService}
 */
@Service
public class ProductManagementService {

    @Autowired
    private ProductRepository productRepository;

    public boolean isProductAvailable(Product product) {
        return productRepository.hasProductQuantity(product);
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findAvailableProducts();
    }

    public List<ProductInventory> getAllAvailableProductInventory() {
        return productRepository.findAllAvailableProductInventory();
    }

    public ProductInventory getInventoryForProduct(Product product) {
        if (!this.isProductAvailable(product)) {
            throw new RuntimeException("Product not found ?!");
            // log error
        }
        return this.productRepository.getProductInventory(product);
    }

}
