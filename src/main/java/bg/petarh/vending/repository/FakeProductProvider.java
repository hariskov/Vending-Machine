package bg.petarh.vending.repository;

import java.util.ArrayList;
import java.util.List;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.entities.ProductInventory;

final class FakeProductProvider {
    private final List<ProductInventory> productInventoryList = new ArrayList<>();

    FakeProductProvider() {
        String[][] inventory = new String[][]{
                new String[]{"1", "Cola", "150", "10"},
                new String[]{"2", "Fanta", "140", "10"},
                new String[]{"3", "Sprite", "140", "10"},
                new String[]{"4", "Water", "100", "10"},
                new String[]{"5", "Cashew", "350", "10"},
                new String[]{"6", "Peanuts", "350", "10"},
                new String[]{"7", "Empty can of Tea", "12500", "10"}
        };

        for (String[] object : inventory) {
            int id = convertToInt(object[0]);
            int price = convertToInt(object[2]);
            int quantity = convertToInt(object[3]);
            this.constructProduct(id, object[1], price, quantity);
        }
    }


    public List<ProductInventory> findAllInventory() {
        return this.productInventoryList;
    }

    private void constructProduct(int id, String name, int price, int quantity) {
        Product product = new Product(id, name, price);
        ProductInventory productInventory = new ProductInventory(id, product, quantity);
        this.productInventoryList.add(productInventory);
    }

    private int convertToInt(String s) {
        return Integer.parseInt(s);
    }

}
