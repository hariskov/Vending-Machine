package bg.petarh.noser.repository;

import java.util.List;

import bg.petarh.noser.entities.Product;
import bg.petarh.noser.entities.ProductInventory;

public interface ProductRepository {

    List<Product> findAvailableProducts();

    List<ProductInventory> findAllProductInventory();

    List<ProductInventory> findAllAvailableProductInventory();

    boolean hasProductQuantity(Product product);
}
