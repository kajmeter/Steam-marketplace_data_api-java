import java.util.ArrayList;

public class main {
    public static ReferenceFinder finder;
    static String first_item = "awp asiimov";
    static String second_item = "earbuds";
    static ArrayList<reference> item_references = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        //when needed ,setting custom appID is a possibility
        finder = new ReferenceFinder(730);
        item_references.add(
                finder.find(first_item)
        );

        //you can choose from 6 predefined appIDs
        finder.setAppID(ReferenceFinder.appID.TF2);
        item_references.add(
                finder.find(second_item)
        );
        print();
    }

    static void print() throws Exception {
        for (reference reference : item_references){
            System.out.println("I'm finding first "+reference.getSearch_name()+" listing link under "
                    +reference.getAppID()+" app id: \n"
                    +reference.getLink()+"\n");
        }
    }
}
