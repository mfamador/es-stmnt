package com.github.mfamador.saa.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
class KeyPhrase {

    private String keyPhrase;
    private long count;
}
