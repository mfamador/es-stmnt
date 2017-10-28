package com.github.mfamador.saa.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class KeyPhrase {

    private String keyPhrase;
    private long count;
}
