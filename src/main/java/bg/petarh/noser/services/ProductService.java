package bg.petarh.noser.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import bg.petarh.noser.entities.Product;
import bg.petarh.noser.entities.ProductInventory;
import bg.petarh.noser.repository.ProductRepository;

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
