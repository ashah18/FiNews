package stock.news.AI.service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import stock.news.AI.model.Article;

@Service
public class ArticleService {
    
    @Value("${polygon.api.key}")
    private String apiKey;
    @Value("${polygon.api.url}")
    private String apiUrl;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate rest;

    private final String publishedUtcGte = DateTimeFormatter.ISO_INSTANT
            .format(Instant.now().minus(14, ChronoUnit.DAYS));;

    public List<Article> getArticles(String ticker) {
        // build URL with all required query params
        String url = String.format(
            "%s?ticker=%s&published_utc.gte=%s&order=desc&limit=10&sort=published_utc&apiKey=%s",
            apiUrl, ticker, publishedUtcGte, apiKey
        );

        String json = rest.getForObject(url, String.class);

        try {
            // 2) pull out the "results" array
            JsonNode resultsNode = objectMapper
                .readTree(json)
                .path("results");

            // 3) convert that array straight into List<Article>
            return objectMapper.convertValue(
                resultsNode,
                new TypeReference<List<Article>>() {}
            );

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing news response", e);
        }
    }
}
