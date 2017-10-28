package com.github.mfamador.saa.model;

import lombok.Value;

@Value
public class SearchRequest {

    private int from;
    private Integer size = 50;
    private String query;
    private String sentiment;
    private Integer cloudSize = 5;
}
