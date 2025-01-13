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