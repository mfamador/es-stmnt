package com.github.mfamador.saa.service;

import com.github.mfamador.saa.model.SAARequest;
import com.github.mfamador.saa.model.SAAResponse;

public interface DocumentService {

    SAAResponse find(SAARequest request);
}
