import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ThemeParser {

    public static String theme_path;
    public static String assets_path;

    public static int parentRank = 0;
    public static int subRank = 1;

    public static ArrayList<String> elements = new ArrayList<>();
    public static ArrayList<String> temp = new ArrayList<>();

    public ThemeParser(String assets_path, String theme_path) {
        this.assets_path = assets_path;
        this.theme_path = theme_path;

        // pass the path to the file as a parameter
        File file = new File(theme_path);
        Scanner raw_elements = null;

        try {
            raw_elements = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (raw_elements.hasNextLine()) {
            String next = raw_elements.nextLine();
            elements.add(next);
            temp.add(next);
        }


        for (int i=0; i < elements.size(); i++) {
            int rank = elements.get(i).lastIndexOf("$");

            // highest ranking elements
            if (rank == parentRank) {
                elements.set(i, elements.get(i).substring(elements.get(i).indexOf(" ", 1)));
                System.out.println(elements.get(i));
            }

            // second-highest ranking elements
            if (rank == subRank) {
                String parent = "";
                for (int j=i; j >= 0; j--) {
                    if (temp.get(j).lastIndexOf("$") == subRank - 1) {
                        parent = elements.get(j);
                        break;
                    }
                }
                elements.set(i, parent + "." + temp.get(i).substring(elements.get(i).indexOf(" ", 1) + 1));
                System.out.println(elements.get(i));
            }
        }
    }

    public String GetElement(String element) {
        // gets part of HTML file that is delimited by the the element tag in the form of a string

    }

    public static void main(String[] args) throws Exception
    {
        ThemeParser parse = new ThemeParser("themes/blue-white/assets.txt", "themes/blue-white/theme.html");
        for (String element : elements) {
            System.out.println(element);
        }
    }
}
