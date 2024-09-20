# Social Media Scraper

## Overview

The Social Media Scraper is a Java application designed to scrape social media links (Twitter and Instagram) from a given website and check the activity status of these accounts. It uses JSoup for web scraping and Feign clients to interact with social media APIs.

## Features

- **Scrapes Websites**: Extracts social media links from a given URL.
- **Checks Activity**: Determines if Twitter and Instagram accounts have posted in the last six months.
- **Logs Information**: Provides detailed logs of scraping and API interactions.

## Prerequisites

Before running the application, ensure you have:

1. **Java Development Kit (JDK) 17** or higher.
2. **Maven** for dependency management.

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/social-scraper.git
cd social-scraper
```

### 2. Configure the Application

To set up the configuration:

1. Create a `application.properties` file in the `src/main/resources` directory.
2. Add the following content to the file:

```properties
twitter.auth.token=${TWITTER_BEARER_TOKEN}
#Use any website you want to check
target.website.url=https://myanimelist.net/

```

3. Replace `${TWITTER_BEARER_TOKEN}` with your actual API tokens.

### 3. Install Dependencies

Run the following command to install the required dependencies:

```bash
mvn clean install
```

## Usage

To run the application, use the following command:

```bash
mvn spring-boot:run
```

The application will start scraping the configured URL and checking the activity status of found social media accounts.

## Configuration

You can modify the following configuration in the `config.properties` file:

- `twitter.auth.token`: Your Twitter API bearer token.
- `target.website.url`: The URL of the website you want to scrape for social media links.

## Output

The application will generate logs with information about:

- Scraped social media links
- Extracts username from the social media links
- Activity status of each account on each social media
- Any errors encountered during the process

