package bg.petarh.vending.order.chain;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.order.OrderStatus;
import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.vending.services.ProductOperationService;

public class ProductDispatcher extends PurchaseOrder {
    private final OrderManagement orderManagement;
    private final ProductOperationService productOperationService;

    public ProductDispatcher(ProductOperationService productOperationService, OrderManagement orderManagement) {
        this.productOperationService = productOperationService;
        this.orderManagement = orderManagement;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {

        OrderStatus status;

        try {
            Product product = orderManagement.getCurrentOrder().getSelectedProduct();
            productOperationService.removeProduct(product);

            status = OrderStatus.SUCCESS;
        } catch (Exception e) {
            // if something goes wrong with product/order switches - return all current money to the poor soul
            // do an actual log
            status = OrderStatus.ERROR;
            
            e.printStackTrace();
        }
        orderManagement.finishOrder(status);

        //TODO
        // drop product (well maybe)

        return getNextPurchaseOrder().handle();
    }
}
