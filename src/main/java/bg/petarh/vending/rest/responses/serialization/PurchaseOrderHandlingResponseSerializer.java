package bg.petarh.vending.rest.responses.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;

public class PurchaseOrderHandlingResponseSerializer extends JsonSerializer<PurchaseOrderHandlingResponse> {

    @Override
    public void serialize(PurchaseOrderHandlingResponse value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("message", value.message());
        gen.writeEndObject();
    }
}
