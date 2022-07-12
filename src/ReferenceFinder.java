import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ReferenceFinder {
    private int appID;
    private String prefix = "<a class=\"market_listing_row_link\" href=\"";
    private String sufix = "\" id=\"resultlink_0\">";

    enum appID{
        STEAM, CSGO,
        DOTA2, RUST,
        TF2, PUBG
    }

    public ReferenceFinder(appID option){
        setAppID(option);
    }

    public ReferenceFinder(int appID){
        setAppID(appID);
    }

    public reference find(String name) throws Exception {
        return new reference(appID,class_data_fetch(get_website_data(prepareLink(name))),name);
    }

    private String prepareLink(String name){
        name = name.replace(" ","+");
        return "https://steamcommunity.com/market/search?appid="
                +appID
                +"&q="
                +name;
    }

    private String class_data_fetch(String content){
        //todo enable settings class id in favour of custom listing order
        String out = "";

        try{
            out = content.substring(content.indexOf(prefix)+prefix.length(),
                    content.indexOf(sufix));
        }catch (StringIndexOutOfBoundsException exception){
        }

        return out;
    }

    private String get_website_data(String prepared_link) throws Exception {
        //todo fix multiple connections bootleneck
        String content = null;
        URLConnection connection;

        try{
            connection = new URL(prepared_link).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());

            while (scanner.hasNextLine()){
                content = scanner.nextLine();
                if(content.contains(prefix)&&content.contains(sufix)){
                    break;
                }
            }

            scanner.close();
        }catch (Exception ex){
            throw new Exception("\n\nWrong name || outdated affix " +
                    "\n When you're certain that It's the second issue or a bug ,please report it here: " +
                    "\n https://github.com/kajmeter/Steam-marketplace_data_api-java/issues \n");
        }

        return content;
    }

    public int getAppID(){
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public void setAppID(appID option) {
        switch (option){
            case STEAM -> appID = 753; case CSGO -> appID = 730;
            case DOTA2 -> appID = 570; case RUST -> appID = 252490;
            case TF2 -> appID = 440; case PUBG -> appID = 578080;
        }
    }
}

class reference{
    private int appID;
    private String link;
    private String search_name;

    public reference(int appID, String link, String search_name){
        this.appID = appID; this.link = link; this.search_name = search_name;
    }

    public int getAppID() {
        return appID;
    }

    public String getLink() {
        return link;
    }

    public String getSearch_name() {
        return search_name;
    }
}
