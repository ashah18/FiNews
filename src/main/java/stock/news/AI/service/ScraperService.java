package stock.news.AI.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stock.news.AI.model.Article;
import stock.news.AI.model.Scraper;
import stock.news.AI.repository.ArticleRepository;
import stock.news.AI.repository.ScraperRepository;

@Service
public class ScraperService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ScraperRepository scraperRepository;

    @Transactional
    public List<Scraper> scrapeArticleBodies(String ticker) {
        scraperRepository.deleteAllByTicker(ticker);
        List<Article> articles = articleRepository.findAllByTicker(ticker);
        List<Scraper> scraped = new ArrayList<>();

        for (Article article : articles) {
            String bodyText = "";
            try {
                Document doc = Jsoup.connect(article.getArticle_url())
                                     .userAgent("Mozilla/5.0 (jsoup)")
                                     .timeout(10_000)
                                     .get();

                bodyText = extractLargestTextBlock(doc);

                // fallback if nothing found
                if (bodyText.isBlank()) {
                    bodyText = doc.body().select("p").text();
                }

                // 1) try <article>
                // if (!doc.select("article").isEmpty()) {
                //     bodyText = doc.select("article").text();

                // // 2) fallback to any div[class*='content']
                // } else if (!doc.select("div[class*=content]").isEmpty()) {
                //     bodyText = doc.select("div[class*=content]").text();

                // // 3) last‐ditch: all paragraphs
                // } else {
                //     bodyText = doc.select("p").text();
                // }

            } catch (IOException e) {
                System.out.println("Failed to fetch article body: " + article.getTitle());
                // failed to fetch or parse; leave bodyText empty or log as needed
            }

            scraped.add(new Scraper(
                article.getId(),
                article.getTicker(),
                article.getArticle_url(),
                bodyText
            ));
        }

        System.out.println("Scraped " + scraped.size());
        return scraperRepository.saveAll(scraped);
    }

    private String extractLargestTextBlock(Document doc) {
        // TODO Auto-generated method stub
        Elements candidates = doc.select("article, div");
        Element best = null;
        int maxWords = 0;

        for (Element el : candidates) {
            String text = el.text().trim();
            if (text.isEmpty()) continue;

            int wordCount = text.split("\\s+").length;
            if (wordCount > maxWords) {
                maxWords = wordCount;
                best = el;
            }
        }

        return (best != null) ? best.text() : "";
    }
}
