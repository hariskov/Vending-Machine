package bg.petarh.vending.coins;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * keeps hold of currently deposited coins
 */
class OrderCoinHolder extends CoinInventory{

    /**
     * cleans up the inventory
     * simulates the effect on current amount when money are sent(dropped) to permanent inventory.
     */
    public void dropCoins() {
        super.totalAmount = 0;
        super.inventory.clear();
    }
}