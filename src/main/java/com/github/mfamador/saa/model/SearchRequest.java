package com.github.mfamador.saa.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRequest {

    private int from;
    private Integer size = 25;
    private String query;
    private String sentiment;
    private Integer cloud = 10;
}
