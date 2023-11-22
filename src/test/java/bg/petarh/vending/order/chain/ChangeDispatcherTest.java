package bg.petarh.vending.order.chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.order.OrderManagement;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChangeDispatcherTest extends AbstractPurchaseOrderTest {

    @Mock
    private CoinManagement coinManagement;
    @Mock
    private OrderManagement orderManagement;

    private ChangeDispatcher changeDispatcher;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        changeDispatcher = new ChangeDispatcher(orderManagement, coinManagement);
    }

    @Test
    void testHandle() {

        //GIVEN
        int value = 100;
        when(orderManagement.getLastOrderCost()).thenReturn(value);

        //WHEN
        this.changeDispatcher.handle();

        //THEN
        verify(coinManagement, times(1)).performReturn(value);
    }

}
