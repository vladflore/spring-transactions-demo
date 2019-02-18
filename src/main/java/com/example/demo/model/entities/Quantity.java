package com.example.demo.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "QUANTITY")
public class Quantity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "QUANTITY_VALUE")
    private Long quantityValue;

    public Quantity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantityValue() {
        return quantityValue;
    }

    public Quantity setQuantityValue(Long quantityValue) {
        this.quantityValue = quantityValue;
        return this;
    }
}
