package stock.news.AI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stock.news.AI.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
    
}
