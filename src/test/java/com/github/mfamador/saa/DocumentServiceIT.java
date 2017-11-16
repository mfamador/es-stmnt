package com.github.mfamador.saa;

import com.github.mfamador.saa.model.SearchRequest;
import com.github.mfamador.saa.model.SearchResult;
import com.github.mfamador.saa.service.DocumentService;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@Log4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentServiceIT {

    @Autowired
    private DocumentService service;

    @Test
    public void GetAllDocumentTest() {

        SearchResult searchResult = service.find(new SearchRequest(0, 2, "", "", 1));

        assertTrue(searchResult.getCount() > 0);
        assertTrue(searchResult.getDocuments().size() <= 3);
        assertTrue(searchResult.getCloud().size() == 1);
    }

    @Test
    public void GetOnlyDocumentsTest() {

        SearchResult searchResult = service.find(new SearchRequest(0, 2, "", "", 0));

        assertTrue(searchResult.getCount() > 0);
        assertTrue(searchResult.getDocuments().size() <= 3);
        assertTrue(searchResult.getCloud() == null);
    }

    @Test
    public void Get2DocumentsTest() {

        SearchResult searchResult = service.find(new SearchRequest(0, 1, "", "", null));

        assertTrue(searchResult.getCount() > 0);
        assertTrue(searchResult.getDocuments().size() == 1);
    }

    @Test
    public void GetDocumentsNoHitsTest() {

        SearchResult searchResult = service.find(new SearchRequest(0, 1, "xpto-wont-find", "", null));

        assertTrue(searchResult.getCount() == 0);
        assertTrue(searchResult.getDocuments() == null);
    }

    @Test
    public void Get1DocumentCountOnlyTest() {

        SearchResult searchResult = service.find(new SearchRequest(0, 0, "", "", null));

        log.debug(searchResult);
        assertTrue(searchResult.getCount() > 0);
        assertTrue(searchResult.getDocuments() == null);
        assertTrue(searchResult.getCloud() != null);
    }
}
