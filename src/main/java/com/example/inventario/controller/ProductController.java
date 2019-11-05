package com.example.inventario.controller;

import com.example.inventario.model.Product;
import com.example.inventario.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public void createProduct(@RequestBody @Valid Product product) {
        try {
            productRepository.save(product);

        } catch (DataIntegrityViolationException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("An error has occurred while the product was created"));
        }
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public List<Product> getProducts() {
        List<Product> productsFound = new ArrayList<>();

        productsFound = productRepository.findAll();

        if (productsFound.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT, String.format("There aren't any products uploaded yet"));
        }
        return productsFound;

    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable("id") Integer id) {
        return productRepository.findById(id).orElseThrow(() ->
                new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("The product with this id doesn't exist: %s", id)));
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public void modifyProduct(@RequestBody @Valid Product product, @PathVariable("id") Integer id){
        Product productFound = productRepository.findById(id).orElseThrow(()->
                new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("The product doesn't exist")));

        productFound.setName(product.getName());
        productFound.setDescription(product.getDescription());
        productFound.setAmount(product.getAmount());
        productFound.setPrice(product.getPrice());

        productRepository.save(productFound);

    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable("id") Integer id){
        Product productFound = productRepository.findById(id).orElseThrow(()->
                new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("The product doesn't exist")));

        productRepository.delete(productFound);

    }



}
