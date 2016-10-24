package dj.appmastery.main.modules.adapters;

import android.support.v7.widget.RecyclerView;
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
public class TitlesAdapter extends RecyclerView.Adapter<TitlesAdapter.TitlesViewHolder>{

    public interface MenuSelectionListener{
        void onMenuSelected(TitlesData data);
    }

    private MenuSelectionListener listener;

    List<TitlesData> titles = new ArrayList<>();

    public void changeData(List<TitlesData> titles){
        this.titles = titles;
        notifyDataSetChanged();
    }

    public TitlesAdapter(MenuSelectionListener listener) {
        this.listener = listener;
    }

    protected int getRootLayout(){
        return R.layout.adapter_main_menu;
    }

    @Override
    public TitlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getRootLayout(), parent, false);
        return new TitlesViewHolder(view);
    }

    private TitlesData previousSelection;
    @Override
    public void onBindViewHolder(TitlesViewHolder holder, int position) {
        TitlesData titlesData = titles.get(position);
        holder.tvMenu.setText(titlesData.getTitle());
        if (titlesData.isSelected()) {
            holder.tvMenu.setBackgroundColor(ResourceReader.getInstance()
                    .getColorFromResource(R.color.colorBlack));
            previousSelection = titlesData;
        }
        else holder.tvMenu.setBackgroundColor(ResourceReader.getInstance()
                .getColorFromResource(R.color.colorBlackDimText));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class TitlesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.tvMenu)
        TextView tvMenu;
        public TitlesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = titles.indexOf(previousSelection);
            if (pos == getAdapterPosition())
                return;
            listener.onMenuSelected(titles.get(getAdapterPosition()));
            TitlesData data = titles.get(getAdapterPosition());
            data.setSelected(true);
            titles.set(getAdapterPosition(), data);
            previousSelection.setSelected(false);
            titles.set(pos, previousSelection);
            notifyDataSetChanged();
        }
    }
}
