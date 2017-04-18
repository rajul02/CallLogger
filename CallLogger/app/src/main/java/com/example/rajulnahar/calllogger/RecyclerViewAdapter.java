package com.example.rajulnahar.calllogger;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    Context context;
    Database database;
    LogInfo logInfo;
    private List<UserDetails> userDetailses;
    RecyclerViewAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        userDetailses= new ArrayList<>();

    }

    public void setUserDetailses(List<UserDetails> userDetailses) {
        this.userDetailses = userDetailses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        database = Database.getInstance(context);
        View view = layoutInflater.inflate(R.layout.user_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        if(viewHolder != null) {
            return viewHolder;
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UserDetails userDetails = userDetailses.get(position);
        holder.name.setText(userDetails.name);
        holder.phone.setText(userDetails.phone);
        logInfo = new LogInfo();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:00"));
        final Date current = cal.getTime();

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "qeer"+userDetailses.get(position).name, Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+userDetailses.get(position).phone));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                logInfo.msg = "dailed";
                logInfo.name = userDetailses.get(position).name;
                logInfo.time =String.valueOf(current);
                database.addLogInfo(logInfo);
                context.startActivity(callIntent);

            }
        });
    }



    @Override
    public int getItemCount() {
        if(userDetailses != null){
            return userDetailses.size();
        }

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView phone;
        CardView card;
        ViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);
            card = (CardView) view.findViewById(R.id.card);
        }
    }

}
