package com.example.kiruyos.myproject;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Data> mData;
    Dialog dSelecteedProfile;
    private Listener listener;

    //notifikasi
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mNotifyBuilder;
    private Notification myNotification;
    private final int NOTIFICATION_ID = 1621;

    private final String CHANNEL_ID = "botanicalbotanchannel";

    public interface Listener{
        void rmvItem(int position);
    }


    public RecyclerViewAdapter(Context mContext, List<Data> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(mContext).inflate(R.layout.birthday,parent,false);

        final MyViewHolder vHolder = new MyViewHolder(v);

        dSelecteedProfile = new Dialog(mContext);
        dSelecteedProfile.setContentView(R.layout.dialog_birthday);
        dSelecteedProfile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button delete = (Button) v.findViewById(R.id.dialog_trash);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(vHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        vHolder.item_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Coba Click " + String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

                // Inisialisasi Dialog

                TextView name_dialog = (TextView) dSelecteedProfile.findViewById(R.id.dialog_name);
                TextView birthday_dialog = (TextView) dSelecteedProfile.findViewById(R.id.dialog_birthday);
                //TextView phone_dialog = (TextView) dSelecteedProfile.findViewById(R.id.dialog_call);
                ImageView photo_dialog = (ImageView) dSelecteedProfile.findViewById(R.id.dialog_photo);

                name_dialog.setText(mData.get(vHolder.getAdapterPosition()).getName());

                final DateFormat formatter = new SimpleDateFormat("dd MMMM", new Locale("in", "ID"));
                String tanggal = formatter.format(mData.get(vHolder.getAdapterPosition()).getBirthday());

                birthday_dialog.setText(tanggal);
                //phone_dialog.setText(mData.get(vHolder.getAdapterPosition()).getPhone());
                photo_dialog.setImageURI(Uri.parse(mData.get(vHolder.getAdapterPosition()).getPhotoPath()));


                dSelecteedProfile.show();

                Button dialog_call = (Button) dSelecteedProfile.findViewById(R.id.dialog_call);
                dialog_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       String phone = mData.get(vHolder.getAdapterPosition()).getPhone();
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        mContext.startActivity(callIntent);

                      // Toast.makeText(mContext, mData.get(vHolder.getAdapterPosition()).getPhone(), Toast.LENGTH_LONG).show();
                    }
                });

                Button dialog_notif = (Button) dSelecteedProfile.findViewById(R.id.dialog_notif);
                dialog_notif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        sendOnChannell("Today is "+mData.get(vHolder.getAdapterPosition()).getName()+"'s birthday!", "Go congroatulate them!");
                        dSelecteedProfile.dismiss();
                    }
                });
            }

        });


        return vHolder;
    }

    public void sendOnChannell(String title, String content){
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_person_add_black_24dp);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(mContext);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Personal Notification";
            String description = "Include all the personal notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager)  mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DateFormat formatter = new SimpleDateFormat("dd MMMM", new Locale("in", "ID"));
        String tanggal = formatter.format(mData.get(position).getBirthday());
        holder.birthday.setText(tanggal);
        holder.nameview.setText(mData.get(position).getName());
        holder.img.setImageURI(Uri.parse(mData.get(position).getPhotoPath()));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item_birthday;
        private TextView nameview, birthday;
        private ImageView img;


        public MyViewHolder(View itemView) {
            super(itemView);

            item_birthday = (LinearLayout) itemView.findViewById(R.id.birthday_id);
            nameview = (TextView) itemView.findViewById(R.id.nameview);
            birthday = (TextView) itemView.findViewById(R.id.b_day);
            img = (ImageView) itemView.findViewById(R.id.profile);
        }
    }



}
