package com.example.zahidmahmudemon.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AudioPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    static MediaPlayer mp=null;
    ArrayList<File> mysongs;
    int position;
    Uri u;
    Thread UpdateSeekBar;
    int totalDuration;
    int currentPosition;
    int sz;
    SeekBar sb;
    Button btplay, btff, btfb, btprev, btnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        setTitle(" Music Player");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.musicalnote);
        getSupportActionBar().setDisplayUseLogoEnabled(true);





        btplay = findViewById(R.id.pause);
        btff = findViewById(R.id.btfastfor);
        btfb = findViewById(R.id.btfastback);
        btnext = findViewById(R.id.btnext);
        btprev = findViewById(R.id.btprev);


        btplay.setOnClickListener(this);
        btff.setOnClickListener(this);
        btfb.setOnClickListener(this);
        btnext.setOnClickListener(this);
        btprev.setOnClickListener(this);

        sb = findViewById(R.id.seek);
        UpdateSeekBar = new Thread() {
            @Override
            public void run() {
                totalDuration = mp.getDuration();
                currentPosition = 0;

                while (currentPosition <= totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mp.getCurrentPosition();
                        sb.setProgress(currentPosition);
                        if(currentPosition>=totalDuration){
                            u = Uri.parse(mysongs.get(position).toString());
                            mp = MediaPlayer.create(getApplicationContext(), u);
                            mp.start();
                            sb.setMax(mp.getDuration());

                            UpdateSeekBar.start();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                //super.run();
            }
        };


        if (mp != null) {
            mp.stop();
            mp.release();
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();

        mysongs = (ArrayList) b.getParcelableArrayList("songlist");
        position = b.getInt("pos", 0);
        sz=mysongs.size();


        u = Uri.parse(mysongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();
        sb.setMax(mp.getDuration());
        UpdateSeekBar.start();


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.pause) {
                if (mp.isPlaying()) {

                    btplay.setText(">");
                    mp.pause();
                } else {

                    mp.start();
                    btplay.setText("||");
                }
            }
            if (v.getId() == R.id.btfastfor) {
                mp.seekTo(mp.getCurrentPosition() + 5000);
                currentPosition=mp.getCurrentPosition();
                if(currentPosition>=totalDuration){
                    mp.stop();
                    mp.release();
                    btplay.setText("||");
                    position = (position + 1) % sz;
                    u = Uri.parse(mysongs.get(position).toString());
                    mp = MediaPlayer.create(getApplicationContext(), u);
                    mp.start();
                    sb.setMax(mp.getDuration());
                    UpdateSeekBar.start();
                }
            }
            if (v.getId() == R.id.btfastback) {
                mp.seekTo(mp.getCurrentPosition() - 5000);


            }
            if (v.getId() == R.id.btnext) {
                mp.stop();
                mp.release();
                btplay.setText("||");
                position = (position + 1) % sz;
                u = Uri.parse(mysongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                mp.start();
                sb.setMax(mp.getDuration());
                UpdateSeekBar.start();

            }
            if (v.getId() == R.id.btprev) {
                mp.stop();
                mp.release();
                btplay.setText("||");
                position = (position - 1 < 0) ? (sz - 1) : (position - 1);
                u = Uri.parse(mysongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                mp.start();
                sb.setMax(mp.getDuration());
                UpdateSeekBar.start();
            }
        }catch (Exception e){
            Toast.makeText(this, "Tap only once", Toast.LENGTH_SHORT).show();
        }

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
}
