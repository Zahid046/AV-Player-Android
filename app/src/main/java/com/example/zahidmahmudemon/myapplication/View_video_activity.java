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

public class View_video_activity extends AppCompatActivity {
    ListView lv1;
    String[] video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video_activity);

        setTitle(" Video PlayList");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.playlist1);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        lv1=findViewById(R.id.videoplaylist);


        final ArrayList<File> myvideos=findvideos(Environment.getExternalStorageDirectory());

        video= new String[myvideos.size()];
        int sz=myvideos.size();
        for(int i=0; i<sz; i++){
            video[i]=myvideos.get(i).getName().toString().replace(".mp4","").replace(".mkv","");
        }
        ArrayAdapter<String> adp=new ArrayAdapter<String>(getApplicationContext(),R.layout.song_layout,R.id.textView,video);
        lv1.setAdapter(adp);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),VideoPlayerActivity.class).putExtra("pos",position).putExtra("videolist",myvideos));
            }
        });
    }

    public ArrayList<File> findvideos(File root){
        ArrayList<File> al =new ArrayList<File>();
        File[] files = root.listFiles();
        for(File singlefile : files){
            if(singlefile.isDirectory() && !singlefile.isHidden()){
                al.addAll(findvideos(singlefile));
            }
            else {
                /*if(singlefile.getName().endsWith(".mp4")  || singlefile.getName().endsWith(".mkv")){
                    al.add(singlefile);
                }*/
                if(singlefile.getName().endsWith(".mp4")){
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
