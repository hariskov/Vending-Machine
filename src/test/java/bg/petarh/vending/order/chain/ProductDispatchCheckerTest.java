package bg.petarh.noser.order.chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import bg.petarh.noser.entities.Product;
import bg.petarh.noser.order.Order;
import bg.petarh.noser.order.OrderManagement;
import bg.petarh.noser.rest.responses.DrinkSelectUnavailableResponse;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.noser.services.ProductService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ProductDispatchCheckerTest extends AbstractPurchaseOrderTest {
    @Mock
    private ProductService productService;
    @Mock
    private OrderManagement orderManagement;

    @Mock
    private SufficientCoinChecker sufficientCoinChecker;

    @InjectMocks
    private ProductDispatchChecker productDispatchChecker;

    private final Product selectedProduct = new Product(1, "Cola", 1500);
    private final PurchaseOrderHandlingResponse mockResponse = new ProductSelectTest.TestResponse();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        Order currentOrder = new Order();
        currentOrder.setSelectedProduct(selectedProduct);
        when(orderManagement.getCurrentOrder()).thenReturn(currentOrder);
    }

    @Test
    void when_no_product_then_next_chain_response() {

        //GIVEN
        when(sufficientCoinChecker.handle()).thenReturn(mockResponse);
        productDispatchChecker.setNext(sufficientCoinChecker);
        when(productService.isProductAvailable(selectedProduct)).thenReturn(true);

        //WHEN
        PurchaseOrderHandlingResponse response = productDispatchChecker.handle();

        //THEN
        assertTrue(response instanceof TestResponse);
        assertEquals(response, mockResponse);
    }

    @Test
    void when_no_product_then_return_DrinkUnavailableResponse() {

        //GIVEN
        when(productService.isProductAvailable(selectedProduct)).thenReturn(false);

        //WHEN
        PurchaseOrderHandlingResponse response = productDispatchChecker.handle();

        //THEN
        assertTrue(response instanceof DrinkSelectUnavailableResponse);
    }

}
