package de.MCmoderSD.giphy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.MCmoderSD.giphy.data.Gif;
import de.MCmoderSD.giphy.enums.Endpoint;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Giphy is a utility class for interacting with the Giphy API.
 */
@SuppressWarnings("unused")
public class Giphy {

    // Attributes
    private final String apiKey;
    private final HttpClient httpClient;
    private final JsonMapper jsonMapper;

    /**
     * Constructs a new Giphy instance with the provided API key.
     *
     * @param apiKey the Giphy API key
     */
    public Giphy(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.jsonMapper = new JsonMapper();
    }

    /**
     * Queries the Giphy API with the specified parameters.
     *
     * @param apiKey   the Giphy API key
     * @param endpoint the endpoint to query
     * @param query    the search query
     * @param limit    the number of results to return
     * @param offset   the offset for pagination
     * @param language the language code
     * @return an array of Gif objects
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public static Gif[] query(String apiKey, Endpoint endpoint, @Nullable String query, @Nullable Integer limit, @Nullable Integer offset, @Nullable String language) throws IOException, InterruptedException {
        return new Giphy(apiKey).query(endpoint, query, limit, offset, language);
    }

    /**
     * Queries the Giphy API with the specified parameters.
     *
     * @param endpoint the endpoint to query
     * @param query    the search query
     * @param limit    the number of results to return
     * @param offset   the offset for pagination
     * @param language the language code
     * @return an array of Gif objects
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public Gif[] query(Endpoint endpoint, @Nullable String query, @Nullable Integer limit, @Nullable Integer offset, @Nullable String language) throws IOException, InterruptedException {
        return switch (endpoint) {
            case RANDOM -> queryRandom(query);
            case SEARCH -> querySearch(query, limit, offset, language);
            case TRENDING -> queryTrending(limit, offset);
        };
    }

    /**
     * Queries the Giphy API for a random GIF with the specified query.
     *
     * @param apiKey the Giphy API key
     * @param query  the search query
     * @return an array of Gif objects
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public static Gif[] queryRandom(String apiKey, String query) throws IOException, InterruptedException {
        return new Giphy(apiKey).queryRandom(query);
    }

    /**
     * Queries the Giphy API for a random GIF with the specified query.
     *
     * @param query the search query
     * @return an array of Gif objects
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public Gif[] queryRandom(String query) throws IOException, InterruptedException {

        // Check Parameters
        if (query == null || query.isBlank()) throw new IllegalArgumentException("Query cannot be null or empty.");

        // Variables
        Gif[] gifs = new Gif[1];

        // Construct the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Endpoint.RANDOM.getUrl() + "?api_key=" + apiKey + "&tag=" + query.replace(" ", "+")))
                .GET()
                .build();

        // Send the request
        JsonNode jsonNode = sendRequest(request);

        // Parse the response
        gifs[0] = new Gif(jsonNode);
        return gifs;
    }

    /**
     * Queries the Giphy API for GIFs matching the specified search query.
     *
     * @param apiKey   the Giphy API key
     * @param query    the search query
     * @param limit    the number of results to return
     * @param offset   the offset for pagination
     * @param language the language code
     * @return an array of Gif objects
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public static Gif[] querySearch(String apiKey, String query, @Nullable Integer limit, @Nullable Integer offset, @Nullable String language) throws IOException, InterruptedException {
        return new Giphy(apiKey).querySearch(query, limit, offset, language);
    }

    /**
     * Queries the Giphy API for GIFs matching the specified search query.
     *
     * @param query    the search query
     * @param limit    the number of results to return
     * @param offset   the offset for pagination
     * @param language the language code
     * @return an array of Gif objects
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public Gif[] querySearch(String query, @Nullable Integer limit, @Nullable Integer offset, @Nullable String language) throws IOException, InterruptedException {

        // Set default values
        if (limit == null) limit = 25;
        if (offset == null) offset = 0;
        if (language == null) language = "en";

        // Check Parameters
        if (query == null || query.isBlank()) throw new IllegalArgumentException("Query cannot be null or empty.");
        if (limit < 1 || limit > 50) throw new IllegalArgumentException("Limit must be between 1 and 50.");
        if (offset < 0 || offset > 499) throw new IllegalArgumentException("Offset must be between 0 and 499.");
        if (language.isBlank() || language.length() != 2) throw new IllegalArgumentException("Language must be a two-letter ISO 639-1 language code.");

        // Variables
        Gif[] gifs = new Gif[limit];

        // Construct the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Endpoint.SEARCH.getUrl() + "?api_key=" + apiKey + "&q=" + query.replace(" ", "+") + "&limit=" + limit + "&offset=" + offset + "&lang=" + language))
                .GET()
                .build();

        // Send the request
        JsonNode jsonNode = sendRequest(request);

        // Parse the response
        for (var i = 0; i < limit; i++) gifs[i] = new Gif(jsonNode.get(i));
        return gifs;
    }

    /**
     * Queries the Giphy API for trending GIFs.
     *
     * @param apiKey the Giphy API key
     * @param limit  the number of results to return
     * @param offset the offset for pagination
     * @return an array of Gif objects
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public static Gif[] queryTrending(String apiKey, @Nullable Integer limit, @Nullable Integer offset) throws IOException, InterruptedException {
        return new Giphy(apiKey).queryTrending(limit, offset);
    }

    /**
     * Queries the Giphy API for trending GIFs.
     *
     * @param limit  the number of results to return
     * @param offset the offset for pagination
     * @return an array of Gif objects
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public Gif[] queryTrending(@Nullable Integer limit, @Nullable Integer offset) throws IOException, InterruptedException {

        // Set default values
        if (limit == null) limit = 25;
        if (offset == null) offset = 0;

        // Check Parameters
        if (limit < 1 || limit > 50) throw new IllegalArgumentException("Limit must be between 1 and 50.");
        if (offset < 0 || offset > 499) throw new IllegalArgumentException("Offset must be between 0 and 499.");

        // Variables
        Gif[] gifs = new Gif[limit];

        // Construct the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Endpoint.TRENDING.getUrl() + "?api_key=" + apiKey + "&limit=" + limit + "&offset=" + offset))
                .GET()
                .build();

        // Send the request
        JsonNode jsonNode = sendRequest(request);

        // Parse the response
        for (var i = 0; i < limit; i++) gifs[i] = new Gif(jsonNode.get(i));
        return gifs;
    }

    /**
     * Sends the HTTP request and returns the response as a JsonNode.
     *
     * @param request the HTTP request
     * @return the response as a JsonNode
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    private JsonNode sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IOException("HTTP " + response.statusCode() + ": " + response.body());
        return jsonMapper.readTree(response.body()).get("data");
    }

    /**
     * Returns the Giphy API key.
     *
     * @return the Giphy API key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Returns the HttpClient instance.
     *
     * @return the HttpClient instance
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Returns the JsonMapper instance.
     *
     * @return the JsonMapper instance
     */
    public JsonMapper getJsonMapper() {
        return jsonMapper;
    }
}