package com.mrasband.yab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Yet Another Bot, well really - a Slack App with Bot-Like tendencies and support
 *
 * @author matt.rasband
 */
@SpringBootApplication
public class YabApp {
    public static void main(String[] args) {
        SpringApplication.run(YabApp.class, args);
    }
}
