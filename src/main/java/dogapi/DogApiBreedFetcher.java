package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();
    private static final String API_URL = "https://dog.ceo";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String SUCCESS_CODE = "success";
    private static final String STATUS_CODE = "status";
    private static final String BREEDS = "message";

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed)throws BreedNotFoundException {
        client.newBuilder().build();

        // https://dog.ceo/api/breed/hound/list
        final Request request = new Request.Builder()
                .url(String.format("%s/api/breed/%s/list", API_URL, breed))
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getString(STATUS_CODE).equals(SUCCESS_CODE)) {
                final JSONArray jsonBreeds = responseBody.getJSONArray(BREEDS);
                ArrayList<String> breeds = new ArrayList<>();
                for (Object jsonbreed: jsonBreeds) {
                    breeds.add(jsonbreed.toString());
                }
                return breeds;
            } else {
                throw new BreedNotFoundException("Breed" + breed + "not found.");
            }

        } catch (IOException | JSONException event) {
            throw new BreedNotFoundException(breed);
        }
    }
}