package dj.appmastery.main.modules.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.appmastery.main.R;
import dj.appmastery.main.activities.MyApplication;
import dj.appmastery.main.modules.billing.BillingData;

/**
 * Created by User on 23-10-2016.
 */
public class BillingThumbnailAdapter extends RecyclerView.Adapter<BillingThumbnailAdapter.ThumbnailViewHolder>{

    List<BillingData> dataList = new ArrayList<>();
    public interface MenuSelectionListener{
        void onMenuSelected(BillingData data);
    }

    public List<BillingData> getDataList(){
        return dataList;
    }

    protected MenuSelectionListener listener;
    public void changeData(List<BillingData> thumbnailDataList){
        this.dataList = new ArrayList<>(thumbnailDataList);
        notifyDataSetChanged();
    }


    public void setExisting(int index, BillingData data){
        this.dataList.set(index, data);
        notifyItemChanged(index);
    }


    /*protected boolean showTitle(){
        return true;
    }*/

    //List<Integer> colorList;
    public BillingThumbnailAdapter(MenuSelectionListener listener) {
        this.listener = listener;
        /*colorList = new ArrayList<>();
        //int[] colors = MyApplication.getInstance().getResources().getIntArray(R.array.imageBank);
        Integer[] temp = new Integer[]{R.drawable.img_tmb_1, R.drawable.img_tmb_2, R.drawable.img_tmb_3, R.drawable.img_tmb_4};
        *//*int i = 0;
        for (int col : colors) {
            temp[i] = col;
            i++;
        }*//*
        colorList.addAll(Arrays.asList(temp));
        Collections.shuffle(colorList);*/
    }

    @Override
    public ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_thumbnail_billing, parent, false);
        return new ThumbnailViewHolder(view);
    }

    //ThumbnailData previousSelection;
    int index = 0;

    @Override
    public void onBindViewHolder(ThumbnailViewHolder holder, int position) {
        BillingData data = dataList.get(position);
        if (data.isPurchased()) {
            holder.tvExpiry.setVisibility(View.VISIBLE);
            holder.btnBuy.setVisibility(View.GONE);
            holder.tvExpiry.setText(data.getExpiryDate());
        }else {
            holder.tvExpiry.setVisibility(View.GONE);
            holder.btnBuy.setVisibility(View.VISIBLE);
            //holder.tvExpiry.setText(data.getExpiryDate());
        }
        /*if (index > 3)
            index = 0;
        //int rand = IDUtils.randInt(0, 2);
        if (true)
            Picasso.with(MyApplication.getInstance())
                    .load(colorList.get(index))
                    .placeholder(R.drawable.vector_icon_progress_animation)
                    .into(holder.ivThumbnail);
        index++;*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected class ThumbnailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @Bind(R.id.tvExpiry)
        TextView tvExpiry;
        @Bind(R.id.btnBuy)
        Button btnBuy;

        public ThumbnailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivThumbnail.setOnClickListener(this);
            btnBuy.setOnClickListener(this);
            //TypefaceHelper.setFont(tvTitle);
        }

        @Override
        public void onClick(View v) {
            //// TODO: 25-10-2016
            if (v.getId() == ivThumbnail.getId()){
                if (dataList.get(getAdapterPosition()).isPurchased())
                    Toast.makeText(MyApplication.getInstance(), "You are viewing this content and the subscription will" +
                            "end on "+dataList.get(getAdapterPosition()).getExpiryDate(), Toast.LENGTH_LONG).show();
                else Toast.makeText(MyApplication.getInstance(), "Purchase to view", Toast.LENGTH_SHORT).show();
            }
            else if (v.getId() == btnBuy.getId()){
                if (listener != null)
                    listener.onMenuSelected(dataList.get(getAdapterPosition()));
            }
        }
    }
}
