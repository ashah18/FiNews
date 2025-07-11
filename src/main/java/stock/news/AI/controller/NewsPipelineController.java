package stock.news.AI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stock.news.AI.service.NewsPipelineService;

@RestController
@RequestMapping("/news")
public class NewsPipelineController {
    @Autowired
    private NewsPipelineService newsPipelineService;

    @GetMapping("")
    public void getNews(@RequestParam String ticker) {
        newsPipelineService.getNews(ticker);
    }
}
