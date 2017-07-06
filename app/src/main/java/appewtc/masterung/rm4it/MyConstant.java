package appewtc.masterung.rm4it;

/**
 * Created by masterUNG on 7/6/2017 AD.
 */

public class MyConstant {

    //URL php
    private String urlGetAllWhereTABLE = "http://androidthai.in.th/chok/getAllWhereTABLE.php";


    //Array
    private String[] tableStrings = new String[]{
            "userTABLE_master",
            "correct",
            "environment",
            "governance",
            "internet",
            "money",
            "network_intrusion",
            "server_network",
            "virus",
            "wiless_network", };

    public String getUrlGetAllWhereTABLE() {
        return urlGetAllWhereTABLE;
    }

    public String[] getTableStrings() {
        return tableStrings;
    }
}   // Main Class
