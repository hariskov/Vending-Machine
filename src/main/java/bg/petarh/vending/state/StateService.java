package bg.petarh.vending.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    @Autowired
    private StateProvider stateProvider;

    private final ObjectMapper mapper = new ObjectMapper();

    public String obtainState() {
        State state = stateProvider.getState();
        return buildToString(state);
    }
    private String buildToString(State state) {
        try {
            return mapper.writeValueAsString(state);
        } catch (JsonProcessingException e) {
            // if i had bothered adding a logger - this would have been a logged error :)
            throw new RuntimeException("There has been an issue with mapping the states to a string");
        }

    }

}
