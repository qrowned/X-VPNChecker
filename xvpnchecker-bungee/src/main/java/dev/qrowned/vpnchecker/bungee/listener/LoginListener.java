package dev.qrowned.vpnchecker.bungee.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.qrowned.vpnchecker.common.VPNCheckerPlugin;
import dev.qrowned.vpnchecker.common.config.JsonConfig;
import dev.qrowned.vpnchecker.common.config.impl.PluginConfig;
import dev.qrowned.vpnchecker.common.handler.ProxyCheckHandler;
import dev.qrowned.vpnchecker.common.ip.IPCheck;
import dev.qrowned.vpnchecker.common.ip.Operator;
import dev.qrowned.vpnchecker.common.result.ProxyCheckResult;
import dev.qrowned.vpnchecker.common.utils.VPNCheckerMessages;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class LoginListener implements Listener {

    private final JsonConfig<PluginConfig> pluginConfig;
    private final ProxyCheckHandler proxyCheckHandler;

    public LoginListener(@NotNull VPNCheckerPlugin vpnCheckerPlugin) {
        this.pluginConfig = vpnCheckerPlugin.getPluginConfig();
        this.proxyCheckHandler = vpnCheckerPlugin.getProxyCheckHandler();
    }

    @EventHandler(priority = Byte.MIN_VALUE)
    public void handle(@NotNull LoginEvent event) {
        final PendingConnection connection = event.getConnection();
        final UUID uuid = connection.getUniqueId();
        final String ipAddress = connection.getAddress().getAddress().getHostAddress();

        if (this.pluginConfig.getConfig()
                .getWhitelistedIps().contains(uuid)) return;

        final ProxyCheckResult proxyCheckResult = this.proxyCheckHandler.getProxyCheckResult(ipAddress);
        final IPCheck ipCheck = proxyCheckResult.getIpCheck();
        if (ipCheck == null || !ipCheck.isProxy()) return;

        final Operator operator = ipCheck.getOperator();
        final BaseComponent[] component = TextComponent.fromLegacyText(operator == null
                ? VPNCheckerMessages.VPN_UNKNOWN_OPERATOR
                : String.format(VPNCheckerMessages.VPN_OPERATOR, operator.getName()));

        event.setCancelReason(component);
        event.setCancelled(true);
    }

}
