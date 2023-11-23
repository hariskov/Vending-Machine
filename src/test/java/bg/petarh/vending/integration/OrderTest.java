package bg.petarh.vending.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import bg.petarh.vending.coins.Coin;
import bg.petarh.vending.entities.Product;
import bg.petarh.vending.repository.ProductRepository;
import bg.petarh.vending.rest.responses.CantReturnChangeResponse;
import bg.petarh.vending.rest.responses.InsufficientCoinsInsertedResponse;
import bg.petarh.vending.rest.responses.NewOrderResponse;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setup() {
        product = productRepository.findAvailableProducts().get(0);
    }

    @Test
    void select_drink_no_coins() throws Exception {
        String productString = mapper.writeValueAsString(product);

        MvcResult response = mockMvc.perform(post("/selectDrink")
                        .content(productString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = response.getResponse().getContentAsString();
        assertTrue("should return insufficient amount", content.contains(InsufficientCoinsInsertedResponse.INSUFFICIENT_AMMOUNT_RESPONSE));
    }

    @Test
    void insert_coins_no_drink_selected() throws Exception {
        String coinString = mapper.writeValueAsString(Coin.TEN);

        MvcResult response = mockMvc.perform(post("/insertCoin")
                        .content(coinString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = response.getResponse().getContentAsString();
        assertEquals("should return correct coin inventory", content, String.valueOf(Coin.TEN.getCoinValue()));
    }

    @Test
    void insert_coins_drink_selected_sufficient_amount_cant_return_change() throws Exception {
        String coinString = mapper.writeValueAsString(Coin.TWO);
        String productString = mapper.writeValueAsString(product);

        mockMvc.perform(post("/insertCoin")
                        .content(coinString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult response = mockMvc.perform(post("/selectDrink")
                        .content(productString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = response.getResponse().getContentAsString();
        assertTrue("cant give change back", content.contains(CantReturnChangeResponse.CANT_RETURN_CHANGE_RESPONSE));
    }

    @Test
    void insert_coins_drink_Selected_sufficient_amount_can_return_change() throws Exception {
        String coinString = mapper.writeValueAsString(Coin.FIFTY);
        String productString = mapper.writeValueAsString(product);

        // add enough coins for change
        for (int i = 0; i < 4; i++) {
            mockMvc.perform(post("/insertCoin")
                            .content(coinString)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        MvcResult response = mockMvc.perform(post("/selectDrink")
                        .content(productString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = response.getResponse().getContentAsString();
        assertTrue("order finished", content.contains(NewOrderResponse.NEW_ORDER_RESPONSE));

    }
}
