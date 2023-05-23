package com.example.mkdown_java.MkDownElasticSearch;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;

/**
 * ElasticSearch 客户端配置
 *
 * @author geng
 * 2020/12/19
 */
@Configuration
public class RestClientConfig extends ElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        HttpHeaders compatibilityHeaders = new HttpHeaders();
        compatibilityHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
        compatibilityHeaders.add("Content-Type", "application/vnd.elasticsearch+json;"
                + "compatible-with=7");
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .withProxy("localhost:8081")
                .withBasicAuth("elastic","tNg*=qwUN7lb7e3mj-*m")
                .withDefaultHeaders(compatibilityHeaders)    // this variant for imperative code
                .withHeaders(() -> compatibilityHeaders)     // this variant for reactive code
                .build();
    }
}
