package ru.ifmo.ctddev.tenischev.news.storage;

import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import ru.ifmo.ctddev.tenischev.news.storage.entity.News;

/**
 * This class is DAO for news storage.
 *
 * @author setenish 01.06.2019.
 */
public interface NewsRepository extends CrudRepository<News, Long> {

    /**
     * Read all news ordered by time.
     *
     * @return all news ordered by time.
     */
    Stream<News> streamAllByOrderByTimeDesc();
}
