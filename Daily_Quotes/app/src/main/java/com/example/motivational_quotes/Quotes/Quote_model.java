package com.example.motivational_quotes.Quotes;

public class Quote_model {
    String q = "";
    String a = "";

    public Quote_model() {}

    public Quote_model(String q, String a) {
        this.q = q;
        this.a = a;
    }

    public void setQuote(String q) {
        this.q = q;
    }

    public String getQuote() {
        return q;
    }

    public void setAuthor(String a) {
        this.a = a;
    }

    public String getAuthor() {
        return a;
    }
}
