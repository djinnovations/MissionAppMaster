package dj.appmastery.main.modules.adapters;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.appmastery.main.R;
import dj.appmastery.main.model.TitlesData;
import dj.appmastery.main.uiutils.ResourceReader;

/**
 * Created by User on 23-10-2016.
 */
public class TitlesAdapter extends RecyclerView.Adapter<TitlesAdapter.TitlesViewHolder> {

    public interface MenuSelectionListener {
        void onMenuSelected(TitlesData data);
    }

    private MenuSelectionListener listener;

    List<TitlesData> titles = new ArrayList<>();

    public void changeData(List<TitlesData> titles) {
        this.titles = new ArrayList<>(titles);
        notifyDataSetChanged();
    }

    public TitlesAdapter(MenuSelectionListener listener) {
        this.listener = listener;
    }

    protected int getRootLayout() {
        return R.layout.adapter_main_menu;
    }

    @Override
    public TitlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getRootLayout(), parent, false);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        return new TitlesViewHolder(view);
    }

    private TitlesData previousSelection;
    private final String TAG = "TitlesAdapter";
    //boolean iscalledfromreq = false;

    @Override
    public void onBindViewHolder(final TitlesViewHolder holder, final int position) {
        final TitlesData titlesData = titles.get(position);
        /*holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    Log.d(TAG, "has focus");
                    *//*if (!iscalledfromreq) {*//*
                        holder.tvMenu.setBackground(ResourceReader.getInstance()
                                .getDrawableFromResId(R.drawable.blue_square_border));
                    //}
                    //iscalledfromreq = false;
                } else if (titlesData.isSelected()) {
                    holder.tvMenu.setBackgroundColor(ResourceReader.getInstance()
                            .getColorFromResource(R.color.colorBlack));
                } else {
                    holder.tvMenu.setBackgroundColor(ResourceReader.getInstance()
                            .getColorFromResource(R.color.colorBlackDimText));
                }
            }
        });*/

        if (titlesData.isSelected()) {
            holder.itemView.requestFocus();
            /*holder.tvMenu.setBackgroundColor(ResourceReader.getInstance()
                    .getColorFromResource(R.color.colorBlack));*/
            holder.itemView.setSelected(true);
            previousSelection = titlesData;
        }
        holder.tvMenu.setText(titlesData.getTitle());
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class TitlesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.tvMenu)
        TextView tvMenu;

        public TitlesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int pos = titles.indexOf(previousSelection);
            if (pos == getAdapterPosition())
                return;
            listener.onMenuSelected(titles.get(getAdapterPosition()));
            TitlesData data = titles.get(getAdapterPosition());
            data.setSelected(true);
            titles.set(getAdapterPosition(), data);
            previousSelection.setSelected(false);
            titles.set(pos, previousSelection);
            (new Handler(Looper.getMainLooper())).post(new Runnable() {
                @Override
                public void run() {
                    //notifyItemChanged(pos);
                    //notifyItemChanged(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
