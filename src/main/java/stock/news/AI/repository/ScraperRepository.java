package stock.news.AI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stock.news.AI.model.Scraper;

@Repository
public interface ScraperRepository extends JpaRepository<Scraper, String> {
    List<Scraper> findAllByTicker(String ticker);

    @Modifying
    @Transactional
    void deleteAllByTicker(String ticker);
}
