package com.example.zahidmahmudemon.myapplication;

import android.media.MediaPlayer;

public class AudioPlay {
    public static MediaPlayer mediaPlayer=AudioPlayerActivity.mp;


    public  static void stopAudio(){

            mediaPlayer.stop();
    }
}
