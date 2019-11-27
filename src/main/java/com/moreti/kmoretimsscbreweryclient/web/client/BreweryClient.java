package com.moreti.kmoretimsscbreweryclient.web.client;

import com.moreti.kmoretimsscbreweryclient.web.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "km.brewery")
public class BreweryClient {
    private String beerEndpoint;
    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setBeerEndpoint(String beerEndpoint) {
        this.beerEndpoint = beerEndpoint;
    }

    public BeerDto getBeerById(UUID uuid) {
        return restTemplate.getForObject(beerEndpoint + "/" + uuid, BeerDto.class);
    }

    public URI saveNewBeer(BeerDto beerDto) {
        return restTemplate.postForLocation(beerEndpoint, beerDto);
    }

    public void updateBeer(UUID uuid, BeerDto beerDto) {
        restTemplate.put(beerEndpoint + "/" + uuid, beerDto);
    }

    public void deleteBeer(UUID uuid) {
        restTemplate.delete(beerEndpoint + "/" + uuid);
    }
}
