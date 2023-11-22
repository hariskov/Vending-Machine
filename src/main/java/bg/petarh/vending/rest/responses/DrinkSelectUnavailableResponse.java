package bg.petarh.vending.rest.responses;

public class DrinkSelectUnavailableResponse implements PurchaseOrderHandlingResponse {
    static final String UNAVAILABLE_QUANTITY_MESSAGE = "The product you have selected has no available quantities";

    @Override
    public String message() {
        return UNAVAILABLE_QUANTITY_MESSAGE;
    }
}
