package bg.petarh.noser.order;

import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;

public abstract class PurchaseOrder {
    private PurchaseOrder nextPurchaseOrder;

    public void setNext(PurchaseOrder purchaseOrder) {
        this.nextPurchaseOrder = purchaseOrder;
    }

    public abstract PurchaseOrderHandlingResponse handle();

    public PurchaseOrder getNextPurchaseOrder() {
        return this.nextPurchaseOrder;
    }

}
