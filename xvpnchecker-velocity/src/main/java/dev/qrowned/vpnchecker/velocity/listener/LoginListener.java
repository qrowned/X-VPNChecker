package dev.qrowned.vpnchecker.velocity.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import dev.qrowned.vpnchecker.common.VPNCheckerPlugin;
import dev.qrowned.vpnchecker.common.utils.VPNCheckerMessages;
import dev.qrowned.vpnchecker.common.config.JsonConfig;
import dev.qrowned.vpnchecker.common.config.impl.PluginConfig;
import dev.qrowned.vpnchecker.common.ip.IPCheck;
import dev.qrowned.vpnchecker.common.ip.Operator;
import dev.qrowned.vpnchecker.common.http.ProxyCheckHttpClient;
import dev.qrowned.vpnchecker.common.result.ProxyCheckResult;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class LoginListener {

    private final JsonConfig<PluginConfig> pluginConfig;
    private final ProxyCheckHttpClient proxyCheckHttpClient;

    public LoginListener(@NotNull VPNCheckerPlugin vpnCheckerPlugin) {
        this.pluginConfig = vpnCheckerPlugin.getPluginConfig();
        this.proxyCheckHttpClient = vpnCheckerPlugin.getProxyCheckHttpClient();
    }

    @Subscribe(order = PostOrder.FIRST)
    public void handle(@NotNull LoginEvent event) throws JsonProcessingException {
        final Player player = event.getPlayer();
        if (this.pluginConfig.getConfig()
                .getWhitelistedIps().contains(player.getUniqueId())) return;

        final String hostAddress = player.getRemoteAddress().getAddress().getHostAddress();

        final ProxyCheckResult proxyCheckResult = this.proxyCheckHttpClient.getProxyCheckResult(hostAddress);
        final IPCheck ipCheck = proxyCheckResult.getIpCheck();
        if (ipCheck == null || !ipCheck.isProxy()) return;

        final Operator operator = ipCheck.getOperator();
        final TextComponent component = LegacyComponentSerializer.legacySection().deserialize(operator == null
                ? VPNCheckerMessages.VPN_UNKNOWN_OPERATOR
                : String.format(VPNCheckerMessages.VPN_OPERATOR, operator.getName()));

        event.setResult(ResultedEvent.ComponentResult.denied(component));
    }

}