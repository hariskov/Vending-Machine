package bg.petarh.vending.order.chain;

import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.vending.rest.responses.PurchaseStartOption;

public class OrderStart extends PurchaseOrder {

    @Override
    public PurchaseOrderHandlingResponse handle() {
        // handle
        // nextPurchaseOrder.handle();
        return new PurchaseStartOption();
    }
}
