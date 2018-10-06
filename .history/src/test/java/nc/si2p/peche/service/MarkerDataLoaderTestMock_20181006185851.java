package nc.si2p.peche.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.springframework.boot.ApplicationArguments;

import nc.si2p.peche.domain.Marker;
import nc.si2p.peche.repository.MarkersRepository;

public class MarkerDataLoaderTestMock {
    private MarkerDataLoader dataLoader;
    @Mock
    private MarkersRepository repo;
    @Mock
    private ApplicationArguments argument;
    @Captor
    private ArgumentCaptor<ArrayList<Marker>> captor;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dataLoader = new MarkerDataLoader(repo);
    }
    @Test
    public void testDeserializeDCP() throws Exception {

       dataLoader.run(argument);
       verify(repo).saveAll(captor.capture());
       List<Marker> markers = captor.getValue();
       for (Marker marker : markers) {
        assertThat(marker.getId());
       }
    }
}