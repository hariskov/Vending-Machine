package bg.petarh.vending.repository;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.entities.ProductInventory;

import java.util.List;

public interface ProductRepository {

    List<Product> findAvailableProducts();

    List<ProductInventory> findAllProductInventory();

    List<ProductInventory> findAllAvailableProductInventory();

    ProductInventory getProductInventory(Product product);

    boolean hasProductQuantity(Product product);

    void saveOrUpdateProductInventory(ProductInventory inventory);

    void updateProductInventory(ProductInventory inventory);
}
