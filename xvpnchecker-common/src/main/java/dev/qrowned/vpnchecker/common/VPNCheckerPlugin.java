package dev.qrowned.vpnchecker.common;

import dev.qrowned.vpnchecker.common.config.JsonConfig;
import dev.qrowned.vpnchecker.common.config.impl.PluginConfig;
import dev.qrowned.vpnchecker.common.http.ProxyCheckHttpClient;
import lombok.Getter;

import java.io.File;

@Getter
public final class VPNCheckerPlugin {

    private JsonConfig<PluginConfig> pluginConfig;
    private ProxyCheckHttpClient proxyCheckHttpClient;

    public void initialize() {
        final File configFile = new File("./plugins/XVPNChecker/config.json");

        this.pluginConfig = new JsonConfig<>(configFile, PluginConfig.class);
        this.proxyCheckHttpClient = new ProxyCheckHttpClient(this.pluginConfig.getConfig().getApiKey());
    }

}
