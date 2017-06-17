package com.july.pigeon.engine;

/**
 * Created by ANDROID on 2017/6/7.
 */

public class RequestParam {
    /**
     * 收藏帖子
     *
     * @param tid
     * @param memberId
     * @return
     */
    public static String collectTheme(String tid, String memberId) {

        StringBuffer sb = new StringBuffer();
        sb.append("?");
        sb.append("tid=" + tid);
        sb.append("&memberId=" + memberId);
        return sb.toString();

    }

}
