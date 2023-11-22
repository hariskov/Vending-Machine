package bg.petarh.noser.order.chain;

import org.springframework.beans.factory.annotation.Autowired;

import bg.petarh.noser.coins.CoinManagement;
import bg.petarh.noser.order.OrderManagement;
import bg.petarh.noser.order.PurchaseOrder;
import bg.petarh.noser.rest.responses.CantReturnChangeResponse;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;

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
