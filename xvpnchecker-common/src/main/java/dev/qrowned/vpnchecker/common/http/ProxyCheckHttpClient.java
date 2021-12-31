package dev.qrowned.vpnchecker.common.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.qrowned.vpnchecker.common.utils.VPNCheckerUtils;
import dev.qrowned.vpnchecker.common.result.ProxyCheckResult;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public final class ProxyCheckHttpClient {

    private static final String API_URL = "https://proxycheck.io/v2/";

    private final String apiKey;

    public ProxyCheckHttpClient(@NotNull String apiKey) {
        this.apiKey = apiKey;
    }

    public ProxyCheckResult getProxyCheckResult(@NotNull String ipAddress) throws JsonProcessingException {
        final String response = new HTTPQuery()
                .get(API_URL + ipAddress + "?vpn=1?asn=1?key=" + apiKey);
        return VPNCheckerUtils.OBJECT_MAPPER.readValue(response, ProxyCheckResult.class);
    }

}
