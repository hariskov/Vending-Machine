package bg.petarh.vending.repository;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.entities.ProductInventory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class that substitutes calls to the database with ones returning predefined data
 */
@Repository
public class FakeProductRepository implements ProductRepository {

    private final FakeProductProvider fakeProductProvider = new FakeProductProvider();

    @Override
    public List<Product> findAvailableProducts() {
        return this.findAllProductInventory().stream()
                .filter(productInventory -> productInventory.getQuantity() > 0)
                .map(ProductInventory::getProduct)
                .toList();
    }

    @Override
    public List<ProductInventory> findAllProductInventory() {
        return fakeProductProvider.findAllInventory();
    }

    @Override
    public List<ProductInventory> findAllAvailableProductInventory() {
        return this.findAllProductInventory()
                .stream()
                .filter(productInventory -> productInventory.getQuantity() > 0)
                .toList();
    }

    @Override
    public ProductInventory getProductInventory(Product p) {
        return this.findAllProductInventory()
                .stream()
                .filter(productInventory -> productInventory.getProduct().equals(p))
                .findFirst().orElse(null); //null here is to simulate nothing found (never return null in practice!)
    }

    @Override
    public boolean hasProductQuantity(Product product) {
        return this.findAvailableProducts().contains(product);
    }

    @Override
    public void saveOrUpdateProductInventory(ProductInventory inventory) {
        ProductInventory dbInventory = fakeProductProvider.findAllInventory()
                .stream()
                .filter(productInventory -> productInventory.equals(inventory))
                .findFirst()
                .orElse(null);
        if (dbInventory != null) {
            dbInventory.setQuantity(inventory.getQuantity());
        } else {
            fakeProductProvider.findAllInventory().add(inventory);
        }
    }

    @Override
    public void updateProductInventory(ProductInventory inventory) {
        ProductInventory dbInventory = fakeProductProvider.findAllInventory()
                .stream()
                .filter(productInventory -> productInventory.equals(inventory))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("product inventory not found !"));

        dbInventory.setQuantity(inventory.getQuantity());
    }
}
