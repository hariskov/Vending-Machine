package bg.petarh.noser.order.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.petarh.noser.coins.CoinManagement;
import bg.petarh.noser.order.OrderManagement;
import bg.petarh.noser.order.PurchaseOrder;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;

public class ChangeDispatcher extends PurchaseOrder {

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private CoinManagement coinManagement;

    public ChangeDispatcher(OrderManagement orderManagement, CoinManagement coinManagement){
        this.orderManagement = orderManagement;
        this.coinManagement = coinManagement;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {
        this.performLastOrderChangeReturn();
        return null;
    }

    void performLastOrderChangeReturn() {
        coinManagement.performReturn(orderManagement.getLastOrderCost());
    }
}