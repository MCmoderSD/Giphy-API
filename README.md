# Giphy-API

## Description
Giphy-API is a Java library that provides an easy way to query GIFs from the [Giphy](https://giphy.com/) API. <br>
You need an API key from Giphy to use this library. You can get one [here](https://developers.giphy.com/dashboard/?create=true).

## Features
It supports the Random, Search, and Trending endpoints from the Giphy API. <br>
You can query up to 50 GIFs at a time. <br>
All data is stored in a `Gif` object, which contains the following information:
- ID
- URL
- Slug
- Bitly URL
- Embed URL
- Title

## Usage

### Maven
Make sure you have my Sonatype Nexus OSS repository added to your `pom.xml` file:
```xml
<repositories>
    <repository>
        <id>Nexus</id>
        <name>Sonatype Nexus</name>
        <url>https://mcmodersd.de/nexus/repository/maven-releases/</url>
    </repository>
</repositories>
```
Add the dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>de.MCmoderSD</groupId>
    <artifactId>Giphy-API</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Usage Example
```java
import de.MCmoderSD.giphy.Giphy;
import de.MCmoderSD.giphy.data.Gif;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        // Create Giphy instance
        Giphy giphy = new Giphy("YOUR_API_KEY");
        Gif[] gif = null;

        // Get random GIF
        try {
            gif = giphy.queryRandom("cat");
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to retrieve random GIF: " + e.getMessage());
        }

        // Print GIF URL
        assert gif != null;
        System.out.println(gif[0].getUrl());

        // Get trending GIFs
        try {
            gif = giphy.queryTrending(5, null);
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to retrieve trending GIFs: " + e.getMessage());
        }

        // Print GIF URLs
        assert gif != null;
        for (Gif g : gif) System.out.println(g.getEmbedUrl());

        // Get GIFs by search term
        try {
            gif = giphy.querySearch("dog", 5, null, "en");
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to retrieve GIFs by search term: " + e.getMessage());
        }

        // Print GIF URLs
        assert gif != null;
        for (Gif g : gif) System.out.println(g.getMediaSource());
    }
}
```