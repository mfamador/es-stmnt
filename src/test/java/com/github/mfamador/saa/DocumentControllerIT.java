package com.github.mfamador.saa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mfamador.saa.model.SearchRequest;
import com.github.mfamador.saa.model.SearchResult;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

@Log4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentControllerIT {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;

    @Test
    public void DocumentControllerTest() {

        SearchRequest request = new SearchRequest(0, 1, "another", "m,v,p", 1);

        HttpEntity<SearchRequest> req = new HttpEntity<SearchRequest>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
          createURLWithPort("/saa"),
          HttpMethod.POST, req, String.class);

        assertTrue(response.getStatusCodeValue() < 300);

        ObjectMapper mapper = new ObjectMapper();
        SearchResult result = new SearchResult();
        try {
            result = mapper.readValue(response.getBody(), SearchResult.class);
            log.debug("count: " + result.getCount());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        assertTrue(result.getCount() > 0);
    }

    private String createURLWithPort(String uri) {

        return "http://localhost:" + port + uri;
    }
}
