package bg.petarh.noser.rest.responses;

public class NewOrderResponse implements PurchaseOrderHandlingResponse{

    static final String NEW_ORDER_RESPONSE = "Please Select Your Drink";
    @Override
    public String message() {
        return NEW_ORDER_RESPONSE;
    }
}
