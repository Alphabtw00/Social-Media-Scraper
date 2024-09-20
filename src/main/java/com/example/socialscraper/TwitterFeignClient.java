package com.example.socialscraper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "twitterClient", url = "https://api.x.com/2")
public interface TwitterFeignClient {

    @GetMapping("/users/{id}/tweets")
    ResponseEntity<String> getUserTweets(@PathVariable("id") String userId,
                                         @RequestParam("max_results") int maxResults,
                                         @RequestHeader("Authorization") String authToken);
}