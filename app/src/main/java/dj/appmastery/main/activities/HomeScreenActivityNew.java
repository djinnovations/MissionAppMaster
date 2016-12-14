package dj.appmastery.main.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.appmastery.main.R;
import dj.appmastery.main.model.MenuResponse;
import dj.appmastery.main.model.SelectionResponseNew;
import dj.appmastery.main.model.ThumbnailData;
import dj.appmastery.main.model.TitlesData;
import dj.appmastery.main.modules.adapters.SubTitlesAdapter;
import dj.appmastery.main.modules.adapters.ThumbnailAdapter;
import dj.appmastery.main.modules.adapters.TitlesAdapter;
import dj.appmastery.main.server.URLHelper;
import dj.appmastery.main.utils.IDUtils;
import dj.appmastery.main.utils.NetworkResultValidator;

/**
 * Created by User on 23-10-2016.
 */
public class HomeScreenActivityNew extends BaseActivity {

    private static final String TAG = "HomeScreenActivityNew";
    /*@Bind(R.id.container)
    FrameLayout container;*/
    @Bind(R.id.rvSubMenu)
    RecyclerView rvSubMenu;
    @Bind(R.id.rvMainMenu)
    RecyclerView rvMainMenu;

    TitlesAdapter mTitlesAdapter;
    SubTitlesAdapter mSubTitlesAdapter;

