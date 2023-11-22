package bg.petarh.vending.state;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import bg.petarh.vending.coins.Coin;
import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.entities.ProductInventory;
import bg.petarh.vending.order.Order;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.services.ProductManagementService;

@Service
class StateProvider {

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private ProductManagementService productManagementService;

    @Autowired
    private CoinManagement coinManagement;

    public State getState() {

        OrdersState os = constructOrderState(orderManagement.getOrderHistory());
        ProductState ps = constructProductState(productManagementService.getAllAvailableProductInventory());
        CoinState cs = constructCoinState(coinManagement.getCurrentCoinInventory());

        return new State(os, ps, cs);
    }

    private OrdersState constructOrderState(List<Order> orders) {
        OrdersState ordersState = new OrdersState();
        ordersState.setOrders(orders
                .stream()
                .map(order -> new OrdersState.Order(order.getSelectedProduct().getName(), order.getCost()))
                .toList());
        return ordersState;
    }

    private ProductState constructProductState(List<ProductInventory> products) {
        ProductState productState = new ProductState();
        productState.setProducts(products
                .stream()
                .map(productInventory -> new ProductState.Product(productInventory.getProduct().getName(), productInventory.getQuantity()))
                .toList());
        return productState;
    }

    private CoinState constructCoinState(Map<Coin, Integer> coinInventory) {
        CoinState coinState = new CoinState();
        coinState.setCoins(coinInventory.entrySet()
                .stream()
                .map(coinIntegerEntry -> new CoinState.Coin(coinIntegerEntry.getKey().name(), coinIntegerEntry.getValue()))
                .toList());
        return coinState;
    }
}

record State(OrdersState currentOrder, ProductState productsInfo, CoinState coinState) {

}
