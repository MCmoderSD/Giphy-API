package de.MCmoderSD.giphy.data;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;

/**
 * Represents a GIF object with various attributes such as id, URL, slug, bitly URL, embed URL, and title.
 */
@SuppressWarnings("unused")
public class Gif {

    // Attributes
    private final String id;
    private final URI url;
    private final String slug;
    private final URI bitlyUrl;
    private final URI embedUrl;
    private final String title;

    /**
     * Constructs a Gif object with the specified attributes.
     *
     * @param id       the unique identifier of the GIF
     * @param url      the URL of the GIF
     * @param slug     the slug of the GIF
     * @param bitlyUrl the Bitly URL of the GIF
     * @param embedUrl the embed URL of the GIF
     * @param title    the title of the GIF
     */
    public Gif(String id, URI url, String slug, URI bitlyUrl, URI embedUrl, String title) {
        this.id = id;
        this.url = url;
        this.slug = slug;
        this.bitlyUrl = bitlyUrl;
        this.embedUrl = embedUrl;
        this.title = title;
    }

    /**
     * Constructs a Gif object from a JsonNode.
     *
     * @param jsonNode the JsonNode containing the GIF data
     */
    public Gif(JsonNode jsonNode) {
        id = jsonNode.get("id").asText();
        url = URI.create(jsonNode.get("url").asText());
        slug = jsonNode.get("slug").asText();
        bitlyUrl = URI.create(jsonNode.get("bitly_url").asText());
        embedUrl = URI.create(jsonNode.get("embed_url").asText());
        title = jsonNode.get("title").asText();
    }

    /**
     * Returns the unique identifier of the GIF.
     *
     * @return the unique identifier of the GIF
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the URL of the GIF.
     *
     * @return the URL of the GIF
     */
    public URI getUrl() {
        return url;
    }

    /**
     * Returns the slug of the GIF.
     *
     * @return the slug of the GIF
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Returns the Bitly URL of the GIF.
     *
     * @return the Bitly URL of the GIF
     */
    public URI getBitlyUrl() {
        return bitlyUrl;
    }

    /**
     * Returns the embed URL of the GIF.
     *
     * @return the embed URL of the GIF
     */
    public URI getEmbedUrl() {
        return embedUrl;
    }

    /**
     * Returns the title of the GIF.
     *
     * @return the title of the GIF
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the media source URL of the GIF.
     *
     * @return the media source URL of the GIF
     */
    public URI getMediaSource() {
        return URI.create("https://i.giphy.com/" + id + ".webp");
    }
}