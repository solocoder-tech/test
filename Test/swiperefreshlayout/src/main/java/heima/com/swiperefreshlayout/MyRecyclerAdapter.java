package heima.com.swiperefreshlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhuwujing on 2017/6/25.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter {
    private List<ItemBean> mList;
    private Context context;

    public MyRecyclerAdapter(Context context, List<ItemBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.image.setImageResource(mList.get(position).id);
        myViewHolder.text.setText(mList.get(position).title);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() :0 ;
    }
    private class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
