import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ThemeParser {

    public static int parentRank = 0;
    public static int subRank = 1;


    public static ArrayList<String> elements = new ArrayList<>();
    public static ArrayList<String> temp = new ArrayList<>();

    public static void main(String[] args) throws Exception
    {
        // pass the path to the file as a parameter
        File file =
                new File("themes/blue-white/assets.txt");
        Scanner sc = new Scanner(file);

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
}
