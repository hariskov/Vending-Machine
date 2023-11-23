package bg.petarh.vending.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.services.ProductOperationService;


@RestController(value = "/product-management")
public class ManagementOperations {

    @Autowired
    private ProductOperationService productOperationService;

    @PostMapping(value = "/addProduct")
    public void addProduct(@RequestBody Product product) {
        productOperationService.addProduct(product);
    }

    @PatchMapping(value = "/updateProduct")
    public void updateProduct(@RequestBody Product product) {
        productOperationService.updateProduct(product);
    }

    @DeleteMapping(value = "/removeProduct")
    public void removeProduct(@RequestBody Product product) {
        productOperationService.removeProduct(product);
    }
}
