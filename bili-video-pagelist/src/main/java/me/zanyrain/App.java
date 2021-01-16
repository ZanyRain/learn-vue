package me.zanyrain;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    private static final String VIDEO_BVID = "BV17j411f74d";
    private static final String API_PAGE_LIST = "https://api.bilibili.com/x/player/pagelist";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(HttpUrl.parse(API_PAGE_LIST).newBuilder()
                        .setQueryParameter("bvid", VIDEO_BVID)
                        .build())
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Content json = gson.fromJson(response.body().charStream(), Content.class);
        List<Content.Page> data = json.getData();
        int count = 0;
        for (int i = 0;i<data.size();i++) {
            String part = data.get(i).getPart();
            if (part.matches("^01-.+$")){
                System.out.printf("\n- [ ] #### 第 %d 天\n\n",++count);
                System.out.println("  | 页   | 内容                   | 进度 |");
                System.out.println("  | ---- | ---------------------- | ---- |");
            }
            System.out.printf("  | %3d  | %s |      |\n",i,part);
        }
    }
}
