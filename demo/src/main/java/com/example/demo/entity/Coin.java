package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "coin")
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "currency", nullable = false, unique = true)
    private String currency;
    @Column(name = "chineseName", nullable = false)
    private String chineseName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", chineseName='" + chineseName + '\'' +
                '}';
    }
}
