package dev.qrowned.vpnchecker.common.config;

import dev.qrowned.vpnchecker.common.utils.VPNCheckerUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

@Getter
@Setter
public final class JsonConfig<T> {

    private final File file;
    private final Class<T> clazz;
    private T config;

    @SneakyThrows
    public JsonConfig(@NotNull File file,
                      @NotNull Class<T> clazz) {
        this.file = file;
        this.clazz = clazz;
        this.reload();
    }

    public void reload() throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            this.file.createNewFile();
            this.config = (T) this.clazz.getConstructors()[0].newInstance();
            this.save();
        }
        final String json = new String(Files.readAllBytes(this.file.toPath()));
        this.config = VPNCheckerUtils.OBJECT_MAPPER.readValue(json, this.clazz);
    }

    public void save() throws IOException {
        try (Writer writer = new FileWriter(this.file)) {
            VPNCheckerUtils.OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValue(writer, this.config);
        }
    }

}
