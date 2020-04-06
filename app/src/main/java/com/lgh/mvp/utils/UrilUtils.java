package com.lgh.mvp.utils;

public class UrilUtils {

    public static String createHomePagerUrl(int materialId, int page) {
        return "discovery/" + materialId + "/" + page;
    }

    public static String getCoverPath(String pict_url) {
        return "https:" + pict_url;
    }

    public static String getCoverPath(String pict_url, int size) {
        if (size < 50)
            return getCoverPath(pict_url);
        int g = size % 10;
        int s = (size / 10) % 10;
        int c = 5 >= s ? 5 : 10;
        int finalSize = size - s * 10 - g + c * 10;
        return "https:" + pict_url + "_" + finalSize + "x" + finalSize + ".jpg";
    }

    public static String getTicketUrl(String url) {
        if (url.contains("http")||url.contains("https")){
            return url;
        }else {
            return "http"+url;
        }
    }
}
