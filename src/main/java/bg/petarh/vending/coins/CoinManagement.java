package bg.petarh.noser.coins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

/**
 * responsible for moving money between the two inventories
 * <b>and</b> discharge the change from temp inventory to user
 */
@Service
public class CoinManagement {

    private final OrderCoinHolder orderCoinHolder;
    private final InventoryCoinHolder inventoryCoinHolder;
    private final ChangeCalculator changeCalculator;

    @Autowired
    CoinManagement(OrderCoinHolder orderCoinHolder, InventoryCoinHolder inventoryCoinHolder, ChangeCalculator changeCalculator) {
        this.orderCoinHolder = orderCoinHolder;
        this.inventoryCoinHolder = inventoryCoinHolder;
        this.changeCalculator = changeCalculator;
    }

    public void insertCoin(Coin insertedCoin) {
        this.orderCoinHolder.addCoin(insertedCoin, 1);
    }

    public Integer getCurrentAmount() {
        return this.orderCoinHolder.getTotalAmount();
    }

    public boolean canReturn(int cost) {
        return !changeCalculator.calculateChange(this.orderCoinHolder, cost).isEmpty();
    }

    /**
     * starts the process to return change to customer
     *
     * @param cost price of product
     *                         <ol>three steps
     *                             <li> calculate change </li>
     *                             <li> sends all money from temp hold to inventory </li>
     *                             <li> sends the required change amount from inventory to temp hold </li>
     *                         </ol>
     *             <b>Note : This calculates against internal inventory,
     *              not a combination of inventory + currently inserted coins for purchase </b>
     */

    public void performReturn(int cost) {
        Map<Coin, Integer> change = changeCalculator.calculateChange(inventoryCoinHolder, cost);
        this.moveCoinsToInventory(); // empty the temp hold
        this.moveCoinsToOrderInventory(change);
    }

    /**
     * Moves coin from order coin inventory to permanent inventory
     */
    public void moveCoinsToInventory() {
        Map<Coin, Integer> holderCoins = new EnumMap<>(this.orderCoinHolder.getInventory());
        this.orderCoinHolder.dropCoins();
        this.inventoryCoinHolder.addToInventory(holderCoins);
    }

    /**
     * Moves a given amount from permanent inventory to order inventory.
     * Should be used to collect the money prepared for change to customer.
     *
     * @param change quantity of coins marked for moving
     */
    private void moveCoinsToOrderInventory(Map<Coin, Integer> change) {
        for (Map.Entry<Coin, Integer> entry : change.entrySet()) {
            Coin coin = entry.getKey();
            int quantity = entry.getValue();

            this.orderCoinHolder.addCoin(coin, quantity);
            this.inventoryCoinHolder.removeCoin(coin, quantity);
        }
    }

    public Map<Coin, Integer> getCurrentCoinInventory() {
        return this.inventoryCoinHolder.getInventory();
    }

}
