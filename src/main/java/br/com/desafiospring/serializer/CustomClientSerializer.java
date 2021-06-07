package br.com.desafiospring.serializer;

import br.com.desafiospring.model.Client;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomClientSerializer extends StdSerializer<List<Client>> {

    public CustomClientSerializer() {
        this(null);
    }

    public CustomClientSerializer(Class<List<Client>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Client> clientList,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Client> sellerList = new ArrayList<>();
        for (Client c : clientList) {
            c.setSeller(null);
            sellerList.add(c);
        }
        generator.writeObject(sellerList);
    }
}