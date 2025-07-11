package stock.news.AI.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatModel chatModel;

    public String prompt(String prompt) {
        return chatModel.call(prompt);
    }
}
