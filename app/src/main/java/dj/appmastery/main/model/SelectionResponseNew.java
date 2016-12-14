package dj.appmastery.main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 24-10-2016.
 */
public class SelectionResponseNew {

    public class Selection {
        private String _id;
        private String active;
        private String description;
        private boolean mature_content;
        private int rating;
        private boolean rental_required;
        private boolean subscription_required;
        private String title;
        private String short_description;
        private boolean tv_show;
        private String thumbnail;
        private String coverart;

        public Selection(String _id, String active, String description, boolean mature_content,
                         int rating, boolean rental_required, boolean subscription_required,
                         String title, String short_description, boolean tv_show, String thumbnail, String coverart) {
            this._id = _id;
            this.active = active;
            this.description = description;
            this.mature_content = mature_content;
            this.rating = rating;
            this.rental_required = rental_required;
            this.subscription_required = subscription_required;
            this.title = title;
            this.short_description = short_description;
            this.tv_show = tv_show;
            this.thumbnail = thumbnail;
            this.coverart = coverart;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isMature_content() {
            return mature_content;
        }

        public void setMature_content(boolean mature_content) {
            this.mature_content = mature_content;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public boolean isRental_required() {
            return rental_required;
        }

        public void setRental_required(boolean rental_required) {
            this.rental_required = rental_required;
        }

        public boolean isSubscription_required() {
            return subscription_required;
        }

        public void setSubscription_required(boolean subscription_required) {
            this.subscription_required = subscription_required;
        }

        public String getShort_description() {
            return short_description;
        }

        public void setShort_description(String short_description) {
            this.short_description = short_description;
        }

        public boolean isTv_show() {
            return tv_show;
        }

        public void setTv_show(boolean tv_show) {
            this.tv_show = tv_show;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getCoverart() {
            return coverart;
        }

        public void setCoverart(String coverart) {
            this.coverart = coverart;
        }
    }

    @Override
    public String toString() {
        return "SelectionResponse{" +
                "response=" + response +
                ", titleList=" + titleList +
                ", urlList=" + urlList +
                '}';
    }

    /*public class Thumbnails {
        double aspect_ratio;
        int height;
        String name;
        String url;
        int width;

        @Override
        public String toString() {
            return "Thumbnails{" +
                    "aspect_ratio=" + aspect_ratio +
                    ", height=" + height +
                    ", name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", width=" + width +
                    '}';
        }

        public double getAspect_ratio() {
            return aspect_ratio;
        }

        public int getHeight() {
            return height;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public int getWidth() {
            return width;
        }
    }*/

    private List<Selection> response;
    private List<String> titleList = new ArrayList<>();
    private List<String> urlList = new ArrayList<>();

    public SelectionResponseNew(String mainmenuTitle, String submenuTitle){
        this.mainmenuTitle = mainmenuTitle;
        this.submenuTitle = submenuTitle;
    }

    public SelectionResponseNew(){

    }

    private String submenuTitle = "";
    private String mainmenuTitle = "";

    public String getSubmenuTitle() {
        return submenuTitle;
    }

    public void setSubmenuTitle(String submenuTitle) {
        this.submenuTitle = submenuTitle;
    }

    public String getMainmenuTitle() {
        return mainmenuTitle;
    }

    public void setMainmenuTitle(String mainmenuTitle) {
        this.mainmenuTitle = mainmenuTitle;
    }

    public List<Selection> getResponse() {
        return response;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void onParse() {
        titleList.clear(); ;
        urlList.clear();
        for (Selection selection: response){
            urlList.add(selection.getThumbnail());
            titleList.add(selection.getTitle());
        }
    }


}
