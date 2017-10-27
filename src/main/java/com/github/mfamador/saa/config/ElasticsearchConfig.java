package com.github.mfamador.saa.config;

import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Log4j
@Configuration
public class ElasticsearchConfig {

    @Resource
    private Environment environment;

    @Bean
    public TransportClient client() {
        TransportClient client = null;

        log.debug("cluster-name: " + environment.getProperty("elasticsearch.cluster-name"));
        log.debug("host: " + environment.getProperty("elasticsearch.host"));
        log.debug("port: " + environment.getProperty("elasticsearch.port"));

        Settings settings = Settings.builder().put("cluster.name", environment.getProperty("elasticsearch.cluster-name")).build();

        try {
            client = new PreBuiltTransportClient(settings)
              .addTransportAddress(new InetSocketTransportAddress(
                InetAddress.getByName(environment.getProperty("elasticsearch.host")),
                Integer.parseInt(environment.getProperty("elasticsearch.port"))));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return client;
    }

}
