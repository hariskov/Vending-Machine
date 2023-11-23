package bg.petarh.vending.rest.responses;

public class InsufficientCoinsInsertedResponse implements PurchaseOrderHandlingResponse {

    public static final String INSUFFICIENT_AMMOUNT_RESPONSE = "You have not yet inserted enough for the drink you have selected";

    @Override
    public String message() {
        return INSUFFICIENT_AMMOUNT_RESPONSE;
    }
}
