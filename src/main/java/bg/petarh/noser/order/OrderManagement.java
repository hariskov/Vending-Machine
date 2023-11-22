package bg.petarh.noser.order;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import bg.petarh.noser.entities.Product;

@Service
public class OrderManagement {

    private Order currentOrder;
    private List<Order> orderHistory = new ArrayList<>();

    public Order getCurrentOrder() {
        return this.getOrStartOrder();
    }

    private Order getOrStartOrder() {
        if (this.currentOrder == null) {
            this.currentOrder = new Order();
        }
        return this.currentOrder;
    }

    public void selectProduct(Product product) {
        getOrStartOrder().setSelectedProduct(product);
    }

    public void finishOrder() {
        // do the actual dispatching of the drink
        orderHistory.add(this.getCurrentOrder());
        this.currentOrder = null;
    }

    public int getLastOrderCost() {
        Order order = orderHistory.get(orderHistory.size() - 1);
        if (order != null) {
            return order.getCost();
        }
        return 0;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }
}
