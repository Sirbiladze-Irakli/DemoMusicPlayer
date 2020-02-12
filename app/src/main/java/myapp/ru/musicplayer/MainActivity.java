package myapp.ru.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int index;
    int flag;
    MediaPlayer mediaPlayer;
    SeekBar bar;
    ImageView play;
    ImageView shadow;
    ImageView replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        shadow = findViewById(R.id.shadow1);
        replay = findViewById(R.id.replay);
        TextView textView = findViewById(R.id.textView);
        index = 0;
        flag = 0;
        mediaPlayer = MediaPlayer.create(this, R.raw.dynamite);
        textView.setText("Dynamite");

        bar = findViewById(R.id.seekBar);
        bar.setMax(mediaPlayer.getDuration());
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                bar.setProgress(mediaPlayer.getCurrentPosition());
                if (bar.getProgress() >= bar.getMax() - 200) {
                    mediaPlayer.seekTo(0);
                    if (flag == 1) {
                        mediaPlayer.start();
                    } else {
                        play(play);
                    }
                }
            }
        }, 0, 100);
    }

    public void play(View view) {
        if (!play.getTag().equals("pl")) {
            play.setTag("pl");
            play.setImageResource(R.drawable.pause);
            shadow.animate().alpha(0.7f).scaleX(1.0f).scaleY(1.0f).setDuration(150);
            mediaPlayer.start();
        } else {
            play.setTag("ps");
            play.setImageResource(R.drawable.play);
            shadow.animate().alpha(0.7f).scaleX(1.0f).scaleY(1.0f).setDuration(150);
            mediaPlayer.pause();
        }
    }

    public void forwardskip(View view) {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
    }

    public void prevskip(View view) {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
    }

    public void previous(View view) {
        mediaPlayer.seekTo(0);
    }

    public void next(View view) {
        mediaPlayer.seekTo(0);
    }

    public void replay(View view) {
        if (flag == 0) {
            flag = 1;
            replay.setImageResource(R.drawable.replayon);
        } else if (flag == 1) {
            flag = 0;
            replay.setImageResource(R.drawable.replayoff);
        }
    }
}
