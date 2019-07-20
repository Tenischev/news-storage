package ru.ifmo.ctddev.tenischev.news.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is simple application to host DB with news.<br/>
 * This application provides REST API to add, delete or read news.
 *
 * @author setenish 01.06.2019.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
