package bg.petarh.noser.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bg.petarh.noser.entities.Product;
import bg.petarh.noser.services.ProductService;


@RestController(value = "/product-management")
public class ManagementOperations {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/addProduct")
    public void addProduct(@RequestBody Product product) {

    }

    @PatchMapping(value = "/updateProduct")
    public void updateProduct(@RequestBody Product product) {

    }

    @DeleteMapping(value = "/removeProduct")
    public void removeProduct(@RequestBody Product product) {

    }
}
