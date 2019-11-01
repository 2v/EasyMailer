package cx.tab.easymailer;

import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class MailerConfig extends Yaml {
    // Class to easily parse the Yaml config - Similar to BetaNyan's implementation

    private Map<String, Map<String, String>> data;

    public MailerConfig(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public <T> T get(String path, Class<T> type) {

        if (path.contains(".")) {

            String[] split = path.split("\\.");
            Map<String, String> subPath = data.get(split[0]);
            return type.cast(subPath.get(split[1]));

        } else {

            return type.cast(data.get(path));

        }

    }

    public <T> T getOrDefault(String path, Class<T> type, T defaultV) {

        T info = get(path, type);
        if (info != null) {
            return info;
        } else {
            return defaultV;
        }

    }
}
