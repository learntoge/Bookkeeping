

package cn.shuzilearn.bookkeeping.utils;

import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.shuzilearn.bookkeeping.pojo.yiyan;

public class NetworkRequestUtil {


    public interface Callback<T> {
        void onResult(T result);
    }

    public static void getYIYANAsync(Callback<yiyan> callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            HttpURLConnection con = null;
            try {
                URI YIYANURL = new URI("https://v1.hitokoto.cn/");
                con = (HttpURLConnection) YIYANURL.toURL().openConnection();
                con.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                Log.d("NetworkRequestUnit", "Response: " + content);

                JSONObject jsonObject = new JSONObject(content.toString());
                int id = jsonObject.getInt("id");
                String hitokoto = jsonObject.getString("hitokoto");
                String s = jsonObject.optString("from");
                String s1 = jsonObject.optString("from_who", "未知");
                Log.d("NetworkRequestUnit", hitokoto);
                yiyan yy = new yiyan(id,hitokoto, s.equals("null") ?"未知":s,s1.equals("null") ?"未知":s1);
                callback.onResult(yy);
            } catch (Exception e) {
                Log.e("NetworkRequestUnit", "Exception occurred: ", e);
                callback.onResult(new yiyan(9999, "带着你的梦想，一起奔跑！", "开发者", "huang"));
            } finally {
                if (con != null) con.disconnect();
            }
        });
    }


}



//package cn.shuzilearn.bookkeeping.unit;
//
//import android.util.Log;
//import cn.shuzilearn.bookkeeping.pojo.yiyan;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.net.URISyntaxException;
//
//
//public class NetworkRequestUnit {
//
//    public static yiyan getYIYAN() {
//        HttpURLConnection con;
//        URI YIYANURL = null;
//        try {
//            YIYANURL = new URI("https://v1.hitokoto.cn/");
//            con = (HttpURLConnection) YIYANURL.toURL().openConnection();
//            con.setRequestMethod("GET");
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuffer content = new StringBuffer();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
//            con.disconnect();
//            JSONObject jsonObject = new JSONObject(content.toString());
//            yiyan yy =  new yiyan(jsonObject.getInt("id"),
//                    jsonObject.getString("hitokoto"),
//                    jsonObject.getString("from"),
//                    jsonObject.getString("from_who")
//            );
//
//            Log.d("yy",yy.toString());
//            return yy;
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new yiyan(9999,"带着你的梦想，一起奔跑！","开发者","huang");
//    }
//}