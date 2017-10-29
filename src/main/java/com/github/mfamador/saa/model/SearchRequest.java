package com.github.mfamador.saa.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRequest {

    private int from;
    private Integer size;
    private String query;
    private String sentiment;
    private Integer cloud;

    public Integer getSize() {
        return size != null ? size : 25;
    }

    public Integer getCloud() {
        return cloud != null ? cloud : 5;
    }
}
