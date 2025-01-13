package de.MCmoderSD.giphy.enums;

@SuppressWarnings("unused")
public enum Endpoint {

    // Constants
    RANDOM("api.giphy.com/v1/gifs/random"),
    SEARCH("api.giphy.com/v1/gifs/search"),
    TRENDING("api.giphy.com/v1/gifs/trending");

    // Attributes
    private final String endpoint;

    // Constructor
    Endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    // Methods
    public String getEndpoint() {
        return endpoint;
    }

    public String getUrl() {
        return String.format("https://%s", endpoint);
    }

    public static Endpoint getEndpoint(String endpoint) {
        if (endpoint == null || endpoint.isBlank()) throw new IllegalArgumentException("Endpoint cannot be null or empty.");
        for (Endpoint e : values()) if (e.getEndpoint().equalsIgnoreCase(endpoint)) return e;
        return null;
    }
}
