package bg.petarh.noser.order.chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import bg.petarh.noser.coins.CoinManagement;
import bg.petarh.noser.entities.Product;
import bg.petarh.noser.order.Order;
import bg.petarh.noser.order.OrderManagement;
import bg.petarh.noser.rest.responses.InsufficientCoinsInsertedResponse;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.noser.services.ProductService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class SufficientCoinCheckerTest extends AbstractPurchaseOrderTest{

    @Mock
    private OrderManagement orderManagement;
    @Mock
    private CoinManagement coinManagement;

    @Mock
    private ChangeDispatchChecker changeDispatchChecker;

    @InjectMocks
    private SufficientCoinChecker sufficientCoinChecker;

    private final int productPrice = 1500;
    private final Product selectedProduct = new Product(1, "Cola", productPrice);
    private final PurchaseOrderHandlingResponse mockResponse = new TestResponse();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        Order currentOrder = new Order();
        currentOrder.setSelectedProduct(selectedProduct);
        when(orderManagement.getCurrentOrder()).thenReturn(currentOrder);
    }

    @Test
    void when_current_inserted_amount_is_sufficient_then_next_handle() {

        //GIVEN
        when(changeDispatchChecker.handle()).thenReturn(mockResponse);
        sufficientCoinChecker.setNext(changeDispatchChecker);
        when(coinManagement.getCurrentAmount()).thenReturn(1600);

        //WHEN
        PurchaseOrderHandlingResponse response = sufficientCoinChecker.handle();

        //THEN
        assertTrue(response instanceof TestResponse);
        assertEquals(response, mockResponse);
    }

    @Test
    void when_current_inserted_amount_is_insufficient_return_InsufficientCoinsInsertedResponse() {

        //GIVEN
        when(coinManagement.getCurrentAmount()).thenReturn(1000);

        //WHEN
        PurchaseOrderHandlingResponse response = sufficientCoinChecker.handle();

        //THEN
        assertTrue(response instanceof InsufficientCoinsInsertedResponse);
    }

}
