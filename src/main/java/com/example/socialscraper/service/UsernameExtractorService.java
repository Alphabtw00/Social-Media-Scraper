package com.example.socialscraper.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsernameExtractorService {

    public String extractUsernameFromTwitterLink(String twitterUrl) {
        log.info("Extracting username from Twitter link: {}", twitterUrl);

        if (twitterUrl != null && (twitterUrl.contains("twitter.com") || twitterUrl.contains("x.com"))) {
            String[] parts = twitterUrl.split("/");
            if (parts.length > 3) {
                String username = parts[3];  // Extract the username from the URL
                log.info("Extracted username: {}", username);
                return username;
            }
        }

        log.warn("Unable to extract username from Twitter link.");
        return null;
    }
}