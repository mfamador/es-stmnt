package com.github.mfamador.saa.model;

import lombok.Value;

@Value
public class SAARequest {

    private int from;
    private int size;
    private String query;
    private String sentiment;
}
