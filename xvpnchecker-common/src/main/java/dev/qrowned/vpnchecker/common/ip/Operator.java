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
public final class Operator {

    @NotNull
    private String name;
    private String url;
    private String popularity;

    public Operator(@NotNull String name,
                    String url,
                    String popularity) {
        this.name = name;
        this.url = url;
        this.popularity = popularity;
    }

}
