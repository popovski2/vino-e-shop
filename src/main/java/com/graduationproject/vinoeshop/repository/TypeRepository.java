package com.graduationproject.vinoeshop.repository;

import com.graduationproject.vinoeshop.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    List<Type> findAllByCategory_Id(Long categoryId);

}
