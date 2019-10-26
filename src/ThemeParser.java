import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ThemeParser {

    private static String themePath = "themes/blue-white/assets.txt";

    private static int parentRank = 0;
    private static int subRank = 1;

    private ArrayList<String> elements = new ArrayList<>();
    private ArrayList<String> temp = new ArrayList<>();

    public ThemeParser(String themePath) {
        ThemeParser.themePath = themePath;

        // pass the path to the file as a parameter
        File file = new File(themePath);
        Scanner rawElements = null;

        try {
            rawElements = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (rawElements.hasNextLine()) {
            String next = rawElements.nextLine();
            elements.add(next);
            temp.add(next);
        }

        for (int i=0; i < elements.size(); i++) {
            int rank = elements.get(i).lastIndexOf("$");

            // highest ranking elements
            if (rank == parentRank) {
                elements.set(i, elements.get(i).substring(elements.get(i).indexOf(" ", 0)));
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
                elements.set(i, parent + "." + temp.get(i).substring(elements.get(i).indexOf(" ", 0) + 1));
                System.out.println(elements.get(i));
            }
        }
    }

    public ThemeParser() {
        this(themePath);
    }

    public ArrayList<String> getElements() {
        return elements;
    }

    public static void main(String[] args) throws Exception
    {
        ThemeParser parse = new ThemeParser();
        for (String element : parse.getElements()) {
            System.out.println(element);
        }
    }
}
