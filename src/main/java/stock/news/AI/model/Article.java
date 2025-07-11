package stock.news.AI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    @Id
    private String id;

    private String title;
    private String description;
    private String article_url;
}