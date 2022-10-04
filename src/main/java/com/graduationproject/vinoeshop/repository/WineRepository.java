package com.graduationproject.vinoeshop.repository;

import com.graduationproject.vinoeshop.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {

    List<Wine> findAllByCategoryName(String categoryName);




}
