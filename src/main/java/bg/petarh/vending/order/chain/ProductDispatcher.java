package bg.petarh.vending.order.chain;

import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class ProductDispatcher extends PurchaseOrder {
    private OrderManagement orderManagement;

    public ProductDispatcher(OrderManagement orderManagement) {
        this.orderManagement = orderManagement;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {
        orderManagement.finishOrder();
        return getNextPurchaseOrder().handle();
    }
}
