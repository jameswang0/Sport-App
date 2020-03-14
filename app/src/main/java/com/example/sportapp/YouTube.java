package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTube extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private String videoID;
    private String apiKey;
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

        Bundle bundle = this.getIntent().getExtras();
        videoID = bundle.getString("videoID");
        Log.d("Response", videoID); //change to playlist ID

        btnReturn = findViewById(R.id.btn_return_youtube);
        setListener1();


        apiKey = getResources().getString(R.string.youtube_api);
        Log.d("Response", apiKey);
        youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(apiKey, this);
    }

    private void setListener1(){
        btnReturn.setOnClickListener(bEvent1);
    }

    private View.OnClickListener bEvent1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try
            {
                Intent intent = new Intent();
                intent.setClass(YouTube.this, Search.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);

            }
            catch (Exception e)
            {
                Toast.makeText(YouTube.this, "please enter completly1", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(videoID); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = "error";
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(apiKey, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
