package bg.petarh.vending.services;


import bg.petarh.vending.entities.Product;
import bg.petarh.vending.entities.ProductInventory;
import bg.petarh.vending.repository.ProductRepository;
import bg.petarh.vending.services.limits.LimitChecker;
import bg.petarh.vending.services.limits.LimitType;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Provides product operative services such as adding / removing products . <br/>
 * For non-operative product service see {@link ProductManagementService}
 */
@Service
public class ProductOperationService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LimitChecker limitChecker;

    public void addProduct(Product product) {
        if (limitChecker.isLimitReached(LimitType.MAX_QUANTITY, product)) {
            throw new RuntimeException(String.format("Can not add any more units of this product : %s", product.getName()));
        }

        ProductInventory inventory = this.productRepository.getProductInventory(product);
        if (inventory == null) {
            int nextId = this.productRepository.findAllAvailableProductInventory().size() + 1;
            inventory = new ProductInventory(nextId, product, 1);
        } else {
            inventory.setQuantity(inventory.getQuantity() + 1);
        }
        productRepository.saveOrUpdateProductInventory(inventory);

    }

    public void removeProduct(Product product) {
        if (limitChecker.isLimitReached(LimitType.MIN_QUANTITY, product)) {
            throw new RuntimeException(String.format("Something went wrong, cant remove product due to no quantity : %s", product.getName()));
        }

        ProductInventory inventory = this.productRepository.getProductInventory(product);

        inventory.setQuantity(inventory.getQuantity() - 1);

        productRepository.updateProductInventory(inventory);
    }

    public void updateProduct(Product product) {
        throw new NotImplementedException();
    }
}
