package bg.petarh.vending.order.chain;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.DrinkSelectUnavailableResponse;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.vending.services.ProductManagementService;

public class ProductDispatchChecker extends PurchaseOrder {

    private final ProductManagementService productManagementService;
    private final OrderManagement orderManagement;

    public ProductDispatchChecker(ProductManagementService productManagementService, OrderManagement orderManagement) {
        this.orderManagement = orderManagement;
        this.productManagementService = productManagementService;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {
        Product product = orderManagement.getCurrentOrder().getSelectedProduct();

        boolean hasProduct = productManagementService.isProductAvailable(product);
        if (hasProduct) {
            return super.getNextPurchaseOrder().handle();
        }

        return new DrinkSelectUnavailableResponse();
    }
}
