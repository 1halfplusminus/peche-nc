package nc.si2p.peche.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.ApplicationArguments;

import nc.si2p.peche.domain.enumeration.DCPEtat;
import nc.si2p.peche.repository.DCPRepository;
import nc.si2p.peche.service.mapper.DCPEtatDeserializer;

// @RunWith(SpringRunner.class)
// @SpringBootTest(classes = PechencApp.class)
public class DCPDataLoaderTest {
    private ObjectMapper mapper;
    private DCPDataLoader dataLoader;
    @Before
    public void setup() {
        mapper = new ObjectMapper();
        dataLoader = new DCPDataLoader(mock(DCPRepository.class));
    }
    @Test
    public void testDeserializeDCP() throws Exception {
       dataLoader.run(mock(ApplicationArguments.class));
    }
}
