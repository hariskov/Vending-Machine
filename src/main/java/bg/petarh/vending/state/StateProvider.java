package bg.petarh.noser.state;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import bg.petarh.noser.coins.Coin;
import bg.petarh.noser.coins.CoinManagement;
import bg.petarh.noser.entities.ProductInventory;
import bg.petarh.noser.order.Order;
import bg.petarh.noser.order.OrderManagement;
import bg.petarh.noser.services.ProductService;

@Service
class StateProvider {

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private ProductService productService;

    @Autowired
    private CoinManagement coinManagement;

    public State getState() {

        OrdersState os = constructOrderState(orderManagement.getOrderHistory());
        ProductState ps = constructProductState(productService.getAllAvailableProductInventory());
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
