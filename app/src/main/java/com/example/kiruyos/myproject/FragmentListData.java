package com.example.kiruyos.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentListData extends android.support.v4.app.Fragment{

    View v;
    private RecyclerView recyclersaia;
    private List<Data> datasaia;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //isi data sampel
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
//        DateFormat formatter = new SimpleDateFormat("dd MMMM", new Locale("in", "ID"));
//        String sampeltanggal = formatter.format(currentDate);
        datasaia = new ArrayList<>();
        Uri path = Uri.parse("android.resource://com.example.kiruyos.myproject/" + R.drawable.defaultd);
        String imageSrc = path.toString();

        datasaia.add(new Data("Botan", currentDate, imageSrc,"020202020"));
        datasaia.add(new Data("Eiko", currentDate, imageSrc,"030303030"));

    }

    private List<Data> listsaia;

    public void addData(String name, Date birthday, String photopath, String phone){
        datasaia.add(new Data(name, birthday, photopath, phone));
        updaterecycler();
    }

    public void rmvItem(int position) {
        datasaia.remove(position);
        updaterecycler();
        //Toast.makeText(getContext(),"sini", Toast.LENGTH_LONG).show();
    }

    public FragmentListData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_listdata,container,false);
        updaterecycler();
        return v;
    }

    public void updaterecycler(){
        recyclersaia = (RecyclerView) v.findViewById(R.id.rv_data);
        RecyclerViewAdapter rv_adapter = new RecyclerViewAdapter(getContext(), datasaia);
        recyclersaia.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclersaia.setAdapter(rv_adapter);
    }


}
