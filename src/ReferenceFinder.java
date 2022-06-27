import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ReferenceFinder {
    private int appID;
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

    public String find(String name) throws Exception {
        return class_data_fetch(get_website_data(prepareLink(name)));
    }

    private String prepareLink(String name){
        name = name.replace(" ","+");
        return "https://steamcommunity.com/market/search?appid="
                +appID
                +"&q="
                +name;
    }

    private String class_data_fetch(String content) throws Exception {
        //todo enable settings class id in favour of custom listing order
        String prefix = "<a class=\"market_listing_row_link\" href=\"";
        String sufix = "\" id=\"resultlink_0\">";
        String out = "";
        try{
            out = content.substring(content.indexOf(prefix)+prefix.length(),
                    content.indexOf(sufix));
        }catch (StringIndexOutOfBoundsException exception){
            throw new Exception("Wrong name || outdated affix");
        }

        return out;
    }

    private String get_website_data(String prepared_link){
        //todo fix multiple connections bootleneck
        String content = null;
        URLConnection connection;

        try{
            connection = new URL(prepared_link).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            //todo avoid downloading unnecessary data using affix filter ,It'll improve computation significantly
            content = scanner.next();
            scanner.close();
        }catch (Exception ex){
            ex.printStackTrace();
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
