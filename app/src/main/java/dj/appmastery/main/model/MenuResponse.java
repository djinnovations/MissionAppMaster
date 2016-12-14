package dj.appmastery.main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 23-10-2016.
 */
public class MenuResponse {

    public class Menu {

        private String _id;
        private String cat_id;
        private String created_at;
        private String title;
        private List<String> values;
        private List<String> sub_id;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public List<String> getSub_id() {
            return sub_id;
        }

        public void setSub_id(List<String> sub_id) {
            this.sub_id = sub_id;
        }

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

    private List<Menu> response;
    private Map<String, List<String>> mapOfMenuTitleSubMenuTitle;
    private Map<String, List<String>> mapOfMenuIdSubMenuId;
    private Map<String, String> mapOfMenuTitlesMenuId;
    private Map<String, String> mapOfSubMenuTitleSubMenuId;
    public void onParse(){
        mapOfMenuTitleSubMenuTitle = new HashMap<>();
        mapOfMenuIdSubMenuId = new HashMap<>();
        mapOfMenuTitlesMenuId = new HashMap<>();
        mapOfSubMenuTitleSubMenuId = new HashMap<>();
        for (Menu titles: response){
            mapOfMenuTitleSubMenuTitle.put(titles.getTitle(), titles.getValues());
            mapOfMenuIdSubMenuId.put(titles.getCat_id(), titles.getSub_id());
            mapOfMenuTitlesMenuId.put(titles.getTitle(), titles.getCat_id());
            List<String> subids = titles.getSub_id();
            int index = 0;
            for (String values: titles.getValues()){
                mapOfSubMenuTitleSubMenuId.put(values, subids.get(index));
                index++;
            }
        }
        titleList = new ArrayList<>(mapOfMenuTitleSubMenuTitle.keySet());
    }



    @Override
    public String toString() {
        return "BottomTitlesMenuResponse{" +
                "response=" + response +
                ", mapOfMenuTitleSubMenuTitle=" + mapOfMenuTitleSubMenuTitle +
                ", titleList=" + titleList +
                '}';
    }

    public Map<String, List<String>> getMapOfMenuTitleSubMenuTitle() {
        return mapOfMenuTitleSubMenuTitle;
    }

    public Map<String, List<String>> getMapOfMenuIdSubMenuId() {
        return mapOfMenuIdSubMenuId;
    }

    public String getCategoryId(String menuTitle){
        return mapOfMenuTitlesMenuId.get(menuTitle);
    }
    public String getSubMenuId(String submenu){
        return mapOfSubMenuTitleSubMenuId.get(submenu);
    }

    private List<String> titleList;

    public List<String> getAllTitles(){
        return titleList;
    }

    public void setMapOfMenuTitleSubMenuTitle(Map<String, List<String>> mapOfMenuTitleSubMenuTitle) {
        this.mapOfMenuTitleSubMenuTitle = mapOfMenuTitleSubMenuTitle;
    }
}
