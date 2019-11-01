package cx.tab.easymailer;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class Driver {

    public static void main(String args[]) {
        Yaml yaml = new Yaml();

        MailerConfig config;

        try {
            config = new MailerConfig((Map<String, Map<String, String>>) yaml
                    .load(new FileReader(new File("config.yml"))));
        } catch (FileNotFoundException e) {
            System.out.println("You're missing your config.yml file!");
            return;
        }

        ThemeParser parse = new ThemeParser(config.getOrDefault("assets_path",
                String.class, "null"), config.getOrDefault("theme_path",
                String.class, "null"));

//        ThemeParser parse = new ThemeParser(ThemeParser.assets_path, ThemeParser.theme_path);

        for (String element : parse.getElements()) {
            System.out.println("FILTERED: " + element);
        }

        System.out.println(parse.parseMap.get("required.gen"));
    }
}
