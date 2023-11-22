package bg.petarh.vending.order.chain;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import bg.petarh.vending.order.Order;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ProductDispatcherTest extends AbstractPurchaseOrderTest {

    @Mock
    private OrderManagement orderManagement;

    @Mock
    private ChangeDispatcher changeDispatcher;

    @InjectMocks
    private ProductDispatcher productDispatcher;

    private final int productPrice = 1500;
    private final PurchaseOrderHandlingResponse mockResponse = new TestResponse();

    @Override
    void setup() {
        Order currentOrder = new Order();
        when(orderManagement.getCurrentOrder()).thenReturn(currentOrder);
        when(changeDispatcher.handle()).thenReturn(mockResponse);
        productDispatcher.setNext(changeDispatcher);
    }

    @Test
    void when_can_return_change_then_next_handle() {

        //WHEN
        PurchaseOrderHandlingResponse response = changeDispatcher.handle();

        //THEN
        assertTrue(response instanceof TestResponse);
        assertEquals(mockResponse, response);
    }
}
