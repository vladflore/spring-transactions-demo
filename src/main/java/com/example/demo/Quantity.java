package com.example.demo;

import javax.persistence.*;

@Entity
@Table(name = "QUANTITIES")
public class Quantity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "QUANTITY")
    private Long quantity;

    public Quantity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Quantity setQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }
}
