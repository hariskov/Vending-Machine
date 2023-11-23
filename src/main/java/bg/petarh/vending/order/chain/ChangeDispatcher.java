package bg.petarh.vending.order.chain;

import org.springframework.beans.factory.annotation.Autowired;

import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class ChangeDispatcher extends PurchaseOrder {

    @Autowired
    private final OrderManagement orderManagement;

    @Autowired
    private final CoinManagement coinManagement;

    public ChangeDispatcher(OrderManagement orderManagement, CoinManagement coinManagement) {
        this.orderManagement = orderManagement;
        this.coinManagement = coinManagement;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {

        // do we expect issues here ?
        this.performLastOrderChangeReturn();

        return super.getNextPurchaseOrder().handle();
    }

    void performLastOrderChangeReturn() {
        coinManagement.performReturn(orderManagement.getLastOrderCost());
    }
}