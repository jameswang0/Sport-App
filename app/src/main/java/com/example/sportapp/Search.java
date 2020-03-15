package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search extends AppCompatActivity {

    // 1 -> Normal search   2 -> Suggestion     3 -> Google Speech  4 -> TWN Speech
    private String which_btn;

    private Button search_btn, suggest_btn, speech_btn, twn_btn, return_btn;
    private String name, age, injury_history;
    private EditText search;
    private boolean busy;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setInit();
        permissionCheck();
        textResult.setText("辨識結果");

        setListener1(); //Normal Search
        setListener2(); //Suggestion Search
        setListener3(); //Google Speech Search
        setListener4(); //Taiwanese Speech Search
        setListener5(); //Set Return to MainActivity

        speech_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpeechInput(view);
            }
        });

        twn_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if(!busy && checkNetwork())
                {
                    busy = true;
                    textResult.setText("正在辨識...");
                    startTaiwaneseRecognition();
                }
            }
        });

    }

    private void setInit()
    {
        busy = false;
        textResult = findViewById(R.id.textView);
        suggest_btn = findViewById(R.id.suggest_btn);
        search_btn = findViewById(R.id.search_button);
        speech_btn = findViewById(R.id.speech_button);
        twn_btn = findViewById(R.id.Twn_btn);
        return_btn = findViewById(R.id.return_btn);
        search = findViewById(R.id.search);
        getVar();
    }

    private void setListener1(){
        search_btn.setOnClickListener(bEvent1);
    }

    private View.OnClickListener bEvent1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try
            {
                which_btn = "1";
                Intent intent = new Intent();
                intent.setClass(Search.this, Result.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("age", age);
                bundle.putString("injury_history", injury_history);
                bundle.putString("which_btn", which_btn);
                bundle.putString("text_search", search.getText().toString());
                intent.putExtras(bundle);
                Log.d("Response", bundle.toString());
                startActivity(intent);

            }
            catch (Exception e)
            {
                Toast.makeText(Search.this, "please enter completly1", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setListener2(){
        suggest_btn.setOnClickListener(bEvent2);
    }

    private View.OnClickListener bEvent2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try
            {
                which_btn = "2";
                Intent intent = new Intent();
                intent.setClass(Search.this, Result.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("age", age);
                bundle.putString("injury_history", injury_history);
                bundle.putString("which_btn", which_btn);
                //bundle.putString("text_search", search.getText().toString());
                intent.putExtras(bundle);
                Log.d("Response", bundle.toString());
                startActivity(intent);

            }
            catch (Exception e)
            {
                Toast.makeText(Search.this, "please enter completly1", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setListener3(){
        speech_btn.setOnClickListener(bEvent3);
    }

    private View.OnClickListener bEvent3 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try
            {
                which_btn = "3";
                Intent intent = new Intent();
                intent.setClass(Search.this, Result.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("age", age);
                bundle.putString("injury_history", injury_history);
                bundle.putString("which_btn", which_btn);
                bundle.putString("text_search", search.getText().toString());
                intent.putExtras(bundle);
                Log.d("Response", bundle.toString());
                startActivity(intent);

            }
            catch (Exception e)
            {
                Toast.makeText(Search.this, "please enter completly1", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setListener4(){
        twn_btn.setOnClickListener(bEvent4);
    }

    private View.OnClickListener bEvent4 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try
            {
                which_btn = "4";
                Intent intent = new Intent();
                intent.setClass(Search.this, Result.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("age", age);
                bundle.putString("injury_history", injury_history);
                bundle.putString("which_btn", which_btn);
                bundle.putString("text_search", search.getText().toString());
                intent.putExtras(bundle);
                Log.d("Response", bundle.toString());
                startActivity(intent);

            }
            catch (Exception e)
            {
                Toast.makeText(Search.this, "please enter completly1", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setListener5() {
        return_btn.setOnClickListener(bEvent5);
    }

    private View.OnClickListener bEvent5 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try
            {
                Intent intent = new Intent();
                intent.setClass(Search.this, MainActivity.class);
                startActivity(intent);

            }
            catch (Exception e)
            {
                Toast.makeText(Search.this, "please enter completly1", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void getVar() {
        Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("nameValue");
        age = bundle.getString("ageValue");
        injury_history = bundle.getString("hurtValue");
        Toast.makeText(Search.this, injury_history, Toast.LENGTH_SHORT).show();
    }

    private void permissionCheck() {
        int permission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if(permission1 != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Search.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);

        }
    }

    private boolean checkNetwork() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        assert mConnectivityManager != null;
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo != null && mNetworkInfo.isConnected())
        {
            return true;
        }
        else
        {
            Toast.makeText(this,"無網路連線", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private File recordFile;
    private MediaRecorder mediaRecorder = new MediaRecorder();
    private void startTaiwaneseRecognition()
    {
        try
        {
            recordFile = File.createTempFile("record_temp", ".m4a", getCacheDir());
            mediaRecorder.setOutputFile(recordFile.getAbsolutePath());
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setAudioEncodingBitRate(326000);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setAudioChannels(1);
            mediaRecorder.prepare();
            mediaRecorder.start();
        }
        catch (IOException e)
        {
            pushResult(e.getMessage(), false);
            e.printStackTrace();
        }

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_recording);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setOnCancelListener(new Dialog.OnCancelListener(){
            @Override
            public void onCancel(DialogInterface dialog)
            {
                endTaiwaneseRecognition();
            }
        });

        ImageButton btnComplete = dialog.findViewById(R.id.btn_robot);
        btnComplete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
            }
        });

        TextView textview = dialog.findViewById(R.id.text_dialogHint);
        textview.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
            }
        });
        dialog.show();

    }

    private void endTaiwaneseRecognition()
    {
        mediaRecorder.stop();
        new TaiwaneseSender().execute(recordFile.getAbsolutePath(), "main");
    }

    private void pushResult(String msg, boolean success)
    {
        if(success) {
            boolean found=false;
            Pattern pattern = Pattern.compile("1.*2");
            Matcher matcher = pattern.matcher(msg);
            while (matcher.find()) {
                Log.d("Response", "I found the text "+matcher.group()+" starting at index "+
                        matcher.start()+" and ending at index "+matcher.end());
                found = true;
            }
            if(!found) {
                Log.d("Response", "not found");
            }

            textResult.setText(msg);
            new Tai2Chi().execute(msg);
        }
        textResult.setText(msg);
        search.setText(msg);
        busy = false;
    }

    // 台語語音辨識
    @SuppressLint("StaticFieldLeak")
    public class TaiwaneseSender extends AsyncTask<String, Void, Boolean> {
        /*
         * param[0]:  path of sound file
         * param[1]: the target model
         * */
        // 伺服器核發之安全性token
        private static final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiJ9.eyJpZCI6NzgsInVzZXJfaWQiOiIwIiwic2VydmljZV9pZCI6IjMiLCJzY29wZXMiOiI5OTk5OTk5OTkiLCJzdWIiOiIiLCJpYXQiOjE1NDEwNjUwNzEsIm5iZiI6MTU0MTA2NTA3MSwiZXhwIjoxNjk4NzQ1MDcxLCJpc3MiOiJKV1QiLCJhdWQiOiJ3bW1rcy5jc2llLmVkdS50dyIsInZlciI6MC4xfQ.K4bNyZ0vlT8lpU4Vm9YhvDbjrfu_xuPx8ygoKsmovRxCCUbj4OBX4PzYLZxeyVF-Bvdi2-wphGVEjz8PsU6YGRSh5SDUoHjjukFesUr8itMmGfZr4BsmEf9bheDm65zzbmbk7EBA9pn1TRimRmNG3XsfuDZvceg6_k6vMWfhQBA";

        // 伺服器資訊
        private static final String host = "140.116.245.149";
        private static final int port = 2802;
        private static final String TAG = "TaiwaneseSender";

        // result message
        private String message;

        @Override
        protected Boolean doInBackground(String... param) {
            String model = param[1];
            String padding = new String(new char[8 - model.length()])
                    .replace("\0", "\u0000");
            String label = "A";
            String header = token + "@@@" + model + padding + label;
            try {
                byte[] b_header = header.getBytes();
                byte[] b_sample = readAsByteArray(param[0]);

                int len = b_header.length + b_sample.length;
                byte[] b_len = new byte[4];
                b_len[0] = (byte) ((len & 0xff000000) >>> 24);
                b_len[1] = (byte) ((len & 0x00ff0000) >>> 16);
                b_len[2] = (byte) ((len & 0x0000ff00) >>> 8);
                b_len[3] = (byte) ((len & 0x000000ff));

                ByteArrayOutputStream arrayOutput = new ByteArrayOutputStream();
                arrayOutput.write(b_len);
                arrayOutput.write(b_header);
                arrayOutput.write(b_sample);

                Socket socket = new Socket();
                InetSocketAddress socketAddress = new InetSocketAddress(host, port);
                socket.connect(socketAddress, 10000);

                // 將訊息傳至server
                BufferedOutputStream sout = new BufferedOutputStream(socket.getOutputStream());
                sout.write(arrayOutput.toByteArray());
                sout.flush();

                // 從server接收訊息
                arrayOutput = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                BufferedInputStream sin = new BufferedInputStream(socket.getInputStream());
                int n;
                while (true) {
                    n = sin.read(buf);
                    if (n < 0) break;
                    arrayOutput.write(buf, 0, n);
                }

                sout.close();
                sin.close();
                socket.close();

                message = new String(arrayOutput.toByteArray(), Charset.forName("UTF-8"));

                return true;
            } catch (IOException e) {
                message = e.getMessage();
                Log.e(TAG, "doInBackground: ", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            Log.i(TAG, "onPostExecute: message: " + message);

            if (success) {
                Matcher match = Pattern.compile("ori:(.*)result:(.*)").matcher(message);
                if (match.find()) {
                    if (match.group(2).contains("same with ori")) {
                        // `result` same as `ori`
                        pushResult(match.group(1)
                                        .replace(" ", "")
                                        .replace("\n", "")
                                        .replace("�", "")
                                , true);
                    } else {
                        pushResult(match.group(2)
                                        .replace(" ", "")
                                        .replace("\n", "")
                                        .replace("�", "")
                                , true);
                    }
                } else {
                    // match failed
                    pushResult("辨識失敗", false);
                }
            } else {
                // print error message send by server
                pushResult(message, false);
            }
        }

        private byte[] readAsByteArray(String path) throws IOException {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];

            for (int readNum; (readNum = fis.read(b)) != -1; ) {
                bos.write(b, 0, readNum);
            }

            return bos.toByteArray();
        }


    }
    class Tai2Chi extends AsyncTask<String, Void, String> {
        //Debug用的
        private static final String TAG = "Taiwanese2Chinese";
        //這邊請填入自己申請的token
        private static final String token = "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ3bW1rcy5jc2llLmVkdS50dyIsInNlcnZpY2VfaWQiOiIxMCIsIm5iZiI6MTU3NjU1MzgzOSwic2NvcGVzIjoiMCIsInVzZXJfaWQiOiI5OSIsImlzcyI6IkpXVCIsInZlciI6MC4xLCJpYXQiOjE1NzY1NTM4MzksInN1YiI6IiIsImlkIjoyMTUsImV4cCI6MTYzOTYyNTgzOX0.LqaPgOInEXTmQZCxGWG7S6p4n3EEB_Xz40rbD8gOxxQzJdu2a0AqD1Bt6LRWQAJYGh9Rd2ucZK-p4g1E_LTjlWHZveolKNuBXfAOLD9rjOPuQvMQyFX9E0sZQf3PC2zPLu52i8gHsMkuFW01HSwcVEvgtHlusojyFI5m2vzBPlc";
        //存回傳結果
        private String result = null;

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, strings[0]);

            //要送給SERVER的訊息
            String outmsg = token + "@@@" + strings[0];

            //用socket的方式傳
            Socket socket = new Socket();
            InetSocketAddress isa = new InetSocketAddress("140.116.245.149", 27002);

            try {
                //將outmsg轉成byte[]
                byte[] token_et_s = outmsg.getBytes();
                //用於計算outmsg的byte數
                byte[] g = new byte[4];

                g[0] = (byte) ((token_et_s.length & 0xff000000) >>> 24);
                g[1] = (byte) ((token_et_s.length & 0x00ff0000) >>> 16);
                g[2] = (byte) ((token_et_s.length & 0x0000ff00) >>> 8);
                g[3] = (byte) ((token_et_s.length & 0x000000ff));

                socket.connect(isa, 10000);

                BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                // 送出字串
                out.write(byteconcate(g, token_et_s));
                out.flush();

                // 接收字串
                BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                byte[] b = new byte[1024];
                while (in.read(b) > 0)// <=0的話就是結束了
                    result = new String(b, Charset.forName("UTF-8"));
                out.close();
                in.close();
                socket.close();
                return result;
            } catch (IOException ex) {
                Log.e(TAG, "doInBackground: request failed", ex);
                return ex.getMessage();
            } catch (NullPointerException ex) {
                Log.e(TAG, "doInBackground: received empty response", ex);
                return ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Log.d(TAG, Integer.toString(s.length()));
                Log.d(TAG, s);
                String[] convert = s.split("\u0000");   //切除亂碼
                Log.d(TAG, convert[0]);
                textResult.setText(convert[0]);
                return;
            }
        }

        private byte[] byteconcate(byte[] a, byte[] b) {
            byte[] result = new byte[a.length + b.length];
            System.arraycopy(a, 0, result, 0, a.length);
            System.arraycopy(b, 0, result, a.length, b.length);
            return result;
        }
    }

    //Chinese and English speech Recognition
    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device don't support speech Recognition", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int resuestCode, int resultCode, Intent data) {
        super.onActivityResult(resuestCode, resultCode, data);

        switch (resuestCode) {
            case 10:
                if(resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //textResult.setText(result.get(0));
                    String resultString = result.get(0);
                    search.setText(resultString);
                }
                break;
        }
    }
}
