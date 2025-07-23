package repository;

/*
Category.java
Category Repository
Author: Abethu Ngxitho 221297820
Date: 24 May 2025
*/

import domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository {

    List<Category> findbyName(String name);

    List<Category> findDescription(String description);

}
