package dj.appmastery.main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 24-10-2016.
 */
public class SelectionResponse {

    public class Selection {
        private String title;
        private List<Thumbnails> thumbnails;

        public Selection(String title, List<Thumbnails> thumbnails) {
            this.title = title;
            this.thumbnails = thumbnails;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Thumbnails> getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(List<Thumbnails> thumbnails) {
            this.thumbnails = thumbnails;
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

    public class Thumbnails {
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
    }

    private List<Selection> response;
    private List<String> titleList;
    private List<String> urlList;

    public List<Selection> getResponse() {
        return response;
    }

    public void onParse() {
        titleList = new ArrayList<>();
        urlList = new ArrayList<>();
        for (Selection selection : response) {
            titleList.add(selection.getTitle());
            urlList.add(selection.getThumbnails().get(1).getUrl());
        }
    }

    public List<String> getTitles() {
        return titleList;
    }

    public List<String> getUrls() {
        return urlList;
    }

}
