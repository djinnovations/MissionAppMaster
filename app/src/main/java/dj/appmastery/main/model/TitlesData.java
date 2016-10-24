package dj.appmastery.main.model;

/**
 * Created by User on 23-10-2016.
 */
public class TitlesData {

    private String title;
    private boolean isSelected;

    public TitlesData(String title, boolean isSelected) {
        this.title = title;
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
