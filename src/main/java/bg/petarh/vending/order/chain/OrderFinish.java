package bg.petarh.vending.order.chain;

import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.NewOrderResponse;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class OrderFinish extends PurchaseOrder {
    private final CoinManagement coinManagement;

    public OrderFinish(CoinManagement coinManagement) {
        this.coinManagement = coinManagement;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {
        coinManagement.moveCoinsToInventory();
        return new NewOrderResponse();
    }
}
