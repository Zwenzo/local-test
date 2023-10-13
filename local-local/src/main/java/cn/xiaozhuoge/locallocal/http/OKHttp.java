package cn.xiaozhuoge.locallocal.http;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaowz
 * @date 2023/6/6 17:37
 */
public class OKHttp {

    private static final String url = "http://127.0.0.1:8899/test";


    public static void main(String[] args) throws IOException {
        //构建http客户端
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS).build();

        //构建post请求参数
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                "{\"exchangeRate\":null,\"localAmount\":null,\"bizExtProject\":null,\"bizExtBudgetCenter\":null,\"auxCustomerProfile\":\"交易对手名称\",\"auxBankAccount\":null,\"auxCostCategories\":null,\"auxEmployee\":null}");

        //构建request请求参数如url、参数、请求方式、请求头、缓存等等
        Request request = new Request.Builder().url(url)
                .post(body)
                .cacheControl(CacheControl.parse(Headers.of(getCacheControl())))
                .addHeader("testHeader", "testHeader")
                .build();

        //构建请求可回调Call，只能执行一次
        Call call = client.newCall(request);
        Response execute = call.execute();
        //enqueue可以在在拿到响应时做处理，也可以在失败的时候做处理
        //execute没有回调方法，直接拿到响应或错误
        //Response execute = call.execute();
        call.enqueue(new Callback() {
            @SneakyThrows
            @Override
            public void onFailure(Call call, IOException e) {
                //失败回调
                System.out.println(call.request().headers());
                System.out.println("url为：" + call.request().url());
                System.out.println("tag为" + call.request().tag());
                throw new Exception(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //子线程回调
                System.out.println("回调数据" + response.body().string());
            }
        });
    }

    private static Map<String, String> getCacheControl() {
        Map<String, String> map = Maps.newHashMap();
        map.put("Cache-Control", "public");
        return map;
    }
}