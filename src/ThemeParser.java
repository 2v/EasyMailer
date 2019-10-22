import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ThemeParser {

    public static String themePath;

    public static int parentRank = 0;
    public static int subRank = 1;

    public static ArrayList<String> elements = new ArrayList<>();
    public static ArrayList<String> temp = new ArrayList<>();

    public ThemeParser(String themePath) {
        this.themePath = themePath;

        // pass the path to the file as a parameter
        File file = new File(themePath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()) {
            String next = sc.nextLine();
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

    public static void main(String[] args) throws Exception
    {
        ThemeParser parse = new ThemeParser("themes/blue-white/assets.txt");
        for (String element : elements) {
            System.out.println(element);
        }
    }
}
