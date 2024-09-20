package com.example.socialscraper.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class WebsiteScraperService {


    @Value("${target.website.url}")
    private String websiteUrl;

    public String scrapeWebsiteForTwitterLink() {
        log.info("Starting to scrape the website: {}", websiteUrl);

        try {
            Document doc = Jsoup.connect(websiteUrl).get();
            Elements links = doc.select("a[href]");  // Select all <a> tags

            for (Element link : links) {
                String url = link.attr("href");

                // Look for Twitter/X URLs
                if (url.contains("twitter.com") || url.contains("x.com")) {
                    log.info("Found Twitter link: {}", url);
                    return url;
                }
            }
            log.warn("No Twitter link found on the website.");
        } catch (IOException e) {
            log.error("Error occurred while scraping the website: {}", e.getMessage());
        }

        return null;
    }
}
