package bg.petarh.vending.order.chain;

import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class ProductSelect extends PurchaseOrder {

    @Override
    public PurchaseOrderHandlingResponse handle() {
        // product has been selected
        // pass along
        return super.getNextPurchaseOrder().handle();
    }

}
