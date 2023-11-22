package bg.petarh.noser.coins;

import java.util.EnumMap;
import java.util.Map;

abstract class CoinInventory {

    protected int totalAmount;
    protected final Map<Coin, Integer> inventory = new EnumMap<>(Coin.class);

    public void addCoin(Coin coin, int quantity) {
        this.updateInventory(coin, quantity);
    }

    public int getTotalAmount() {
        return this.totalAmount;
    }

    public Map<Coin, Integer> getInventory() {
        return this.inventory;
    }

    public void removeCoin(Coin coin, int quantity) {
        if (this.inventory.get(coin) < quantity) {
            // should not happen unless the calculations for change are incorrect
            throw new RuntimeException(String.format("removing non existing quantity of coin %s, trying to remove %d, but found %d",
                    coin.name, quantity, this.inventory.get(coin)));
        }
        this.updateInventory(coin, -quantity);
    }

    private void updateInventory(Coin coin, int quantity) {
        inventory.merge(coin, quantity, Integer::sum);
        this.totalAmount += coin.getCoinValue() * quantity;
    }
}
