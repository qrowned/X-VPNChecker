package dev.qrowned.vpnchecker.common.handler;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dev.qrowned.vpnchecker.common.http.ProxyCheckHttpClient;
import dev.qrowned.vpnchecker.common.result.ProxyCheckResult;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public final class ProxyCheckHandler {

    private final LoadingCache<String, ProxyCheckResult> proxyCheckResultCache;

    public ProxyCheckHandler(@NotNull ProxyCheckHttpClient httpClient) {
        this.proxyCheckResultCache = CacheBuilder.newBuilder()
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .build(new CacheLoader<String, ProxyCheckResult>() {
                    @Override
                    public @NotNull ProxyCheckResult load(@NotNull String ipAddress) throws Exception {
                        return httpClient.getProxyCheckResult(ipAddress);
                    }
                });
    }

    public ProxyCheckResult getProxyCheckResult(@NotNull String ipAddress) {
        return this.proxyCheckResultCache.getUnchecked(ipAddress);
    }

}
