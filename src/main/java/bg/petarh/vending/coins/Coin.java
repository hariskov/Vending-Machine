package bg.petarh.noser.coins;


import java.util.Arrays;

// possible input coins
public enum Coin {
    TEN(10, "10st"),
    TWENTY(20, "20st"),
    FIFTY(50, "50st"),
    ONE(100, "1lv"),
    TWO(200, "2lv");

    final int coinValue;
    final String name;

    Coin(int coinValue, String name) {
        this.coinValue = coinValue;
        this.name = name;
    }

    public static Coin[] valuesSortedDecrease() {
        Coin[] values = values();
        Arrays.sort(values, (o1, o2) -> o2.getCoinValue() - o1.getCoinValue());
        return values;
    }

    public int getCoinValue() {
        return this.coinValue;
    }

    @Override
    public String toString() {
        return this.name;
    }
}