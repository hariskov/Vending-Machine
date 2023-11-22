package bg.petarh.vending.services.limits;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.services.ProductManagementService;

class MinQuantityLimit extends Limit {
    private final ProductManagementService productManagementService;

    public MinQuantityLimit(ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @Override
    public boolean check(Product product) {
        int currentQuantity = this.productManagementService.getInventoryForProduct(product).getQuantity();
        return currentQuantity - 1 >= 0;
    }

}
