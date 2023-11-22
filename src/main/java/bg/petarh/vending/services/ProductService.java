package bg.petarh.vending.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.entities.ProductInventory;
import bg.petarh.vending.repository.ProductRepository;

@Service
public class ProductService {

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

}
