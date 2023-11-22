package bg.petarh.vending.coins;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

class ChangeCalculator {

    Map<Coin, Integer> calculateChange(CoinInventory inventory, int purchaseAmount) {
        int changeAmount = inventory.getTotalAmount() - purchaseAmount;
        Map<Coin, Integer> change = new EnumMap<>(Coin.class);

        Coin[] coins = Coin.valuesSortedDecrease();

        for (Coin coin : coins) {
            int numberOfCoins = changeAmount / coin.getCoinValue();
            int availableCoins = inventory.getInventory().getOrDefault(coin, 0);
            int coinsToGive = Math.min(numberOfCoins, availableCoins);

            if (coinsToGive > 0) {
                change.put(coin, coinsToGive);
                changeAmount -= coinsToGive * coin.getCoinValue();
            }
        }

        if (changeAmount == 0) {
            return change;
        } else {
            return Collections.emptyMap();
        }
    }
}
