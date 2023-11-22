package bg.petarh.noser.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import bg.petarh.noser.entities.Product;
import bg.petarh.noser.entities.ProductInventory;

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
    public List<ProductInventory> findAllAvailableProductInventory(){
        return this.findAllProductInventory()
                .stream()
                .filter(productInventory -> productInventory.getQuantity() > 0)
                .toList();
    }

    @Override
    public boolean hasProductQuantity(Product product) {
        return this.findAvailableProducts().contains(product);
    }
}
