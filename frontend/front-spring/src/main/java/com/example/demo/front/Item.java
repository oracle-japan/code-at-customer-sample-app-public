package com.example.demo.front;

import java.math.BigDecimal;

public class Item{
    private Integer id;
    private String name;
    private BigDecimal price;
    private String status;

    public Item(){}

    public Item(Integer id, String name, BigDecimal price, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

