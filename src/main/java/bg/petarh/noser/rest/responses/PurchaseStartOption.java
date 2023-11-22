package bg.petarh.noser.rest.responses;

public class PurchaseStartOption implements PurchaseOrderHandlingResponse {
    static final String CONTINUE_OR_RETURN_CHANGE = "Please select a drink or press the return change button";

    @Override
    public String message() {
        return CONTINUE_OR_RETURN_CHANGE;
    }

    // do you want to continue ?
    // do you want to return cash ?
}
