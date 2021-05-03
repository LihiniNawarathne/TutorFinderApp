package com.example.tutorfinder.StudentUI;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorfinder.Database.GroupMessageModel;
import com.example.tutorfinder.Database.NotificationModel;
import com.example.tutorfinder.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterNotification extends  RecyclerView.Adapter<AdapterNotification.HolderNotification>{


    Context context;
    List<NotificationModel> notificationList;

    public AdapterNotification(Context context, ArrayList<NotificationModel> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }


    @NonNull
    @Override
    public HolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate view row_notification
        View view = LayoutInflater.from(context).inflate(R.layout.row_notification, parent, false);
        return new HolderNotification(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNotification holder, int position) {
        //get data
        NotificationModel model = notificationList.get(position);
        String message = model.getNotification();
        String timestamp = model.getTimestamp();

        //get Time
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy   HH:mm");
        String time = sdf.format(cal.getTime());

        //set data to view
        holder.tvNotifcation.setText(message);
        holder.tvNoticationtime.setText(time);

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    //holder class for views of row_notification.xml
    class HolderNotification extends RecyclerView.ViewHolder{

        TextView tvNotifcation,tvNoticationtime;

        public HolderNotification(@NonNull View itemView) {
            super(itemView);

            //initialize views
            tvNotifcation= itemView.findViewById(R.id.tvNotifcation);
            tvNoticationtime= itemView.findViewById(R.id.tvNoticationtime);
        }
    }
}
