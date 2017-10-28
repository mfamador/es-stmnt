package com.github.mfamador.saa.service;

import com.github.mfamador.saa.model.SearchRequest;
import com.github.mfamador.saa.model.SearchResult;

public interface DocumentService {

    SearchResult find(SearchRequest request);
}
