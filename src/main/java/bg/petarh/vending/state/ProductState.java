package bg.petarh.vending.state;

import java.util.List;

public class ProductState {
    List<Product> products;
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public record Product(String name, int quantity) {
    }

    public List<Product> getProducts() {
        return products;
    }
}
