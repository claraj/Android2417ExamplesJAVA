package com.clara.wordsapi;

public class Definition {
    String definition;
    String image_url;
    String example;    // example sentence
    String type;   // noun, verb, adjective...

    @Override
    public String toString() {
        return "Definition{" +
                "definition='" + definition + '\'' +
                ", image_url='" + image_url + '\'' +
                ", example='" + example + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}


