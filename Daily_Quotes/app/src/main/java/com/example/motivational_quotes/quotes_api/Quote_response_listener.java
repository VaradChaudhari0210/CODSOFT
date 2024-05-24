package com.example.motivational_quotes.quotes_api;

import com.example.motivational_quotes.Quotes.Quote_model;

import java.util.List;

public interface Quote_response_listener {
    void didfetch(List<Quote_model> responses,String msg);
    void diderror(String msg);
}
