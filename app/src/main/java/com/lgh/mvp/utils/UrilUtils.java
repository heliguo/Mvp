package com.lgh.mvp.utils;

public class UrilUtils {

    public static String createHomePagerUrl(int materialId, int page) {
        return "discovery/" + materialId +"/"+ page;
    }

}
