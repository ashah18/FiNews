package stock.news.AI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stock.news.AI.model.Scraper;

@Service
public class NewsPipelineService {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ScraperService scraperService;

    public List<Scraper> getNews(String ticker) {
        articleService.getArticles(ticker);
        return scraperService.scrapeArticleBodies(ticker);
    }
}
