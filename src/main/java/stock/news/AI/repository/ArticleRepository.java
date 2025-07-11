package stock.news.AI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stock.news.AI.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
    List<Article> findAllByTicker(String ticker);

    @Modifying
    @Transactional
    void deleteAllByTicker(String ticker);
}
