package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Result extends AppCompatActivity {

    private Button btnReturn;

    private CustomListAdapter listAdapter;
    private ArrayList<String> totalVideoImageLinks = new ArrayList<String>();
    private ArrayList<String> totalVideoTitles = new ArrayList<String>();
    private ArrayList<String> totalVideoIDs = new ArrayList<String>();
    private ArrayList<VideoEntry> myVideoList = new ArrayList<VideoEntry>();
    private String age;
    private String injury_his;
    private String q_string;
    private String which_btn;
    private String text_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getPara();
        SetInit();
        setListener1();
    }

    private void getPara(){
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        age = bundle.getString("age");
        injury_his = bundle.getString("injury_history");
        which_btn = bundle.getString("which_btn");
        Toast.makeText(Result.this, which_btn, Toast.LENGTH_SHORT).show();
        switch (which_btn)
        {
            case("1"):  //injury history
                q_string = injury_his + "運動";
                break;
            case("2"):  //normal search
                text_search = bundle.getString("text_search");
                q_string = text_search;
                break;
            case("3"):  //speech recognition
                q_string = injury_his;
                break;
        }
    }

    private void SetInit() {

        this.myVideoList.addAll(GetTotalResultInfo());
        this.totalVideoImageLinks = this.GetVideoImageLinks();
        this.totalVideoTitles = this.GetVideoTitles();
        this.totalVideoIDs = this.GetVideoIDs();

        btnReturn = findViewById(R.id.btn_return_result);

        ListView videoList = findViewById(R.id.result_sheet_list_view);
        videoList.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int indexOfClickItem = parent.getPositionForView(view);
                VideoEntry video = myVideoList.get(indexOfClickItem);

                String videoId = video.GetVideoID();
                Log.d("Response", videoId);

                try
                {
                    Intent intent = new Intent();
                    intent.setClass(Result.this, YouTube.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("videoID", videoId);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(Result.this, "please enter completly1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.listAdapter = new CustomListAdapter(this, this.totalVideoTitles, this.totalVideoImageLinks, this.totalVideoIDs);
        videoList.setAdapter(this.listAdapter);
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
                intent.setClass(Result.this, Search.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);

            }
            catch (Exception e)
            {
                Toast.makeText(Result.this, "please enter completly1", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private  ArrayList<String> GetVideoImageLinks() {
        ArrayList<String> totalListViewData = new ArrayList<String>();

        for (int index = 0; index < this.myVideoList.size(); index++) {
            VideoEntry video = this.myVideoList.get(index);
            String perListViewData = video.GetImageUrl();

            totalListViewData.add(perListViewData);
        }
        return totalListViewData;
    }

    private  ArrayList<String> GetVideoTitles() {
        ArrayList<String> totalListViewData = new ArrayList<String>();

        for (int index = 0; index < this.myVideoList.size(); index++) {
            VideoEntry video = this.myVideoList.get(index);
            String perListViewData = video.GetVideoTitle();

            totalListViewData.add(perListViewData);
        }
        return totalListViewData;
    }

    private  ArrayList<String> GetVideoIDs() {
        ArrayList<String> totalListViewData = new ArrayList<String>();

        for (int index = 0; index < this.myVideoList.size(); index++) {
            VideoEntry video = this.myVideoList.get(index);
            String perListViewData = video.GetVideoID();

            totalListViewData.add(perListViewData);
        }
        return totalListViewData;
    }

    public ArrayList<VideoEntry> GetTotalResultInfo() {
        ArrayList<VideoEntry> totalResultInfo = new ArrayList<VideoEntry>();

        //Some url endpoint that you may have
        String myUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q=" + q_string + "&key=" + getResources().getString(R.string.youtube_api) +"\n";
        //String to place our result in
        String result;
        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();
        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
            Log.d("Response", result);
            JSONObject reader = new JSONObject(result);

            JSONArray items = reader.getJSONArray("items");
            Log.d("Response", items.toString());
            for (int i = 0; i < items.length(); i++) {
                JSONObject row = items.getJSONObject(i);
                try {
                    String video_title = row.getJSONObject("snippet").getString("title");
                    String video_id = row.getJSONObject("id").getString("videoId");
                    JSONObject snippet = row.getJSONObject("snippet");
                    String image_url = snippet.getJSONObject("thumbnails").getJSONObject("default").getString("url");
                    Log.d("Response", "index = " + i);
                    Log.d("Response", video_id);
                    Log.d("Response", video_title);
                    Log.d("Response", image_url);

                    VideoEntry perVideoEntry = new VideoEntry();
                    perVideoEntry.init(video_id, video_title, image_url);
                    totalResultInfo.add(perVideoEntry);
                } catch (Exception e) {
                    continue;
                }
            }
        }
        catch(Exception e) {
            Log.d("Response", "failed");
        }

        return totalResultInfo;
    }

    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }
    }
}
