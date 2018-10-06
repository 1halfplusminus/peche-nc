package nc.si2p.peche.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import nc.si2p.peche.PechencApp;
import nc.si2p.peche.repository.DCPRepository;
import nc.si2p.peche.service.DCPDataLoader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PechencApp.class)
@Transactional
public class DCPDataLoaderTest {
    private DCPDataLoader dataLoader;
    @Autowired
    private DCPRepository repo;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dataLoader = new DCPDataLoader(repo);
    }
    @Test
    @Transactional
    public void testDeserializeDCP() throws Exception {
       dataLoader.run(mock(ApplicationArguments.class));
    }
}
