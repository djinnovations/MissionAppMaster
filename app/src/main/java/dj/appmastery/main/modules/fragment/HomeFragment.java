package dj.appmastery.main.modules.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.appmastery.main.R;
import dj.appmastery.main.model.ThumbnailData;
import dj.appmastery.main.modules.adapters.ThumbnailAdapter;

/**
 * Created by User on 23-10-2016.
 */
public class HomeFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }


    @Bind(R.id.rvThumbnail)
    RecyclerView rvThumbnail;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        menuSelectionListener = new ThumbnailAdapter.MenuSelectionListener() {
            @Override
            public void onMenuSelected(ThumbnailData data) {

            }
        };
        setUpRecycleView();
    }

    public void updateView(ThumbnailData data){
        //// TODO: 24-10-2016
    }

    ThumbnailAdapter.MenuSelectionListener menuSelectionListener;
    private ThumbnailAdapter thumbnailAdapter;

    private void setUpRecycleView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvThumbnail.setHasFixedSize(false);
        rvThumbnail.setLayoutManager(mLayoutManager);
        rvThumbnail.setItemAnimator(new DefaultItemAnimator());
        thumbnailAdapter = new ThumbnailAdapter(menuSelectionListener);
        rvThumbnail.setAdapter(thumbnailAdapter);
    }

    public void updateView(List<ThumbnailData> dataList){
        thumbnailAdapter.changeData(dataList);
    }

    public void moveOver(boolean isNext){
        thumbnailAdapter.performClick(isNext);
    }
}
