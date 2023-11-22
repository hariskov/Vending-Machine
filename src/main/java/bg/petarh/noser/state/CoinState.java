package bg.petarh.noser.state;

import java.util.List;

public class CoinState {
    List<Coin> coins;

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public record Coin(String name, int v) {
    }
}
