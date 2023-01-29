package com.example.demo.repo;

import com.example.demo.entity.Coin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface CoinRepo extends CrudRepository<Coin, Integer> {

    @Query(value = "select * from coin where currency = ?1 ", nativeQuery = true)
    Coin findByCurrency(String currency);

    @Transactional
    @Modifying
    @Query(value = "delete from coin where currency = ?1 ", nativeQuery = true)
    void deleteByCurrency(String currency);

}
