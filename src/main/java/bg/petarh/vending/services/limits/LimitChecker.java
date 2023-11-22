package bg.petarh.vending.services.limits;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.services.ProductManagementService;
import org.springframework.stereotype.Component;


/**
 * Checks whether a limit has been reached for any given product.
 */
@Component
public class LimitChecker {

    private final Limits limits;

    public LimitChecker(ProductManagementService productManagementService) {
        Limit maxQuantityLimit = new MaxQuantityLimit(productManagementService, 10);
        Limit minQuantityLimit = new MinQuantityLimit(productManagementService);

        limits = new Limits.Builder()
                .addLimit(LimitType.MAX_QUANTITY, maxQuantityLimit)
                .addLimit(LimitType.MIN_QUANTITY, minQuantityLimit)
                .build();
    }

    public boolean isLimitReached(LimitType limitType, Product product) {
        return this.limits.getLimit(limitType).check(product);
    }

}

