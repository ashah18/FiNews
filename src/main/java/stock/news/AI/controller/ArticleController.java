package stock.news.AI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stock.news.AI.model.Article;
import stock.news.AI.service.ArticleService;

@RestController
@RequestMapping("/news")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public List<Article> getArticles(
            @RequestParam String ticker) {
        return articleService.getArticles(ticker);
    }
}
