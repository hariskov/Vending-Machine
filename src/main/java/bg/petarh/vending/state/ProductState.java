package bg.petarh.vending.state;

import java.util.List;

class ProductState {
    List<Product> products;
    void setProducts(List<Product> products) {
        this.products = products;
    }

    record Product(String name, int quantity) {
    }

    List<Product> getProducts() {
        return products;
    }
}
