package dev.qrowned.vpnchecker.common.ip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public final class IPCheck {

    @NotNull
    private String ip;

    private String asn;
    private String provider;
    private String organisation;

    private String city;
    private String region;
    private String country;
    private String continent;

    private String proxy;

    private Operator operator;

    public IPCheck(@NotNull String ip,
                   String asn,
                   String provider,
                   String organisation,
                   String city,
                   String region,
                   String country,
                   String continent,
                   boolean proxy,
                   Operator operator) {
        this.ip = ip;
        this.asn = asn;
        this.provider = provider;
        this.organisation = organisation;
        this.city = city;
        this.region = region;
        this.country = country;
        this.continent = continent;
        this.proxy = proxy ? "yes" : "no";
        this.operator = operator;
    }

    public boolean isProxy() {
        return this.proxy.equals("yes");
    }

}
