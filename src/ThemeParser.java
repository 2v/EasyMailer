import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ThemeParser {

    public static String theme_path = "themes/blue-white/theme.html";
    public static String assets_path = "themes/blue-white/assets.txt";

    private static int parentRank = 0;
    private static int subRank = 1;

    private ArrayList<String> elements = new ArrayList<>();

    private ArrayList<String> themelines = new ArrayList<>();

    public ThemeParser(String assets_path, String theme_path) {
        this.assets_path = assets_path;
        this.theme_path = theme_path;

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
        }

        // gets part of HTML file that is delimited by the the element tag in the form of a string
        return temp;
    }

    public ArrayList<String> getElements() {
        return elements;
    }

    public static void main(String[] args) throws Exception
    {
        // these lines are only here for testing the methods

        ThemeParser parse = new ThemeParser("themes/blue-white/assets.txt", "themes/blue-white/theme.html");
        for (String element : parse.getElements()) {
            System.out.println("FILTERED: " + element);
        }

        System.out.println(parse.getElement(parse.getElements().get(0)));
    }
}
