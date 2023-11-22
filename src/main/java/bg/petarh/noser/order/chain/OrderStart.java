package bg.petarh.noser.order.chain;

import org.springframework.stereotype.Component;

import bg.petarh.noser.order.PurchaseOrder;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.noser.rest.responses.PurchaseStartOption;

public class OrderStart extends PurchaseOrder {

    @Override
    public PurchaseOrderHandlingResponse handle() {
        // handle
        // nextPurchaseOrder.handle();
        return new PurchaseStartOption();
    }
}
