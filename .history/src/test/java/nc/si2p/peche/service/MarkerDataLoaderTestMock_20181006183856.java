package nc.si2p.peche.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.ApplicationArguments;

import nc.si2p.peche.repository.MarkersRepository;

public class MarkerDataLoaderTestMock {
    private MarkerDataLoader dataLoader;
    @Mock
    private MarkersRepository repo;
    @Mock
    private ApplicationArguments argument;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dataLoader = new MarkerDataLoader(repo);
    }
    @Test
    public void testDeserializeDCP() throws Exception {
       dataLoader.run(argument);
    }
}