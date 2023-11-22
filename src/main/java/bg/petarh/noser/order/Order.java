package bg.petarh.noser.order;

import bg.petarh.noser.entities.Product;

public class Order {

    private Product selectedProduct;
    private int cost;

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        this.cost = selectedProduct.getCost();
    }

    public int getCost() {
        return cost;
    }
}
