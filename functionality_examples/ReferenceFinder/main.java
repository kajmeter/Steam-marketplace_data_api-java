public class main {
    public static ReferenceFinder finder;
    static String first_item = "awp asiimov";
    static String second_item = "earbuds";

    public static void main(String[] args) throws Exception {
        //when needed ,setting custom appID is a possibility
         finder = new ReferenceFinder(730);
         print(first_item);

        //you can choose from 6 predefined appIDs
        finder.setAppID(ReferenceFinder.appID.TF2);
        print(second_item);
    }

    static void print(String item_name) throws Exception {
        System.out.println("I'm finding first "+item_name+" listing link under "
                +finder.getAppID()+" app id: \n"
                +finder.find(item_name)+"\n");
    }
}
