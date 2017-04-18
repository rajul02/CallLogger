package com.example.rajulnahar.calllogger;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajul Nahar on 10-04-2017.
 */


public class RecyclerViewLogAdapter extends RecyclerView.Adapter<RecyclerViewLogAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    Context context;
    private List<LogInfo> logs;
    RecyclerViewLogAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }

    public void setLogs(List<LogInfo> logs) {
        this.logs = logs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.log_card,parent,false);
        RecyclerViewLogAdapter.ViewHolder viewHolder = new RecyclerViewLogAdapter.ViewHolder(view);
        if(viewHolder != null) {
            return viewHolder;
        }
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LogInfo log= logs.get(position);
        holder.log_name.setText(log.name);
        holder.log_time.setText(log.time);
        holder.log_msg.setText(log.msg);
    }

    @Override
    public int getItemCount() {
        if(logs!=null){
            return logs.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    TextView log_name;
    TextView log_msg;
    TextView log_time;
    public ViewHolder(View itemView) {
        super(itemView);
        log_msg= (TextView) itemView.findViewById(R.id.log_msg);
        log_name=(TextView) itemView.findViewById(R.id.log_name);
        log_time=(TextView) itemView.findViewById(R.id.log_time);
    }
}

}
