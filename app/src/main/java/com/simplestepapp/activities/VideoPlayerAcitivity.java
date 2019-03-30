package com.simplestepapp.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.simplestepapp.R;
import com.simplestepapp.utils.Toaster;

import uk.co.jakelee.vidsta.VidstaPlayer;
import uk.co.jakelee.vidsta.listeners.VideoStateListeners;

public class VideoPlayerAcitivity extends AppCompatActivity {
    VidstaPlayer vista_Player;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        vista_Player=findViewById(R.id.vista_Player);
        vista_Player.setVideoSource("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+1.mp4");
        vista_Player.setOnVideoFinishedListener(new VideoStateListeners.OnVideoFinishedListener() {
            @Override
            public void OnVideoFinished(VidstaPlayer evp) {
                evp.stop();
                Toaster.showInfoMessage("gshdgf");
            }
        });
        vista_Player.setAutoLoop(true);
        vista_Player.setAutoPlay(true);
    }
}
