package bg.petarh.vending.order.chain;

import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class ProductDispatcher extends PurchaseOrder {
    private final OrderManagement orderManagement;

    public ProductDispatcher(OrderManagement orderManagement) {
        this.orderManagement = orderManagement;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {
        orderManagement.finishOrder();

        //TODO
        // drop product and remove from inventory

        return getNextPurchaseOrder().handle();
    }
}
