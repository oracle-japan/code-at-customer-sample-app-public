package com.oracle.jp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Item.
 * 
 * @author Shuhei Kawamura
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

}
