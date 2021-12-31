package dev.qrowned.vpnchecker.common.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.qrowned.vpnchecker.common.ip.IPCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ProxyCheckResult {

    @NotNull
    private String status;

    private IPCheck ipCheck;

}
