package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "coin")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "currency", nullable = false, unique = true)
    private String currency;
    @Column(name = "chineseName", nullable = false)
    private String chineseName;
}
