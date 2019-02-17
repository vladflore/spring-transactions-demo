package com.example.demo;

import javax.persistence.*;

@Entity
@Table(name = "TOTAL_QUANTITY")
public class TotalQuantity {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "TOTAL")
    private Long total;

    public TotalQuantity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotal() {
        return total;
    }

    public TotalQuantity setTotal(Long total) {
        this.total = total;
        return this;
    }
}
