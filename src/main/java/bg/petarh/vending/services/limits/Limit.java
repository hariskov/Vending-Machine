package bg.petarh.vending.services.limits;


import bg.petarh.vending.entities.Product;

abstract class Limit {

    public abstract boolean check(Product product);

}

