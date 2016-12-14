package dj.appmastery.main.server;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by User on 24-11-2016.
 */
public class URLHelper {

    public String REG_TOKEN = "RAJESHTOKENGENE";

    private static URLHelper urlHelper;
    public static URLHelper getInstance(){
        if (urlHelper == null)
            urlHelper = new URLHelper();
        return urlHelper;
    }

    private URLHelper(){

    }

    //http://vod.reliableiptv.com/generic/reliableiptv/json/categories.php?regToken=RAJESHTOKENGENE&category=00000000001&sub_id=12

    public final String getVideoThumbnails(String categoryId, @Nullable String submenuId){
        if (TextUtils.isEmpty(submenuId))
            return new StringBuilder(getMainMenuAPI()).append("&")
                .append("category").append("=").append(categoryId).toString();
        return new StringBuilder(getMainMenuAPI()).append("&")
                .append("category").append("=").append(categoryId).append("&")
                .append("sub_id").append("=").append(submenuId).toString();
    }

    public final String getMainMenuAPI(){
        return new StringBuilder(END_POINT).append(Verb.MAIN_MENU_API)
                .append("regToken").append("=").append(REG_TOKEN).toString();
    }

    public final String END_POINT = "http://vod.reliableiptv.com/generic/reliableiptv/json/";

    private final class Verb{

        private static final String MAIN_MENU_API = "categories.php?";
    }
}
