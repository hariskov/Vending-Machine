package bg.petarh.vending.repository;

import java.util.List;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.entities.ProductInventory;

public interface ProductRepository {

    List<Product> findAvailableProducts();

    List<ProductInventory> findAllProductInventory();

    List<ProductInventory> findAllAvailableProductInventory();

    boolean hasProductQuantity(Product product);
}
