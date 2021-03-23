package com.example.zahidmahmudemon.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class VideoPlayerActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer=AudioPlayerActivity.mp;
    VideoView v;
    ArrayList<File> myvideos;
    int position;
    int sz;

    MediaController m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        setTitle(" Video Player");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.video33player);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        v=findViewById(R.id.v1);
        m=new MediaController(this);



        Intent i = getIntent();
        Bundle b = i.getExtras();



        myvideos = (ArrayList) b.getParcelableArrayList("videolist");
        position = b.getInt("pos", 0);
        sz=myvideos.size();

        v.setVideoPath(myvideos.get(position).toString());


        m.setAnchorView(v);
        v.setMediaController(m);
        if(mediaPlayer==null){

           v.start();
        }
        else if(mediaPlayer.isPlaying()) {

            AudioPlay.stopAudio();
            v.start();
        }
        else {
            v.start();
        }







    }
}
