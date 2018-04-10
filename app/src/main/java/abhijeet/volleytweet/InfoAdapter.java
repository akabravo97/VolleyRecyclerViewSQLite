package abhijeet.volleytweet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MAHE on 10-Apr-18.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyViewHolder> {
    ArrayList<Info> infos=new ArrayList<>();

    public InfoAdapter(ArrayList<Info> arrayList) {
        this.infos = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tweet.setText(infos.get(position).getTweet());
        holder.coin_symbol.setText(infos.get(position).getCoin_symbol());
        holder.coin_handle.setText(infos.get(position).getCoin_handle());
        holder.coin_name.setText(infos.get(position).getCoin_name());
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView coin_name,coin_handle,coin_symbol,tweet;
        public MyViewHolder(View itemView) {
            super(itemView);
            coin_name=(TextView)itemView.findViewById(R.id.coin_name);
            coin_handle=(TextView)itemView.findViewById(R.id.coin_handle);
            coin_symbol=(TextView)itemView.findViewById(R.id.coin_symbol);
            tweet=(TextView)itemView.findViewById(R.id.tweet);
        }
    }
}

