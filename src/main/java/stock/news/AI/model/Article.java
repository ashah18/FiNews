package stock.news.AI.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    @Id
    private String id;

    @Lob
    @Column(nullable = true)
    private String title;
    @Lob
    @Column(nullable = true)
    private String description;

    private String article_url;

    private String ticker;
}