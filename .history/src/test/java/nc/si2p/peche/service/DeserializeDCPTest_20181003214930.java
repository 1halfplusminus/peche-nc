package nc.si2p.peche.service;

import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import nc.si2p.peche.PechencApp;
import nc.si2p.peche.domain.enumeration.DCPEtat;
import nc.si2p.peche.service.mapper.DCPEtatDeserializer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PechencApp.class)
public class DeserializeDCPTest {

    @Test
    public void testSendEmail() throws Exception {
        DCPEtatDeserializer deserializer = new DCPEtatDeserializer(DCPEtat.class);
        JsonParser jsonparser;
        DeserializationContext context;
		DCPEtat etat = deserializer.deserialize(jsonparser, context);
        assertThat(etat).isPresent();
    }
}
