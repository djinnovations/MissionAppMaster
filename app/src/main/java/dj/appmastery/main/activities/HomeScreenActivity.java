package dj.appmastery.main.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
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
import dj.appmastery.main.model.BottomTitlesMenuResponse;
import dj.appmastery.main.model.SelectionResponse;
import dj.appmastery.main.model.ThumbnailData;
import dj.appmastery.main.model.TitlesData;
import dj.appmastery.main.modules.adapters.SubTitlesAdapter;
import dj.appmastery.main.modules.adapters.TitlesAdapter;
import dj.appmastery.main.modules.fragment.HomeFragment;
import dj.appmastery.main.utils.IDUtils;
import dj.appmastery.main.utils.NetworkResultValidator;
import dj.appmastery.main.utils.URLHelper;

/**
 * Created by User on 23-10-2016.
 */
public class HomeScreenActivity extends BaseActivity {

    private static final String TAG = "HomeScreenActivity";
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.container)
    FrameLayout container;
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
        setContentView(R.layout.activity_home_screen);
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
        setUpRecycleViews();
        queryForTitles();
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(container.getId(), homeFragment).commit();
    }

    public void updateView(boolean isMainMenu, TitlesData data) {
        if (isMainMenu) {
            mainMenu = data.getTitle();
            changeSubMenu(data.getTitle());
        } else {
            subMenu = data.getTitle();
            queryForThumbnail();
        }
    }


    private String mainMenu;
    private String subMenu;

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
    }

    public final int TITLES_CALL = IDUtils.generateViewId();

    public void queryForTitles() {
        startProgress();
        AjaxCallback ajaxCallback = getAjaxCallback(TITLES_CALL);
        ajaxCallback.method(AQuery.METHOD_GET);
        Log.d(TAG, "GET url- queryForTitles()" + TAG + ": " + URLHelper.getAppMasterTitlesAPI());
        getAQuery().ajax(URLHelper.getAppMasterTitlesAPI(), String.class, ajaxCallback);
    }

    public final int THUMBNAIL_CALL = IDUtils.generateViewId();

    public void queryForThumbnail() {
        startProgress();
        AjaxCallback ajaxCallback = getAjaxCallback(THUMBNAIL_CALL);
        ajaxCallback.method(AQuery.METHOD_GET);
        Log.d(TAG, "GET url- queryForThumbnail()" + TAG + ": " + URLHelper.getThumbnailAPI(mainMenu, subMenu));
        getAQuery().ajax(URLHelper.getThumbnailAPI(mainMenu, subMenu), String.class, ajaxCallback);
    }

    BottomTitlesMenuResponse menuResponse;
    SelectionResponse selectionResponse;

    @Override
    public void serverCallEnds(int id, String url, Object json, AjaxStatus status) {
        Log.d(TAG, "url queried-" + TAG + ": " + url);
        Log.d(TAG, "response-" + TAG + ": " + json);
        stopProgress();
        if (id == TITLES_CALL) {
            boolean success = NetworkResultValidator.getInstance().isResultOK(url, (String) json, status, null,
                    progressBar, this);
            if (success) {
                //JSONObject jsonObject = new JSONObject(json.toString());
                menuResponse = new Gson().fromJson((String) json, BottomTitlesMenuResponse.class);
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
                selectionResponse = new Gson().fromJson((String) json, SelectionResponse.class);
                selectionResponse.onParse();
                if (selectionResponse != null) {
                    Log.d("", "parsed SelectionResponse: " + selectionResponse.toString());
                    changeThumbnail();
                }
            }
        } else super.serverCallEnds(id, url, json, status);
    }

    HomeFragment homeFragment;
    private void changeThumbnail() {
        List<ThumbnailData> dataList = new ArrayList<>();
        List<String> urlList = selectionResponse.getUrls();
        int index = 0;
        for (String title : selectionResponse.getTitles()) {
            if (index == 0)
                dataList.add(new ThumbnailData(true, title, urlList.get(index)));
            else dataList.add(new ThumbnailData(false, title, urlList.get(index)));
            index++;
        }
        homeFragment.updateView(dataList);
    }

    public void changeSubMenu(String title) {
        int index = 0;
        List<TitlesData> subtitlesDataList = new ArrayList<>();
        for (String txt : menuResponse.getMapOfMenu().get(title)) {
            if (index == 0)
                subtitlesDataList.add(new TitlesData(txt, true));
            else subtitlesDataList.add(new TitlesData(txt, false));
            index++;
        }
        subMenu = subtitlesDataList.get(0).getTitle();
        mSubTitlesAdapter.changeData(subtitlesDataList);
        queryForThumbnail();
    }
}
