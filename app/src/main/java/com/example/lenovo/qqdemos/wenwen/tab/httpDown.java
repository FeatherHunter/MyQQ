//package com.example.lenovo.qqdemos.wenwen.tab;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.ListView;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//
///**
// * Created by lenovo on 2016/7/30.
// */
//public class httpDown extends AsyncTask<String, Void, String> {
//
//    private URL url;
//    private URLConnection con;
//    private ListView listView;
//    private Context context;
//
//    public httpDown(ListView listView, Context context) {
//        this.listView = listView;
//        this.context = context;
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//
//        StringBuffer sb = new StringBuffer();
//        String line = null;
//        BufferedReader bufferedReader = null;
//
//        try {
////          url = new URL("http://192.168,1,100:8080/users/usersinfo/xml");
//            con = url.openConnection();
//            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            while ((line = bufferedReader.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bufferedReader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return sb.toString();
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//        Log.d("main", "" + s);
//
//        try {
//
//            Gson gson = new Gson();
//        }catch (Exception e){
//
//        }
//
//    }
//}
