package bg.petarh.noser.order.chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import bg.petarh.noser.order.Order;
import bg.petarh.noser.order.OrderManagement;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;

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

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
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
