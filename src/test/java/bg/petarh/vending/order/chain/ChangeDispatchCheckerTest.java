package bg.petarh.vending.order.chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.order.Order;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.rest.responses.CantReturnChangeResponse;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChangeDispatchCheckerTest extends AbstractPurchaseOrderTest {

    @Mock
    private OrderManagement orderManagement;
    @Mock
    private CoinManagement coinManagement;

    @Mock
    private ProductDispatcher productDispatcher;

    @InjectMocks
    private ChangeDispatchChecker changeDispatchChecker;


    private final int productPrice = 1500;
    private final PurchaseOrderHandlingResponse mockResponse = new TestResponse();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        Order currentOrder = new Order();
        when(orderManagement.getCurrentOrder()).thenReturn(currentOrder);
    }

    @Test
    void when_can_return_change_then_next_handle() {

        //GIVEN
        when(productDispatcher.handle()).thenReturn(mockResponse);
        changeDispatchChecker.setNext(productDispatcher);
        when(coinManagement.canReturn(productPrice)).thenReturn(true);

        //WHEN
        PurchaseOrderHandlingResponse response = productDispatcher.handle();

        //THEN
        assertTrue(response instanceof TestResponse);
        assertEquals(response, mockResponse);
    }

    @Test
    void when_cant_return_change_then_CantReturnChangeResponse() {
        //GIVEN
        when(coinManagement.canReturn(productPrice)).thenReturn(false);

        //WHEN
        PurchaseOrderHandlingResponse response = changeDispatchChecker.handle();

        //THEN
        assertTrue(response instanceof CantReturnChangeResponse);
        verify(coinManagement, times(1));

    }

}
