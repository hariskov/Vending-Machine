package bg.petarh.vending.order.chain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public abstract class AbstractPurchaseOrderTest {
    protected static final String SIMULATED_TEST_RESPONSE = "Simulated test response for next chained class";

    static class TestResponse implements PurchaseOrderHandlingResponse {

        @Override
        public String message() {
            return SIMULATED_TEST_RESPONSE;
        }
    }

    private AutoCloseable autoCloseable;

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @BeforeEach
    void abstractSetup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        setup();
    }

    abstract void setup();
}
