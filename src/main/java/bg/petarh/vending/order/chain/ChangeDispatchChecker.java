package bg.petarh.vending.order.chain;

import org.springframework.beans.factory.annotation.Autowired;

import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.CantReturnChangeResponse;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class ChangeDispatchChecker extends PurchaseOrder {

    @Autowired
    private OrderManagement orderManagement;
    @Autowired
    private CoinManagement coinManagement;

    public ChangeDispatchChecker(OrderManagement orderManagement, CoinManagement coinManagement) {
        this.orderManagement = orderManagement;
        this.coinManagement = coinManagement;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {

        if (coinManagement.canReturn(orderManagement.getCurrentOrder().getCost())) {
            super.getNextPurchaseOrder().handle();
        }

        return new CantReturnChangeResponse();
    }
}
