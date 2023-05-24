package com.example.code.repository;

import com.example.code.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order save(Order order);

    @Transactional
    void deleteByScientificName(String scientificName);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.scientificName = :scientificName, o.author = :author, o.publication_year = :publication_year, o.taxon_ancestor_id = :taxon_ancestor_id, o.collecting_method = :collecting_method WHERE o.id = :id")
    int update(@Param("scientificName") String scientificName, @Param("author") String author, @Param("publication_year") Integer publication_year, @Param("id") Integer id, @Param("taxon_ancestor_id") Integer taxon_ancestor_id, @Param("collecting_method") String collecting_method);

    Order findById(int id);

    @Query("SELECT o FROM Order o WHERE o.taxonType = 'Order'")
    List<Order> findAllOrders();
}
