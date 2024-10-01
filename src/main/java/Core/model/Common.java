package Core.model;

import java.util.Properties;

public class Common {

    private static Properties envData = null;
    private static Properties appData = null;


    //get value from environment by key
    public static String getEnvData(String key) {
        return envData.get(key).toString();
    }

    //add environment data
    public static void setEnvData(Properties Data) {
        envData = Data;
    }

    //get value from application static by key
    public static String getAppData(String key) {
        return appData.get(key).toString();
    }

    //add application static data to property
    public static void setAppData(Properties Data) {
        appData = Data;
    }

    //for dropdown selection modes
    public static enum DROPDOWN{
        ByText,
        ByValue,
        ByIndex,
        RandomOption
    }

    //for random string
    public static enum RANDOMTEXT {
        Alpha,
        Numeric,
        AlphaNumeric
    }

    //browser window option
    public static enum BROWSER_WINDOW {
        Index,
        Title
    }

    //scroll position
    public static enum SCROLL_TO{
        Element,
        Position
    }

    //element state
    public static enum ELEMENT_STATE {
        Visible,
        Enabled,
        Present
    }
}
