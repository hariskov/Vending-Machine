package bg.petarh.vending.order.chain;

import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.NewOrderResponse;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class OrderFinish extends PurchaseOrder {

    @Override
    public PurchaseOrderHandlingResponse handle() {
        return new NewOrderResponse();
    }
}
