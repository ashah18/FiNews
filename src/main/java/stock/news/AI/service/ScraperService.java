package stock.news.AI.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stock.news.AI.model.Article;
import stock.news.AI.model.Scraper;
import stock.news.AI.repository.ArticleRepository;

@Service
public class ScraperService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Scraper> scrapeArticleBodies(String ticker) {
        List<Article> articles = articleRepository.findAllByTicker(ticker);
        List<Scraper> scraped = new ArrayList<>();

        for (Article article : articles) {
            String bodyText = "";
            try {
                Document doc = Jsoup.connect(article.getArticle_url())
                                     .userAgent("Mozilla/5.0 (jsoup)")
                                     .timeout(10_000)
                                     .get();

                // 1) try <article>
                if (!doc.select("article").isEmpty()) {
                    bodyText = doc.select("article").text();

                // 2) fallback to any div[class*='content']
                } else if (!doc.select("div[class*=content]").isEmpty()) {
                    bodyText = doc.select("div[class*=content]").text();

                // 3) last‐ditch: all paragraphs
                } else {
                    bodyText = doc.select("p").text();
                }

            } catch (IOException e) {
                System.out.println("Failed to fetch article body: " + article.getTitle());
                // failed to fetch or parse; leave bodyText empty or log as needed
            }

            scraped.add(new Scraper(
                article.getId(),
                article.getArticle_url(),
                bodyText
            ));
        }

        return scraped;
    }
}
