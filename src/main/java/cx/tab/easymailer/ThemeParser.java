package cx.tab.easymailer;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ThemeParser {

    public static String theme_path = "themes/blue-white/theme.html";
    public static String assets_path = "themes/blue-white/assets.txt";

    private static int parentRank = 0;
    private static int subRank = 1;

    private ArrayList<String> elements = new ArrayList<>();

    private ArrayList<String> themelines = new ArrayList<>();

    // This wap will be used to associate the various elements with their HTML counterparts
    HashMap<String,String> parseMap = new HashMap<String,String>();

    public ThemeParser(String assets_path, String theme_path) {
        if (!(assets_path == "null" || theme_path == "null")) {
            this.assets_path = assets_path;
            this.theme_path = theme_path;
        } else {
            throw new RuntimeException("assets_path or theme_path not found. Please configure them in the config.yml file");
        }


        // pass the path to the file as a parameter
        Scanner rawElements = null;
        Scanner in = null;

        try {
            rawElements = new Scanner(new File(assets_path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            in = new Scanner(new File(theme_path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (in.hasNextLine()) {
            String next = in.nextLine();
            themelines.add(next);
        }

        ArrayList<String> temp = new ArrayList<>();

        while (rawElements.hasNextLine()) {
            String next = rawElements.nextLine();
            temp.add(next);
        }

        for (int i=0; i < temp.size(); i++) {
            int rank = temp.get(i).lastIndexOf("$");

            // highest ranking elements
            if (rank == parentRank) {
                elements.add(temp.get(i).substring(temp.get(i).indexOf(" ") + 1));
            }

            // second-highest ranking elements
            if (rank == subRank) {
                String parent = "";
                for (int j=i; j >= 0; j--) {
                    if (temp.get(j).lastIndexOf("$") == subRank - 1) {
                        parent = temp.get(j).substring(temp.get(j).indexOf(" ") + 1);
                        break;
                    }
                }
                elements.add(parent + "." + temp.get(i).substring(temp.get(i).indexOf(" ") + 1));
            }
        }

        // creates a map mapping elements to their html counterparts
        for (String element : elements) {
            parseMap.put(element, getElement(element));
        }

    }

    public ThemeParser() {
        this(theme_path, assets_path);
    }

    public String getElement(String element) {
        int startLine = 0;
        int endLine = 0;

        String temp = "";

        for (int i = 0; i < themelines.size(); i++) {
            if (themelines.get(i).contains("<!-- $" + element + " -->")) {
                startLine = i;
            }

            if (themelines.get(i).contains("<!-- $!" + element + " -->")) {
                endLine = i;
                break;
            }
        }

        if (endLine > startLine) {
            for (int i = startLine; i <= endLine; i++) {
                temp += themelines.get(i) + " \n";
            }
        } else {
            System.out.println("Element tags not found! Ensure that all elements are properly placed in the theme config file!");
        }

        // gets part of HTML file that is delimited by the the element tag in the form of a string
        return temp;
    }

    public ArrayList<String> getElements() {
        return elements;
    }

}
