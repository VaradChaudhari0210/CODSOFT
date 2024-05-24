package com.example.motivational_quotes.Quotes;

public class Saved_quote_model {
    String quote = "";
    String author = "";

    public Saved_quote_model() {}

    public Saved_quote_model( String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
