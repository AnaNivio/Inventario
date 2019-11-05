package com.example.inventario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer id;

    @NotNull(message = "Products MUST have a name")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Products MUST have a description")
    @Column(name = "description")
    private String description;

    //see if this atributte is neccesary in product. Maybe, we could have an entity stock for each product and, trough a method, obtained the number of products in stock and inicialize it into this. For that, we must erease the @NotNull tag
    @NotNull(message = "Products MUST have an amount")
    @Column(name = "amount")
    private Integer amount;

    @NotNull(message = "Products MUST have a price")
    @Column(name = "price")
    private float price;


}
