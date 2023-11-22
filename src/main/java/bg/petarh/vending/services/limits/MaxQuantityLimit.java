package bg.petarh.vending.services.limits;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.entities.ProductInventory;
import bg.petarh.vending.services.ProductManagementService;

class MaxQuantityLimit extends Limit {

    private final ProductManagementService productManagementService;

    private final int quantityLimit;

    public MaxQuantityLimit(ProductManagementService productManagementService, int quantityLimit) {
        this.productManagementService = productManagementService;
        this.quantityLimit = quantityLimit;
    }

    @Override
    public boolean check(Product product) {
        ProductInventory inventory = productManagementService.getInventoryForProduct(product);
        return inventory.getQuantity() + 1 <= quantityLimit;
    }
}
