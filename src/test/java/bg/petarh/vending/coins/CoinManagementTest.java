package bg.petarh.vending.coins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class CoinManagementTest {
    @InjectMocks
    private CoinManagement coinManagement;

    @Spy
    private OrderCoinHolder orderCoinHolder;

    @Spy
    private InventoryCoinHolder inventoryCoinHolder;

    @Mock
    private ChangeCalculator changeCalculator;

    private AutoCloseable autoCloseable;

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void move_coins_from_order_to_inventory() {

        //GIVEN
        setupCoinHolders();

        // WHEN
        assertEquals(380, inventoryCoinHolder.getTotalAmount());
        assertEquals(190, orderCoinHolder.getTotalAmount());

        coinManagement.moveCoinsToInventory();

        // THEN
        assertEquals(0, orderCoinHolder.getTotalAmount());
        assertTrue(orderCoinHolder.getInventory().isEmpty());
        assertEquals(570, inventoryCoinHolder.getTotalAmount());
        assertFalse(inventoryCoinHolder.getInventory().isEmpty());
    }

    @Test
    void perform_return() {
        //GIVEN
        setupCoinHolders();

        int purchaseAmount = 150;
        Map<Coin, Integer> calculatedChange = Collections.singletonMap(Coin.TEN, 2);
        when(changeCalculator.calculateChange(inventoryCoinHolder, purchaseAmount)).thenReturn(calculatedChange);
//        when(coinManagement.getChange(inventoryCoinHolder, purchaseAmount)).thenReturn(calculatedChange);
        int calculatedChangeAmount = 20;
        int inventoryStartAmount = inventoryCoinHolder.getTotalAmount();
        int orderStartAmount = orderCoinHolder.getTotalAmount();
        int totalExpectedCashInMachine = inventoryStartAmount + orderStartAmount - calculatedChangeAmount;


        //WHEN
        coinManagement.performReturn(purchaseAmount);

        //THEN
        assertFalse(orderCoinHolder.getInventory().isEmpty());
        assertEquals(calculatedChange, orderCoinHolder.getInventory());
        assertEquals(calculatedChangeAmount, orderCoinHolder.getTotalAmount());
        assertEquals(totalExpectedCashInMachine, inventoryCoinHolder.getTotalAmount());

    }

    @Test
    void add_to_both_inventories() {
        //GIVEN both inventories are empty
        assertEmptyHoldersAtStart();

        //WHEN coins are added
        setupCoinHolders();

        //THEN
        assertEquals(190, orderCoinHolder.getTotalAmount());
        assertEquals(380, inventoryCoinHolder.getTotalAmount());

        assertEquals(2, orderCoinHolder.getInventory().get(Coin.FIFTY));
        assertEquals(2, orderCoinHolder.getInventory().get(Coin.TWENTY));
        assertEquals(5, orderCoinHolder.getInventory().get(Coin.TEN));

        assertEquals(4, inventoryCoinHolder.getInventory().get(Coin.FIFTY));
        assertEquals(4, inventoryCoinHolder.getInventory().get(Coin.TWENTY));
        assertEquals(10, inventoryCoinHolder.getInventory().get(Coin.TEN));

    }

    private void assertEmptyHoldersAtStart() {
        assertEquals(0, orderCoinHolder.getTotalAmount());
        assertEquals(0, inventoryCoinHolder.getTotalAmount());
        assertTrue(orderCoinHolder.getInventory().isEmpty());
        assertTrue(inventoryCoinHolder.getInventory().isEmpty());
    }

    /**
     * <ul>Coin Holder total = 190st
     * <li>5x10st</li>
     * <li>2x20st</li>
     * <li>2x50st</li>
     * </ul>
     * <ul>Inventory Coin Holder = 380st
     * <li>10x10st</li>
     * <li>4x20st</li>
     * <li>4x50st</li>
     * </ul>
     */
    private void setupCoinHolders() {

        orderCoinHolder.addCoin(Coin.TEN, 5);
        orderCoinHolder.addCoin(Coin.TWENTY, 2);
        orderCoinHolder.addCoin(Coin.FIFTY, 2);

        inventoryCoinHolder.addToInventory(orderCoinHolder.getInventory());
        inventoryCoinHolder.addToInventory(orderCoinHolder.getInventory());

    }


}
