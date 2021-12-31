package dev.qrowned.vpnchecker.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import dev.qrowned.vpnchecker.common.ip.IPCheck;
import dev.qrowned.vpnchecker.common.result.ProxyCheckResult;

import java.io.IOException;
import java.util.Iterator;

public final class ProxyCheckResultDeserializer extends StdDeserializer<ProxyCheckResult> {

    public ProxyCheckResultDeserializer() {
        this(null);
    }

    public ProxyCheckResultDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ProxyCheckResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        final Iterator<JsonNode> nodeIterator = node.elements();

        final String status = nodeIterator.next().asText();
        final JsonNode subNode = nodeIterator
                .next();

        final IPCheck ipCheck = this.mapIntoObject(subNode, IPCheck.class);

        return new ProxyCheckResult(status, ipCheck);
    }

    private <T> T mapIntoObject(JsonNode node, Class<T> type) throws IOException {
        JsonParser parser = node.traverse();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(parser, type);
    }

}
