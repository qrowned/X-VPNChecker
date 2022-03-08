package dev.qrowned.vpnchecker.velocity.listener;

import com.velocitypowered.api.event.EventTask;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import dev.qrowned.vpnchecker.common.VPNCheckerPlugin;
import dev.qrowned.vpnchecker.common.config.JsonConfig;
import dev.qrowned.vpnchecker.common.config.impl.PluginConfig;
import dev.qrowned.vpnchecker.common.handler.ProxyCheckHandler;
import dev.qrowned.vpnchecker.common.ip.IPCheck;
import dev.qrowned.vpnchecker.common.ip.Operator;
import dev.qrowned.vpnchecker.common.result.ProxyCheckResult;
import dev.qrowned.vpnchecker.common.utils.VPNCheckerMessages;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class LoginListener {

    private final JsonConfig<PluginConfig> pluginConfig;
    private final ProxyCheckHandler proxyCheckHandler;

    public LoginListener(@NotNull VPNCheckerPlugin vpnCheckerPlugin) {
        this.pluginConfig = vpnCheckerPlugin.getPluginConfig();
        this.proxyCheckHandler = vpnCheckerPlugin.getProxyCheckHandler();
    }

    @Subscribe(order = PostOrder.FIRST)
    public EventTask handle(@NotNull LoginEvent event) {
        final Player player = event.getPlayer();
        final String hostAddress = player.getRemoteAddress().getAddress().getHostAddress();

        if (this.pluginConfig.getConfig()
                .getWhitelistedIps().contains(player.getUniqueId()) || hostAddress.equals("127.0.0.1")) return null;

        return EventTask.async(() -> {
            final ProxyCheckResult proxyCheckResult = this.proxyCheckHandler.getProxyCheckResult(hostAddress);
            final IPCheck ipCheck = proxyCheckResult.getIpCheck();
            if (ipCheck == null || !ipCheck.isProxy()) return;

            final Operator operator = ipCheck.getOperator();
            final TextComponent component = LegacyComponentSerializer.legacySection().deserialize(operator == null
                    ? VPNCheckerMessages.VPN_UNKNOWN_OPERATOR
                    : String.format(VPNCheckerMessages.VPN_OPERATOR, operator.getName()));

            event.setResult(ResultedEvent.ComponentResult.denied(component));
        });
    }

}
