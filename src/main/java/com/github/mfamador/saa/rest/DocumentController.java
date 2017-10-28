package com.github.mfamador.saa.rest;

import com.github.mfamador.saa.model.SearchRequest;
import com.github.mfamador.saa.model.SearchResult;
import com.github.mfamador.saa.service.DocumentService;
import com.github.mfamador.saa.service.impl.DocumentServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Log4j
@RestController
@RequestMapping("/saa")
public class DocumentController {

    @Autowired
    private DocumentService service;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest request) {

        log.debug("request: " + request);

        return new ResponseEntity<>(service.find(request), HttpStatus.CREATED);
    }
}
