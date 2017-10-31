package com.github.mfamador.saa;

import com.github.mfamador.saa.service.DocumentService;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@Log4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentServiceImplTest {

    @Autowired
    private DocumentService service;

    @Test
    public void DocumentServiceInjectionTest() {

        log.debug(this.service.getClass().toString());

        assertEquals("class com.github.mfamador.saa.service.impl.DocumentServiceImpl",
          this.service.getClass().toString());
    }
}