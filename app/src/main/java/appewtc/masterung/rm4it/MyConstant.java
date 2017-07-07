package appewtc.masterung.rm4it;

/**
 * Created by masterUNG on 7/6/2017 AD.
 */

public class MyConstant {

    //URL php
    private String urlGetAllWhereTABLE = "http://androidthai.in.th/chok/getAllWhereTABLE.php";
    private String urlAddUser = "http://androidthai.in.th/chok/php_add_user_master.php";
    private String urlAddRisk = "http://androidthai.in.th/chok/addRisk.php";
    private String urlGetCheckWhere = "http://androidthai.in.th/chok/getCheckWhere.php";


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

    public String getUrlGetCheckWhere() {
        return urlGetCheckWhere;
    }

    public String getUrlAddRisk() {
        return urlAddRisk;
    }

    public String getUrlAddUser() {
        return urlAddUser;
    }

    public String getUrlGetAllWhereTABLE() {
        return urlGetAllWhereTABLE;
    }

    public String[] getTableStrings() {
        return tableStrings;
    }
}   // Main Class
