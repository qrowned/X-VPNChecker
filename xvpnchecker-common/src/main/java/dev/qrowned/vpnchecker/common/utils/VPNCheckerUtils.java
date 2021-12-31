package dev.qrowned.vpnchecker.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.qrowned.vpnchecker.common.deserializer.ProxyCheckResultDeserializer;
import dev.qrowned.vpnchecker.common.result.ProxyCheckResult;

public final class VPNCheckerUtils {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(ProxyCheckResult.class, new ProxyCheckResultDeserializer());
        OBJECT_MAPPER.registerModule(simpleModule);
    }

}
