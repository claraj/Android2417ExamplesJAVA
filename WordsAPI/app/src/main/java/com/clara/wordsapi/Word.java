package com.clara.wordsapi;

import java.util.Arrays;

class Word {
    String word;
    String pronunciation;
    Definition[] definitions;

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", pronunciation='" + pronunciation + '\'' +
                ", definitions=" + Arrays.toString(definitions) +
                '}';
    }
}


