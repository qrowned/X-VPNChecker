package dev.qrowned.vpnchecker.common.config.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
public final class PluginConfig {

    private final String apiKey;
    private final List<UUID> whitelistedIps;

    public PluginConfig() {
        this.apiKey = "00000-00000-00000-00000";
        this.whitelistedIps = Collections.singletonList(UUID.fromString("a7f2ffe9-cf32-46be-8453-9c73eb3be00b"));
    }

    public PluginConfig(String apiKey, List<UUID> whitelistedIps) {
        this.apiKey = apiKey;
        this.whitelistedIps = whitelistedIps;
    }
}
