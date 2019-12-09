package com.lei.tang.java8.utils;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author tanglei
 * @date 2019/12/9
 */
@Slf4j
public class HttpUtils {

    //http连接池
    private static volatile PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    //请求配置
    private static RequestConfig requestConfig;

    //请求超时时间
    private final static Integer TIME_OUT = 1000;

    public static PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
        if (poolingHttpClientConnectionManager == null) {
            synchronized (HttpUtils.class) {
                if (poolingHttpClientConnectionManager == null) {
                    poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
                    //连接池最大连接数
                    poolingHttpClientConnectionManager.setMaxTotal(1024);
                    //每个路由最大连接数
                    poolingHttpClientConnectionManager.setDefaultMaxPerRoute(32);

                    //配置请求的超时设置
                    requestConfig =
                            RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT).setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
                }
            }
        }
        return poolingHttpClientConnectionManager;
    }

    public static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(getPoolingHttpClientConnectionManager()).setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 请求发送执行
     *
     * @param httpMethd
     * @return
     */
    public static String registRequest(HttpUriRequest httpMethd) {
        CloseableHttpClient httpClient = getHttpClient();
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpMethd, HttpClientContext.create())) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.error("响应失败 statusCode {}", statusCode);
                httpMethd.abort();
            }
            log.debug("响应成功 statusCode {}", statusCode);
            String response = EntityUtils.toString(httpResponse.getEntity(), Consts.UTF_8);
            log.debug("响应成功 response {}", response);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("请求失败", e);
        }
    }

    public static List<BasicNameValuePair> toPairs(Map<String, Object> params) {
        List<BasicNameValuePair> pairs = Lists.newArrayList();
        if (params != null && !params.isEmpty()) {
            pairs = params.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(),
                    entry.getValue().toString())).collect(Collectors.toList());
        }
        return pairs;
    }

    /**
     * get url请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Map<String, Object> params) {
        HttpGet request = new HttpGet();
        try {
            String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(toPairs(params), Consts.UTF_8));
            request.setURI(new URI(url.concat("?").concat(paramsStr)));
            return registRequest(request);
        } catch (Exception e) {
            log.error("请求失败", e);
        }
        return null;
    }

    /**
     * PSOT URL方式提交
     *
     * @param url    请求url
     * @param params 请求参数
     * @return
     */
    public static String postFromUrl(String url, Map<String, Object> params) {
        HttpPost request = new HttpPost(url);
        request.setEntity(new UrlEncodedFormEntity(toPairs(params), Consts.UTF_8));
        try {
            return registRequest(request);
        } catch (Exception e) {
            log.error("请求失败", e);
        }
        return null;
    }

    /**
     * PSOT JSON方式提交
     *
     * @param url    请求url
     * @param params json串
     * @return
     */
    public static String postFromJson(String url, String params) {
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(params, ContentType.APPLICATION_JSON));
        try {
            return registRequest(request);
        } catch (Exception e) {
            log.error("请求失败", e);
        }
        return null;
    }
}
