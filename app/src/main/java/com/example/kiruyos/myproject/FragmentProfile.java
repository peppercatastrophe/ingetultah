package com.example.kiruyos.myproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class FragmentProfile extends android.support.v4.app.Fragment {

    View v;
    ImageView fotoprofil;
    Thread uploadgambar;
    Runnable runnable;
    EditText editultah, editnama, edithape;
    Calendar myCalendar;
    Button btTambbah;
    String selectedImage;
    private Listener listener;
    Context mContext;
    LinearLayout bgProfil;
    SharedPreferences mPreference;
    private String sharedPrefFile = "com.example.kiruyos.myproject";
    int bgProfilState;


    //notifikasi
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mNotifyBuilder;
    private Notification myNotification;
    private final int NOTIFICATION_ID = 1621;

    private final String CHANNEL_ID = "botanicalbotanchannel";




    public interface Listener {
        void addItem(String name, Date bday, String photopath, String phone);
        void test();
    }

    public FragmentProfile() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_profile, container, false);
        btTambbah = (Button) v.findViewById(R.id.btTambah);
        fotoprofil = (ImageView) v.findViewById(R.id.photoprofile);
        fotoprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadgambar == null || uploadgambar.getState() == Thread.State.TERMINATED) {
                    runnable();
                    uploadgambar = new Thread(runnable);
                    uploadgambar.start();
                } else{
                    uploadgambar.interrupt();
                    runnable();
                    uploadgambar.start();
                }
            }
        });

        bgProfil = (LinearLayout) v.findViewById(R.id.bgProfil);
        // set color
        mPreference = this.getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        bgProfilState = mPreference.getInt("bgcolor", 1);
        if (bgProfilState == 1){
            gantiBgProfil(1);
        } else {
            gantiBgProfil(2);
        }


        myCalendar = Calendar.getInstance();
        editnama = (EditText) v.findViewById(R.id.tname);
        edithape = (EditText) v.findViewById(R.id.tphone);
        editultah = (EditText) v.findViewById(R.id.tbirthday);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTextViewBirthday();
            }

        };

        editultah.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

       btTambbah.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               if(editnama.getText().toString().trim().equals("") || edithape.getText().toString().trim().equals("") || edithape.getText().toString().trim().equals("")){
                   Toast.makeText(getActivity().getBaseContext(),"Name, Birthday and Phone cant be empty", Toast.LENGTH_LONG).show();
               }
               else
                   {
                       try{
                           if(selectedImage == null) {
                               Uri path = Uri.parse("android.resource://com.example.kiruyos.myproject/" + R.drawable.defaultd);
                               String imageSrc = path.toString();
                               listener.addItem(editnama.getText().toString(), myCalendar.getTime(), imageSrc,edithape.getText().toString());

                           } else{
                               listener.addItem(editnama.getText().toString(), myCalendar.getTime(), Uri.parse(selectedImage).toString(),edithape.getText().toString());
                           }

                           sendOnChannell("Profile added succesfully", editnama.getText().toString() + " is now on your list!");
                           resetInput();

                       } catch(Exception e){
                           e.getStackTrace();
                           resetInput();
                   }

               }
           }
       });
        return v;
    }

    public void sendOnChannell(String title, String content){
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getBaseContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_person_add_black_24dp);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(getActivity().getBaseContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Personal Notification";
            String description = "Include all the personal notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager)  getContext().getSystemService(getContext().NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    private void updateTextViewBirthday() {
        DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
        String tanggal = formatter.format(myCalendar.getTime());
        editultah.setText(tanggal);
    }


    private void runnable(){
         this.runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Intent browse = new Intent();
                    browse.setAction(Intent.ACTION_GET_CONTENT);
                    browse.setType("image/*");
                    startActivityForResult(browse, 0);
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==0)&&(resultCode== Activity.RESULT_OK)){
            this.selectedImage= data.getDataString();
            fotoprofil.setImageURI(Uri.parse(selectedImage));
        }

    }

    public void gantiBgProfil(int c){
        if (c == 1)
            bgProfil.setBackgroundResource(R.drawable.background1);
        if (c == 2)
            bgProfil.setBackgroundResource(R.drawable.background);
    }

    private void resetInput(){
        edithape.setText("");
        editnama.setText("");
        editultah.setText("");
        fotoprofil.setImageResource(R.drawable.defaultd);
    }

}

