package com.ivakulchyk.task.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import org.jetbrains.annotations.PropertyKey;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyUtil {

    private static final String PROPERTIES_FILE = "/application.properties";

    @SneakyThrows
    private static Properties getPropertiesInstance(String fileName) {
        Properties instance = new Properties();
        try (
                InputStream resourceStream = PropertyUtil.class.getResourceAsStream(fileName);
                InputStreamReader inputStream = new InputStreamReader(requireNonNull(resourceStream), StandardCharsets.UTF_8)
        ) {
            instance.load(inputStream);
        }
        return instance;
    }

    public static String getProperty(String propertyName) {
        return getPropertiesInstance(PROPERTIES_FILE).getProperty(propertyName);
    }

    public static String label(@PropertyKey(resourceBundle = "label") String key, Object... args) {
        Locale locale = Locale.forLanguageTag(getProperty("organization.locale"));
        String value = ResourceBundle.getBundle("label", locale).getString(key);
        return value.formatted(args);
    }
}
