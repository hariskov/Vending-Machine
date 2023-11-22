package bg.petarh.noser.order.chain;

import bg.petarh.noser.coins.CoinManagement;
import bg.petarh.noser.order.OrderManagement;
import bg.petarh.noser.order.PurchaseOrder;
import bg.petarh.noser.rest.responses.InsufficientCoinsInsertedResponse;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;

public class SufficientCoinChecker extends PurchaseOrder {

    private OrderManagement orderManagement;

    private CoinManagement coinManagement;

    public SufficientCoinChecker(OrderManagement orderManagement, CoinManagement coinManagement) {
        this.orderManagement = orderManagement;
        this.coinManagement = coinManagement;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {
        int currentCoinHolder = coinManagement.getCurrentAmount();

        if (orderManagement.getCurrentOrder().getCost() < currentCoinHolder) {
            return super.getNextPurchaseOrder().handle();
        }

        return new InsufficientCoinsInsertedResponse();
    }
}
