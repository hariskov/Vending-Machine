package bg.petarh.vending.order.chain;

import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.InsufficientCoinsInsertedResponse;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class SufficientCoinChecker extends PurchaseOrder {

    private final OrderManagement orderManagement;

    private final CoinManagement coinManagement;

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
