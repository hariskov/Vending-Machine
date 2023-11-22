package bg.petarh.vending.order.chain;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.rest.responses.NewOrderResponse;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrderFinishTest extends AbstractPurchaseOrderTest {

    @Mock
    private CoinManagement coinManagement;

    @InjectMocks
    private OrderFinish orderFinish;

    @Test
    void testHandle() {

        //WHEN
        PurchaseOrderHandlingResponse response = orderFinish.handle();

        //THEN
        verify(coinManagement, times(1)).moveCoinsToInventory();
        assertTrue(response instanceof NewOrderResponse);
    }

    @Override
    void setup() {

    }
}
