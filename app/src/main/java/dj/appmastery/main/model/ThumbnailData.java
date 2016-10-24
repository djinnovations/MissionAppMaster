package dj.appmastery.main.model;

/**
 * Created by User on 23-10-2016.
 */
public class ThumbnailData {

    private boolean isSelected;
    private String title;
    private String url;

    public ThumbnailData(boolean isSelected, String title, String url) {
        this.isSelected = isSelected;
        this.title = title;
        this.url = url;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
