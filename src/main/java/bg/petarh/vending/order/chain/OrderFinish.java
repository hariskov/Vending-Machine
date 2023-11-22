package bg.petarh.noser.order.chain;

import bg.petarh.noser.coins.CoinManagement;
import bg.petarh.noser.order.PurchaseOrder;
import bg.petarh.noser.rest.responses.NewOrderResponse;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;

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
