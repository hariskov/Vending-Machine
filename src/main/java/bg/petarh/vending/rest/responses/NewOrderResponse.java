package bg.petarh.vending.rest.responses;

public class NewOrderResponse implements PurchaseOrderHandlingResponse {

    public static final String NEW_ORDER_RESPONSE = "Please Select Your Drink";

    @Override
    public String message() {
        return NEW_ORDER_RESPONSE;
    }
}
