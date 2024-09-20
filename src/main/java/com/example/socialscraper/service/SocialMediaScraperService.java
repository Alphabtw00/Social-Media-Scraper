package com.example.socialscraper.service;

import com.example.socialscraper.TwitterFeignClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class SocialMediaScraperService {


    private TwitterFeignClient twitterFeignClient;
    private ObjectMapper objectMapper;

    public SocialMediaScraperService(TwitterFeignClient twitterFeignClient, ObjectMapper objectMapper) {
        this.twitterFeignClient = twitterFeignClient;
        this.objectMapper = objectMapper;
    }

    @Value("${twitter.auth.token}")
    private String twitterAuthToken;




    public boolean checkTwitterActivity(String twitterId) {
        log.info("Starting to scrape Twitter for user: {}", twitterId);

        try {
            ResponseEntity<String> response = twitterFeignClient.getUserTweets(twitterId, 10, "Bearer " + twitterAuthToken);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully fetched tweets for user: {}", twitterId);
                log.debug("Twitter API response: {}", response.getBody());

                JsonNode rootNode = objectMapper.readTree(response.getBody());
                JsonNode tweetsArray = rootNode.get("data");

                if (tweetsArray != null && tweetsArray.isArray()) {
                    for (JsonNode tweetNode : tweetsArray) {
                        String tweetDateStr = tweetNode.get("created_at").asText();
                        LocalDateTime tweetDate = LocalDateTime.parse(tweetDateStr, DateTimeFormatter.ISO_DATE_TIME);

                        if (tweetDate.isAfter(LocalDateTime.now().minusMonths(6))) {
                            log.info("User {} has been active within the last 6 months.", twitterId);
                            return true;
                        }
                    }
                }

                log.info("No activity found for user {} in the last 6 months.", twitterId);
            } else {
                log.error("Failed to fetch tweets for user {}. Status: {}", twitterId, response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error occurred while checking Twitter activity for user {}: {}", twitterId, e.getMessage());
        }

        return false;
    }
}
