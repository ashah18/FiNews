package stock.news.AI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stock.news.AI.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    
    @GetMapping("/prompt")
    public String prompt(@RequestParam String prompt) {
        return chatService.prompt(prompt);
    }
}
