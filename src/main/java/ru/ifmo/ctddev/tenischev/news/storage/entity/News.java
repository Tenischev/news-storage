package ru.ifmo.ctddev.tenischev.news.storage.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * This class is entity representation of News table.
 *
 * @author setenish 01.06.2019.
 */
@Data
@Entity
public class News {

    /**
     * The id of news.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    /**
     * The topic or title of news.
     */
    private String topic;

    /**
     * The text of news.
     */
    private String text;

    /**
     * The creation time of news.
     */
    @CreationTimestamp
    private Timestamp time;

    /**
     * The date when news become not relevant.
     */
    private Timestamp expirationDate;

    /**
     * The publisher name of news.
     */
    private String publisher;
}
