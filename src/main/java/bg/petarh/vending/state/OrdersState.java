package bg.petarh.vending.state;

import java.util.List;

public class OrdersState {

    private List<Order> orders;

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public record Order(String productName, int productValue) {
    }

}
