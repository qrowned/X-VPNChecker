package dev.qrowned.vpnchecker.bungee;

import dev.qrowned.vpnchecker.bungee.listener.LoginListener;
import dev.qrowned.vpnchecker.common.VPNCheckerPlugin;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public final class VPNCheckerBungeeBootstrap extends Plugin {

    private final VPNCheckerPlugin vpnCheckerPlugin = new VPNCheckerPlugin();

    @Override
    public void onEnable() {
        final Logger logger = this.getLogger();
        logger.info("--------------------------");
        logger.info("Starting...");
        logger.info("XVPNChecker by qrowned");
        logger.info("--------------------------");

        this.vpnCheckerPlugin.initialize();

        this.getProxy().getPluginManager().registerListener(this, new LoginListener(this.vpnCheckerPlugin));
    }

}
