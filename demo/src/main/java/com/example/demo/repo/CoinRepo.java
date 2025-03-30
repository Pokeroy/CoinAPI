package com.example.demo.repo;

import com.example.demo.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CoinRepo extends JpaRepository<Coin, Long> {

    Coin findByCurrency(String currency);
    
    @Transactional
    void deleteByCurrency(String currency);
}
