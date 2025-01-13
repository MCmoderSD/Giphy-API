package de.MCmoderSD.giphy.data;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;

@SuppressWarnings("unused")
public class Gif {

    // Attributes
    private final String id;
    private final URI url;
    private final String slug;
    private final URI bitlyUrl;
    private final URI embedUrl;
    private final String title;

    public Gif(String id, URI url, String slug, URI bitlyUrl, URI embedUrl, String title) {
        this.id = id;
        this.url = url;
        this.slug = slug;
        this.bitlyUrl = bitlyUrl;
        this.embedUrl = embedUrl;
        this.title = title;
    }

    public Gif(JsonNode jsonNode) {
        id = jsonNode.get("id").asText();
        url = URI.create(jsonNode.get("url").asText());
        slug = jsonNode.get("slug").asText();
        bitlyUrl = URI.create(jsonNode.get("bitly_url").asText());
        embedUrl = URI.create(jsonNode.get("embed_url").asText());
        title = jsonNode.get("title").asText();
    }

    public String getId() {
        return id;
    }

    public URI getUrl() {
        return url;
    }

    public String getSlug() {
        return slug;
    }

    public URI getBitlyUrl() {
        return bitlyUrl;
    }

    public URI getEmbedUrl() {
        return embedUrl;
    }

    public String getTitle() {
        return title;
    }

    public URI getMediaSource() {
        return URI.create("https://i.giphy.com/" + id + ".webp");
    }
}