    TitlesAdapter.MenuSelectionListener mainMenuSelectionListener;
    TitlesAdapter.MenuSelectionListener subMenuSelectionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_new);
        ButterKnife.bind(this);
        setProgressBar(progressBar);
        mainMenuSelectionListener = new TitlesAdapter.MenuSelectionListener() {
            @Override
            public void onMenuSelected(TitlesData data) {
                updateView(true, data);
            }
        };
        subMenuSelectionListener = new TitlesAdapter.MenuSelectionListener() {
            @Override
            public void onMenuSelected(TitlesData data) {
                updateView(false, data);
            }
        };
        vidSelectionListener = new ThumbnailAdapter.MenuSelectionListener() {
            @Override
            public void onMenuSelected(ThumbnailData data) {

            }
        };
        setUpRecycleViews();
        queryForMenus();
        /*homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(container.getId(), homeFragment).commit();*/
    }

    public void updateView(boolean isMainMenu, TitlesData data) {
        if (isMainMenu) {
            mainMenu = data.getTitle();
            changeSubMenu(data.getTitle());
        } else {
            subMenu = data.getTitle();
            queryForThumbnail(mainMenu, subMenu);
        }
    }

    @Bind(R.id.rvThumbnail)
    RecyclerView rvThumbnail;

    private String mainMenu;
    private String subMenu;

    /*@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //return super.onKeyUp(keyCode, event);
        *//*Log.d(TAG, "keycode: "+keyCode);
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
            if (homeFragment != null)
                homeFragment.moveOver(true);
        }
        else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
            if (homeFragment != null)
                homeFragment.moveOver(false);
        }
        return super.onKeyUp(keyCode, event);*//*
    }*/

    ThumbnailAdapter.MenuSelectionListener vidSelectionListener;
    private ThumbnailAdapter thumbnailAdapter;

    public void updateThumbnailView(List<ThumbnailData> dataList){
        thumbnailAdapter.changeData(dataList);
    }

    private void setUpRecycleViews() {
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMainMenu.setHasFixedSize(false);
        rvMainMenu.setLayoutManager(mLayoutManager1);
        rvMainMenu.setItemAnimator(new DefaultItemAnimator());
        mTitlesAdapter = new TitlesAdapter(mainMenuSelectionListener);
        rvMainMenu.setAdapter(mTitlesAdapter);

        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSubMenu.setHasFixedSize(false);
        rvSubMenu.setLayoutManager(mLayoutManager2);
        rvSubMenu.setItemAnimator(new DefaultItemAnimator());
        mSubTitlesAdapter = new SubTitlesAdapter(subMenuSelectionListener);
        rvSubMenu.setAdapter(mSubTitlesAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvThumbnail.setHasFixedSize(false);
        rvThumbnail.setLayoutManager(mLayoutManager);
        rvThumbnail.setItemAnimator(new DefaultItemAnimator());
        thumbnailAdapter = new ThumbnailAdapter(vidSelectionListener);
        rvThumbnail.setAdapter(thumbnailAdapter);
    }

    private final int MAIN_MENU_CALL = IDUtils.generateViewId();

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    public void queryForMenus() {
        //WindowUtils.justPlainOverLay = true;
        //showOverLay(null, 0, WindowUtils.PROGRESS_FRAME_GRAVITY_CENTER);
        startProgress();
        AjaxCallback ajaxCallback = getAjaxCallback(MAIN_MENU_CALL);
        ajaxCallback.method(AQuery.METHOD_GET);
        Log.d(TAG, "GET url- queryForTitles()" + TAG + ": " + URLHelper.getInstance().getMainMenuAPI());
        getAQuery().ajax(URLHelper.getInstance().getMainMenuAPI(), String.class, ajaxCallback);
    }


    public final int THUMBNAIL_CALL = IDUtils.generateViewId();

    public void queryForThumbnail(String mainMenuTitle, String subMenuTitle) {
        if (menuResponse == null)
            return;
        /*WindowUtils.justPlainOverLay = true;
        showOverLay(null, 0, WindowUtils.PROGRESS_FRAME_GRAVITY_CENTER);
        startProgress();*/
        String categoryId = menuResponse.getCategoryId(mainMenuTitle);
        String subMenuId = menuResponse.getSubMenuId(subMenuTitle);
        AjaxCallback ajaxCallback = getAjaxCallback(THUMBNAIL_CALL);
        ajaxCallback.method(AQuery.METHOD_GET);
        Log.d(TAG, "GET url- queryForThumbnail()" + TAG + ": " + URLHelper.getInstance().getVideoThumbnails(categoryId, subMenuId));
        getAQuery().ajax(URLHelper.getInstance().getVideoThumbnails(categoryId, subMenuId), String.class, ajaxCallback);
    }
    MenuResponse menuResponse;
    SelectionResponseNew selectionResponse;

    @Override
    public void serverCallEnds(int id, String url, Object json, AjaxStatus status) {
        Log.d(TAG, "url queried-" + TAG + ": " + url);
        Log.d(TAG, "response-" + TAG + ": " + json);
        stopProgress();
        if (id == MAIN_MENU_CALL) {
            boolean success = NetworkResultValidator.getInstance().isResultOK(url, (String) json, status, null,
                    progressBar, this);
            if (success) {
                //JSONObject jsonObject = new JSONObject(json.toString());
                menuResponse = new Gson().fromJson((String) json, MenuResponse.class);
                menuResponse.onParse();
                if (menuResponse != null) {
                    Log.d("", "parsed-BottomTitlesMenuResponse : " + menuResponse.toString());
                    List<TitlesData> titlesDataList = new ArrayList<>();
                    int index = 0;
                    for (String txt : menuResponse.getAllTitles()) {
                        if (index == 0)
                            titlesDataList.add(new TitlesData(txt, true));
                        else titlesDataList.add(new TitlesData(txt, false));
                        index++;
                    }
                    mainMenu = titlesDataList.get(0).getTitle();
                    mTitlesAdapter.changeData(titlesDataList);
                    /*index = 0;
                    List<TitlesData> subtitlesDataList = new ArrayList<>();
                    for (String txt: menuResponse.getMapOfMenu().get(titlesDataList.get(0).getTitle())){
                        if (index == 0)
                            subtitlesDataList.add(new TitlesData(txt, true));
                        else subtitlesDataList.add(new TitlesData(txt, false));
                        index++;
                    }
                    mSubTitlesAdapter.changeData(subtitlesDataList);*/
                    changeSubMenu(titlesDataList.get(0).getTitle());
                }
            }
        } else if (id == THUMBNAIL_CALL) {
            boolean success = NetworkResultValidator.getInstance().isResultOK(url, (String) json, status, null,
                    progressBar, this);
            if (success) {
                selectionResponse = new Gson().fromJson((String) json, SelectionResponseNew.class);
                selectionResponse.onParse();
                if (selectionResponse != null) {
                    Log.d("", "parsed SelectionResponse: " + selectionResponse.toString());
                    changeThumbnail();
                }
            }
        } else super.serverCallEnds(id, url, json, status);
    }

    //HomeFragment homeFragment;
    private void changeThumbnail() {
        List<ThumbnailData> dataList = new ArrayList<>();
        List<String> urlList = selectionResponse.getUrlList();
        int index = 0;
        for (String title : selectionResponse.getTitleList()) {
            if (index == 0)
                dataList.add(new ThumbnailData(true, title, urlList.get(index)));
            else dataList.add(new ThumbnailData(false, title, urlList.get(index)));
            index++;
        }
        updateThumbnailView(dataList);
    }

    public void changeSubMenu(String title) {
        int index = 0;
        List<TitlesData> subtitlesDataList = new ArrayList<>();
        for (String txt : menuResponse.getMapOfMenuTitleSubMenuTitle().get(title)) {
            if (index == 0)
                subtitlesDataList.add(new TitlesData(txt, true));
            else subtitlesDataList.add(new TitlesData(txt, false));
            index++;
        }
        if (subtitlesDataList.size() > 0)
            subMenu = subtitlesDataList.get(0).getTitle();
        subMenu = "";
        mSubTitlesAdapter.changeData(subtitlesDataList);
        queryForThumbnail(mainMenu, subMenu);
    }
}
