package bg.petarh.noser.order.chain;

import bg.petarh.noser.order.OrderManagement;
import bg.petarh.noser.order.PurchaseOrder;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;

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
