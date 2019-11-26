package com.moreti.kmoretimsscbreweryclient.web.client;

import com.moreti.kmoretimsscbreweryclient.web.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "km.brewery", ignoreUnknownFields = false)
public class BreweryClient {
    private String apiHost;
    private String beerEndpoint;
    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public void setBeerEndpoint(String beerEndpoint) {
        this.beerEndpoint = beerEndpoint;
    }

    public BeerDto getBeerById(UUID id) {
        return restTemplate.getForObject(apiHost + beerEndpoint + "/" + id, BeerDto.class);
    }

    public URI saveNewBeer(BeerDto beerDto) {
        return restTemplate.postForLocation(apiHost + beerEndpoint, beerDto);
    }

    public void updateBeer(UUID id, BeerDto beerDto) {
        restTemplate.put(apiHost + beerEndpoint + "/" + id, beerDto);
    }
}
