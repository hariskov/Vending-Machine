package bg.petarh.vending.order.chain;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProductSelectTest extends AbstractPurchaseOrderTest {
    @Mock
    private ProductDispatchChecker productDispatchChecker;
    @InjectMocks
    private ProductSelect productSelect = new ProductSelect();

    private final PurchaseOrderHandlingResponse mockResponse = new TestResponse();

    @Override
    void setup() {
        when(productDispatchChecker.handle()).thenReturn(mockResponse);
    }

    @Test
    void should_return_next_chain_response() {
        //WHEN
        PurchaseOrderHandlingResponse response = productSelect.handle();
        //THEN
        assertEquals(mockResponse, response);
        assertEquals(SIMULATED_TEST_RESPONSE, response.message());
    }

}
