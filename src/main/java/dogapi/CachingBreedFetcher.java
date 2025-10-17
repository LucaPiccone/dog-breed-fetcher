package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private BreedFetcher fetcher;
    private static HashMap<String, List<String>> cacheMap = new HashMap<>();

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (cacheMap.containsKey(breed)) {
            if (callsMade == 0)
                callsMade++;
            return cacheMap.get(breed);
        }
        callsMade++;
        List<String> subBreeds = fetcher.getSubBreeds(breed);
        cacheMap.put(breed, subBreeds);
        return cacheMap.get(breed);
    }

    public int getCallsMade() {
        return callsMade;
    }
}
// you initialize a CachingBreedFetcher(DogAPIBreedFetcher("hound"))
// if DogAPIFetcher returns 404, do nothing, else add 1 to count