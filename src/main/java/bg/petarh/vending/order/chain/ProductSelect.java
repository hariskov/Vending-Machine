package bg.petarh.noser.order.chain;

import bg.petarh.noser.order.PurchaseOrder;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;

public class ProductSelect extends PurchaseOrder {

    @Override
    public PurchaseOrderHandlingResponse handle() {
        // product has been selected
        // pass along
        return super.getNextPurchaseOrder().handle();
    }

}
