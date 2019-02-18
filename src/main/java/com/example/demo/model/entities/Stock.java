package com.example.demo.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "STOCK")
public class Stock {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "STOCK_VALUE")
    private Long stockValue;

    public Stock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockValue() {
        return stockValue;
    }

    public Stock setStockValue(Long stockValue) {
        this.stockValue = stockValue;
        return this;
    }
}
