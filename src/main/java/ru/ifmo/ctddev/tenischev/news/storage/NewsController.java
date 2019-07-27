package ru.ifmo.ctddev.tenischev.news.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.ctddev.tenischev.news.storage.dto.NewsDTO;

import ru.ifmo.ctddev.tenischev.news.storage.entity.News;

/**
 * This class is controller of news storage.
 *
 * @author setenish 01.06.2019.
 */
@RestController
@RequestMapping("news")
public class NewsController {

    private static final int MAXIMAL_AMOUNT_OF_NEWS = 10;

    @Autowired
    private NewsRepository newsRepository;

    private ModelMapper mapper = new ModelMapper();

    /**
     * Gets specified amount latest news out of storage.
     * Maximal allowed amount is {@link #MAXIMAL_AMOUNT_OF_NEWS}.
     *
     * @param amount requested amount of news
     * @return list with latest news
     */
    @GetMapping(value = "/{amount}", produces = "application/json")
    @Transactional(readOnly = true)
    public List<NewsDTO> getNews(@PathVariable int amount) {
        if (amount < 1) {
            return Collections.emptyList();
        }
        amount = Math.min(amount, MAXIMAL_AMOUNT_OF_NEWS);
        try (Stream<News> stream = newsRepository.streamAllByOrderByTimeDesc()) {
            return stream
                    .filter(n -> n.getExpirationDate().after(new Timestamp(System.currentTimeMillis())))
                    .limit(amount)
                    .map(e -> mapper.map(e, NewsDTO.class)).collect(Collectors.toList());
        }
    }

    /**
     * Gets latest 5 news out of storage.
     *
     * @return list of latest 5 news
     */
    @GetMapping(produces = "application/json")
    @Transactional(readOnly = true)
    public List<NewsDTO> getNews() {
        return getNews(5);
    }

    /**
     * Adds new news to storage.
     *
     * @param news the news
     */
    @PutMapping("/add")
    @Transactional
    public void addNews(@RequestBody NewsDTO news) {
        news.setId(null);
        news.setTime(null);
        if (news.getExpirationDate() == null) {
            news.setExpirationDate(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        }
        newsRepository.save(mapper.map(news, News.class));
    }

    /**
     * Removes news from storage by id.
     *
     * @param id the id of news for removal
     */
    @DeleteMapping("/remove/{id}")
    @Transactional
    public void removeNews(@PathVariable long id) {
        newsRepository.deleteById(id);
    }
}
