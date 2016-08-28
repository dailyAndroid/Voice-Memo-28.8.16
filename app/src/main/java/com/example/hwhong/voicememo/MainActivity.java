package com.example.hwhong.voicememo;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaRecorder myAudioRecorder;

    private Button record, stop, play;
    private ListView listView;

    private String outputFile;

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        record = (Button) findViewById(R.id.record);
        stop = (Button) findViewById(R.id.stop);
        play = (Button) findViewById(R.id.play);

        record.setOnClickListener(this);
        stop.setOnClickListener(this);
        play.setOnClickListener(this);

        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.record:

                myAudioRecorder = new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setOutputFile(outputFile);
                myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                try {
                    myAudioRecorder.prepare();
                } catch (IOException e) {
                    Log.e("record", "prepare() failed");
                }

                myAudioRecorder.start();

                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();

                break;
            case R.id.stop:

                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder  = null;

                stop.setEnabled(false);
                play.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_SHORT).show();

                break;
            case R.id.play:

                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_SHORT).show();

                break;

        }

    }
}
