package com.example.zahidmahmudemon.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Playlist extends AppCompatActivity {

    ListView lv;
    String[] product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        setTitle(" Audio PlayList");




        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.songplaylist);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        lv=findViewById(R.id.audioplaylist);


        final ArrayList<File> mysongs=findsongs(Environment.getExternalStorageDirectory());

        product= new String[mysongs.size()];
        int sz=mysongs.size();
        for(int i=0; i<sz; i++){
            product[i]=mysongs.get(i).getName().toString().replace(".mp3","").replace(".amr","");
        }
        ArrayAdapter<String> adp=new ArrayAdapter<String>(getApplicationContext(),R.layout.song_layout,R.id.textView,product);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),AudioPlayerActivity.class).putExtra("pos",position).putExtra("songlist",mysongs));
            }
        });

    }


    public ArrayList<File> findsongs(File root){
        ArrayList<File> al =new ArrayList<File>();
          File[] files = root.listFiles();
          for(File singlefile : files){
              if(singlefile.isDirectory() && !singlefile.isHidden()){
                al.addAll(findsongs(singlefile));
              }
              else {
                  /*if(singlefile.getName().endsWith(".mp3")  || singlefile.getName().endsWith(".amr")){
                       al.add(singlefile);
                  }*/if(singlefile.getName().endsWith(".mp3")) {
                      al.add(singlefile);
                  }

              }
          }
          return al;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.playlistoptionmanulayout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.settingsid){
            Toast.makeText(this,"Settings Button is Clicked",Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this,"Permission Not Granted",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

}
