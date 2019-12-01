package com.moreti.kmoretimsscbreweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//@Component
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer connectionTimeout;
    private final Integer ioThreadCount;
    private final Integer soTimeout;
    private final Integer defaultMaxPerRoute;
    private final Integer maxTotal;

    public NIORestTemplateCustomizer(@Value("${km.http.nio.rest.connectiontimeout}") Integer connectionTimeout,
                                     @Value("${km.http.nio.rest.iothreadcount}") Integer ioThreadCount,
                                     @Value("${km.http.nio.rest.sotimeout}") Integer soTimeout,
                                     @Value("${km.http.nio.rest.defaultmaxperroute}") Integer defaultMaxPerRoute,
                                     @Value("${km.http.nio.rest.maxtotal}") Integer maxTotal) {
        this.connectionTimeout = connectionTimeout;
        this.ioThreadCount = ioThreadCount;
        this.soTimeout = soTimeout;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        this.maxTotal = maxTotal;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {
        final DefaultConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(
                IOReactorConfig
                        .custom()
                .setConnectTimeout(3000)
                .setIoThreadCount(4)
                .setSoTimeout(3000)
                .build()
        );

        final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connectionManager.setDefaultMaxPerRoute(100);
        connectionManager.setMaxTotal(1000);

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients
                .custom()
                .setConnectionManager(connectionManager)
                .build();

        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        try {
            restTemplate.setRequestFactory(this.clientHttpRequestFactory());
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
    }
}
