package com.spring.assignment.model;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {

    @Value("${elasticsearch.host:localhost}")
    public String host;
    @Value("${elasticsearch.port:9200}")
    public int port;

    public String getHost() {
        return host;
    }


    public int getPort() {
        return port;
    }

    @Bean
    public RestHighLevelClient client() {
        RestHighLevelClient client = null;
        try {
            System.out.println("host:" + host + "port:" + port);
            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(host, port, "http")
                    ));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}
