package za.ac.cput.domain;
/*
Category.java
Category POJO class
Author: Abethu Ngxitho 221297820
Date: 07 May 2025
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "category")
public class Category {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;

   protected Category() {

    }

    private Category(Builder builder) {
        this.categoryId = builder.categoryId;
        this.name = builder.name;
        this.description = builder.description;

    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {return description;}


    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +

                '}';
    }

    public static class Builder{
        private Long categoryId;
        private String name;
        private String description;


        public Builder setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }



        public Builder copy(Category category) {
            this.categoryId = category.categoryId;
            this.name = category.name;
            this.description = category.description;

            return this;
        }

        public Category build(){

            return new Category(this);
        }
    }
}
