package de.MCmoderSD.giphy.enums;

/**
 * Enum representing the various endpoints of the Giphy API.
 */
@SuppressWarnings("unused")
public enum Endpoint {

    // Constants
    RANDOM("api.giphy.com/v1/gifs/random"),
    SEARCH("api.giphy.com/v1/gifs/search"),
    TRENDING("api.giphy.com/v1/gifs/trending");

    // Attributes
    private final String endpoint;

    /**
     * Constructs an Endpoint enum with the specified endpoint URL.
     *
     * @param endpoint the endpoint URL
     */
    Endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Returns the endpoint URL.
     *
     * @return the endpoint URL
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Returns the full URL of the endpoint.
     *
     * @return the full URL of the endpoint
     */
    public String getUrl() {
        return String.format("https://%s", endpoint);
    }

    /**
     * Returns the Endpoint enum corresponding to the specified endpoint URL.
     *
     * @param endpoint the endpoint URL
     * @return the Endpoint enum corresponding to the specified endpoint URL, or null if no match is found
     * @throws IllegalArgumentException if the endpoint is null or empty
     */
    public static Endpoint getEndpoint(String endpoint) {
        if (endpoint == null || endpoint.isBlank()) throw new IllegalArgumentException("Endpoint cannot be null or empty.");
        for (Endpoint e : values()) if (e.getEndpoint().equalsIgnoreCase(endpoint)) return e;
        return null;
    }
}