package bg.petarh.vending.rest.responses;

public class CantReturnChangeResponse implements PurchaseOrderHandlingResponse {

    static final String CANT_RETURN_CHANGE_RESPONSE = "There is not enough money in the machine to return you correct change";

    @Override
    public String message() {
        return CANT_RETURN_CHANGE_RESPONSE;
    }
}
