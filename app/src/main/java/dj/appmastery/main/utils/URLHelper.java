package dj.appmastery.main.utils;

/**
 * Created by DJphy on 28-09-2016.
 */
public class URLHelper {

    public static final String END_POINT = "https://api.nasa.gov/";
    public static final String END_POINT_APP_MAS = "https://api.zype.com/";
    public static final String API_KEY_NASA = "vtRoSiuO7Ze134C859OhPy8AqLOXxIvDmNfVmHOU";
    public static final String APP_KEY = "fB9gMa3z-SL1liR2AKs83YREWcvOCXZ0jogDfeYv9Qzdb9ZBtAb5GTOPSVVbLZl-";

    //https://api.zype.com/categories?app_key=fB9gMa3z-SL1liR2AKs83YREWcvOCXZ0jogDfeYv9Qzdb9ZBtAb5GTOPSVVbLZl-
    public static final String getAppMasterTitlesAPI(){
        return new StringBuffer().append(END_POINT_APP_MAS).append(VERB.AM_TITLES_API)
                .append("app_key").append("=").append(APP_KEY).toString();
    }

    //https://api.zype.com/videos?app_key=fB9gMa3z-SL1liR2AKs83YREWcvOCXZ0jogDfeYv9Qzdb9ZBtAb5GTOPSVVbLZl-&category[Movies]=kids
    public static final String getThumbnailAPI(String mainmenu, String submenu){
        return new StringBuilder().append(END_POINT_APP_MAS).append(VERB.AM_THUMBNAIL_API)
                .append("app_key").append("=").append(APP_KEY).append("&")
                .append("category[").append(mainmenu).append("]=").append(submenu).toString();
    }

    public static final String getApodAPI(){
        return END_POINT + VERB.APOD_API;
    }

    private static final class VERB{
        static final String APOD_API = "planetary/apod?";
        static final String AM_TITLES_API = "categories?";
        static final String AM_THUMBNAIL_API = "videos?";
    }
}
