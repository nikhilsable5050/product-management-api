package com.zestindia.productmanagement.repository;

import com.zestindia.productmanagement.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}