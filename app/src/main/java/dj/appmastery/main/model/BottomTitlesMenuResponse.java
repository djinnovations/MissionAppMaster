package dj.appmastery.main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 23-10-2016.
 */
public class BottomTitlesMenuResponse {

    public class Titles{
        private String title;
        private List<String> values;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }
    }

    private List<Titles> response;
    private Map<String, List<String>> mapOfMenu;
    public void onParse(){
        mapOfMenu = new HashMap<>();
        for (Titles titles: response){
            mapOfMenu.put(titles.getTitle(), titles.getValues());
        }
        titleList = new ArrayList<>(mapOfMenu.keySet());
    }

    @Override
    public String toString() {
        return "BottomTitlesMenuResponse{" +
                "response=" + response +
                ", mapOfMenu=" + mapOfMenu +
                ", titleList=" + titleList +
                '}';
    }

    public Map<String, List<String>> getMapOfMenu() {
        return mapOfMenu;
    }

    private List<String> titleList;

    public List<String> getAllTitles(){
        return titleList;
    }

    public void setMapOfMenu(Map<String, List<String>> mapOfMenu) {
        this.mapOfMenu = mapOfMenu;
    }
}
