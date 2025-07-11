package stock.news.AI.service;

import java.util.List;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import stock.news.AI.model.Scraper;
import stock.news.AI.repository.ScraperRepository;

@Service
@AllArgsConstructor
public class ChatService {
    @Autowired
    private ScraperRepository scraperRepository;

    private final ChatModel chatModel;

    public String prompt(String ticker) {
        List<Scraper> scraped = scraperRepository.findAllByTicker(ticker);
        StringBuilder prompt = new StringBuilder(
                "You are a top-tier financial analyst.  " +
                        "Your job is to read raw scraped news about a single stock, " +
                        "perform sentiment analysis on each article, " +
                        "and then produce:\n" +
                        "  1. A concise high-level summary of what’s happening with the company based on all of the raw scraped news.\n" +
                        "  2. A per-article sentiment score (Positive / Neutral / Negative).\n" +
                        "  3. An overall sentiment trend.\n" +
                        "  4. Contextual interpretation: what does this news and sentiment mean " +
                        "for investors over the next 1–3 months?");

        prompt.append("Company ticker: ").append(ticker).append("\n\n");
        prompt.append("Here are the scraped news articles:\n\n");
        for (int i = 0; i < scraped.size(); i++) {
            Scraper a = scraped.get(i);
            prompt
                    .append("Article ").append(i + 1).append("):\n")
                    .append(a.getBodyText()).append("\n\n");
            prompt.append(
                    "Please structure your response in JSON with these fields:\n" +
                            "{\n" +
                            "  \"ticker\": string,\n" +
                            "  \"summary\": string,\n" +
                            "  \"articles\": [\n" +
                            "    {\"id\": string, \"sentiment\": \"Positive|Neutral|Negative\"}\n" +
                            "  ],\n" +
                            "  \"overall_sentiment\": \"Positive|Neutral|Negative\",\n" +
                            "  \"outlook\": string\n" +
                            "}");

        }

        return chatModel.call(prompt.toString());
    }
}
