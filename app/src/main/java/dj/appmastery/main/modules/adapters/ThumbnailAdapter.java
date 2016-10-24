package dj.appmastery.main.modules.adapters;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.appmastery.main.R;
import dj.appmastery.main.activities.MyApplication;
import dj.appmastery.main.model.ThumbnailData;
import dj.appmastery.main.model.TitlesData;
import dj.appmastery.main.uiutils.ResourceReader;

/**
 * Created by User on 23-10-2016.
 */
public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder>{

    List<ThumbnailData> thumbnailDataList = new ArrayList<>();
    public interface MenuSelectionListener{
        void onMenuSelected(ThumbnailData data);
    }

    private MenuSelectionListener listener;
    public void changeData(List<ThumbnailData> thumbnailDataList){
        this.thumbnailDataList = thumbnailDataList;
        notifyDataSetChanged();
    }

    public ThumbnailAdapter(MenuSelectionListener listener) {
        this.listener = listener;
    }

    @Override
    public ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_thumbnail, parent, false);
        return new ThumbnailViewHolder(view);
    }

    ThumbnailData previousSelection;

    @Override
    public void onBindViewHolder(ThumbnailViewHolder holder, int position) {
        ThumbnailData data = thumbnailDataList.get(position);
        holder.tvTitle.setText(data.getTitle());
        if (!TextUtils.isEmpty(data.getUrl()))
            Picasso.with(MyApplication.getInstance())
                    .load(data.getUrl())
                    .placeholder(R.drawable.vector_icon_progress_animation_white)
                    .into(holder.ivThumbnail);
        if (data.isSelected()) {
            holder.ivThumbnail.setBackground(ResourceReader.getInstance()
                    .getDrawableFromResId(R.drawable.blue_square_border));
            previousSelection = data;
        }
        else {
            holder.ivThumbnail.setBackground(null);
        }
    }

    @Override
    public int getItemCount() {
        return thumbnailDataList.size();
    }

    class ThumbnailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @Bind(R.id.tvTitle)
        TextView tvTitle;

        public ThumbnailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = thumbnailDataList.indexOf(previousSelection);
            if (pos == getAdapterPosition())
                return;
            listener.onMenuSelected(thumbnailDataList.get(getAdapterPosition()));
            ThumbnailData data = thumbnailDataList.get(getAdapterPosition());
            data.setSelected(true);
            thumbnailDataList.set(getAdapterPosition(), data);
            previousSelection.setSelected(false);
            thumbnailDataList.set(pos, previousSelection);
            notifyDataSetChanged();
        }
    }
}
