package dogapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void getNumberOfSubBreedsValidBreed() {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();

        try {
            assertEquals(2, Main.getNumberOfSubBreeds("hound", mock));
        } catch (BreedFetcher.BreedNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getNumberOfSubBreedsInvalidBreed() {
        BreedFetcherForLocalTesting mock = new BreedFetcherForLocalTesting();

        try {
            assertEquals(-1, Main.getNumberOfSubBreeds("cat", mock));
        } catch (BreedFetcher.BreedNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void exceptionTypeTest() {
        Exception bfe = new BreedFetcher.BreedNotFoundException("hound");
        assertTrue(bfe instanceof Exception && !(bfe instanceof RuntimeException),
                "BreedFetcher.BreedNotFoundException must be a checked exception.");
    }

}