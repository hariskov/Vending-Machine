package bg.petarh.noser.coins;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * to simulate the total amount in the vending machine
 */
@Component
class InventoryCoinHolder extends CoinInventory {

    /**
     *
     * @param coinsToAdd quantity of coins to add to the holder.
     */
    public void addToInventory(Map<Coin, Integer> coinsToAdd) {
        for (Map.Entry<Coin, Integer> coinQuantity : coinsToAdd.entrySet()) {
            addCoin(coinQuantity.getKey(), coinQuantity.getValue());
        }
    }
}