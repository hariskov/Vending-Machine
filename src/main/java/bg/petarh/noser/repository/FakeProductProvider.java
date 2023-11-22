package bg.petarh.noser.repository;

import java.util.ArrayList;
import java.util.List;

import bg.petarh.noser.entities.Product;
import bg.petarh.noser.entities.ProductInventory;

public final class FakeProductProvider {
    private List<Product> productList = new ArrayList<>();
    private List<ProductInventory> productInventoryList = new ArrayList<>();

    FakeProductProvider() {
        String[][] inventory = new String[][]{
                new String[]{"1", "Cola", "150", "2"},
                new String[]{"2", "Fanta", "140", "2"},
                new String[]{"3", "Sprite", "140", "2"},
                new String[]{"4", "Water", "100", "2"},
                new String[]{"5", "Cashew", "350", "2"},
                new String[]{"6", "Peanuts", "350", "2"},
                new String[]{"7", "Empty can of Tea", "12500", "1"}
        };

        for (String[] object : inventory) {
            int id = convertToInt(object[0]);
            int price = convertToInt(object[2]);
            int quantity = convertToInt(object[3]);
            this.constructProduct(id, object[1], price, quantity);
        }
    }

    public List<Product> findAllProducts() {
        return this.productList;
    }

    public List<ProductInventory> findAllInventory() {
        return this.productInventoryList;
    }

    private void constructProduct(int id, String name, int price, int quantity) {
        Product product = new Product(id, name, price);
        this.productList.add(product);
        ProductInventory productInventory = new ProductInventory(id, product, quantity);
        this.productInventoryList.add(productInventory);
    }

    private int convertToInt(String s) {
        return Integer.parseInt(s);
    }

}
