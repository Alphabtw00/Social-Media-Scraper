package com.example.socialscraper;

import com.example.socialscraper.service.SocialMediaScraperService;
import com.example.socialscraper.service.UsernameExtractorService;
import com.example.socialscraper.service.WebsiteScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("social")
@Slf4j
public class SocialMediaScraperController {


    private WebsiteScraperService websiteScraperService;


    private UsernameExtractorService twitterUsernameExtractorService;

    private SocialMediaScraperService socialMediaScraperService;

    public SocialMediaScraperController(WebsiteScraperService websiteScraperService,
                                 UsernameExtractorService twitterUsernameExtractorService,
                                 SocialMediaScraperService socialMediaScraperService) {
        this.websiteScraperService = websiteScraperService;
        this.twitterUsernameExtractorService = twitterUsernameExtractorService;
        this.socialMediaScraperService = socialMediaScraperService;
    }

    @GetMapping("/check-website-twitter-activity")
    public ResponseEntity<String> checkWebsiteTwitterActivity() {
        log.info("Received request to check Twitter activity by scraping website.");

        String twitterUrl = websiteScraperService.scrapeWebsiteForTwitterLink();
        if (twitterUrl == null) {
            log.warn("No Twitter link found on the website.");
            return ResponseEntity.ok("No Twitter link found on the website.");
        }

        String twitterUsername = twitterUsernameExtractorService.extractUsernameFromTwitterLink(twitterUrl);
        if (twitterUsername == null) {
            log.warn("Could not extract Twitter username from the link: {}", twitterUrl);
            return ResponseEntity.ok("Could not extract Twitter username.");
        }

        boolean isActive = socialMediaScraperService.checkTwitterActivity(twitterUsername);
        if (isActive) {
            log.info("User {} is active on Twitter.", twitterUsername);
            return ResponseEntity.ok("User is active on Twitter.");
        } else {
            log.info("User {} is not active on Twitter.", twitterUsername);
            return ResponseEntity.ok("User is not active on Twitter.");
        }
    }
}
