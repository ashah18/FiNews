package stock.news.AI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsPipelineService {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ScraperService scraperService;
    @Autowired
    private ChatService chatService;

    public String getNews(String ticker) {
        articleService.getArticles(ticker);
        scraperService.scrapeArticleBodies(ticker);
        return chatService.prompt(ticker);
    }
}
