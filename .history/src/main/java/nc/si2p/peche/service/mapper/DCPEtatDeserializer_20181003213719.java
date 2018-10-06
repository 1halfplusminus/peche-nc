package nc.si2p.peche.service.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.springframework.expression.ParseException;

import nc.si2p.peche.domain.enumeration.DCPEtat;

public class DCPEtatDeserializer
  extends StdDeserializer<DCPEtat> {
 
    public DCPEtatDeserializer() {
        this(null); 
    } 
 
    public DCPEtatDeserializer(Class<?> vc) {
        super(vc); 
    }
 
    @Override
    public DCPEtat deserialize(
      JsonParser jsonparser, DeserializationContext context) 
      throws IOException {
        String etat = jsonparser.getText();
        try {
            return DCPEtat.valueOf(etat.toUpperCase());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}