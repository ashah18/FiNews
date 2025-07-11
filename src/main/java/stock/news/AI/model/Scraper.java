package stock.news.AI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scraper {
    @Id
    private String id;

    private String ticker;

    private String url;

    @Lob
    @Column(nullable = true)
    private String bodyText;
}
