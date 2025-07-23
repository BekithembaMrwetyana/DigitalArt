package domain;
/*
Category.java
Category POJO class
Author: Abethu Ngxitho 221297820
Date: 07 May 2025
*/

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String categoryID;
    private String name;
    private String description;

   protected Category() {

    }

    private Category(Builder builder) {
        this.categoryID = builder.categoryID;
        this.name = builder.name;
        this.description = builder.description;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryID='" + categoryID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    public static class Builder{
        private String categoryID;
        private String name;
        private String description;

        public Builder setCategoryID(String categoryID) {
            this.categoryID = categoryID;
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
            this.categoryID = category.categoryID;
            this.name = category.name;
            this.description = category.description;
            return this;
        }

        public Category build(){

            return new Category(this);
        }
    }
}
