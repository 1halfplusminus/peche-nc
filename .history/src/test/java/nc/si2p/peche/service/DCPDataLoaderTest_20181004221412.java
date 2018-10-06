package nc.si2p.peche.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

import nc.si2p.peche.PechencApp;
import nc.si2p.peche.repository.DCPRepository;
import nc.si2p.peche.service.DCPDataLoader;

// @RunWith(SpringRunner.class)
@SpringBootTest(classes = PechencApp.class)
public class DCPDataLoaderTest {
    @Autowired
    private DCPDataLoader dataLoader;
    @Autowired
    private DCPRepository repo;
    @Before
    public void setup() {
        dataLoader = new DCPDataLoader(repo);
    }
    @Test
    public void testDeserializeDCP() throws Exception {
       dataLoader.run(mock(ApplicationArguments.class));
    }
}
