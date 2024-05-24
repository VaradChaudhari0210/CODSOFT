package com.example.motivational_quotes.Categories;

import java.util.ArrayList;

public class Category_model {
    ArrayList<String> categories;

    public Category_model() {
        //setting the list of all available categories of quotes
        categories = new ArrayList<>();
        this.categories.add(0,"Anxiety");
        this.categories.add(1,"Change");
        this.categories.add(2,"Choice");
        this.categories.add(3,"Confidence");
        this.categories.add(4,"Courage");
        this.categories.add(5,"Death");
        this.categories.add(6,"Dreams");
        this.categories.add(7,"Excellence");
        this.categories.add(8,"Failure");
        this.categories.add(9,"Fairness");
        this.categories.add(10,"Fear");
        this.categories.add(11,"Forgiveness");
        this.categories.add(12,"Freedom");
        this.categories.add(13,"Future");
        this.categories.add(14,"Happiness");
        this.categories.add(15,"Inspiration");
        this.categories.add(16,"Kindness");
        this.categories.add(17,"Leadership");
        this.categories.add(18,"Life");
        this.categories.add(19,"Love");
    }
    public ArrayList<String> getCategories() {
        return categories;
    }
}
