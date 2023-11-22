package bg.petarh.vending.order.chain;

import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class AbstractPurchaseOrderTest {
    protected static final String SIMULATED_TEST_RESPONSE = "Simulated test response for next chained class";

    static class TestResponse implements PurchaseOrderHandlingResponse {

        @Override
        public String message() {
            return SIMULATED_TEST_RESPONSE;
        }
    }
}
