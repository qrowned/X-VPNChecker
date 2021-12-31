package dev.qrowned.vpnchecker.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.qrowned.vpnchecker.common.VPNCheckerPlugin;
import dev.qrowned.vpnchecker.velocity.listener.LoginListener;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;


@Getter
@Plugin(id = "xvpnchecker_velocity")
public final class VPNCheckerVelocityBootstrap {

    private final VPNCheckerPlugin vpnCheckerPlugin = new VPNCheckerPlugin();

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public VPNCheckerVelocityBootstrap(@NotNull ProxyServer server,
                                       @NotNull Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void handle(@NotNull ProxyInitializeEvent event) {
        this.logger.info("--------------------------");
        this.logger.info("Starting...");
        this.logger.info("XVPNChecker by qrowned");
        this.logger.info("--------------------------");

        this.vpnCheckerPlugin.initialize();

        this.server.getEventManager().register(this, new LoginListener(this.vpnCheckerPlugin));
    }

}
