package com.july.pigeon.eventbus;


import com.july.pigeon.bean.Empty;
import com.july.pigeon.util.StringUtils;

public class EventUtils {

    public static boolean isValid(EventByTag eventByTag, String tagConfig, EmptyEventCallback emptyEventCallback) {
        if (StringUtils.isEquals(eventByTag.getTAG(), tagConfig)) {
            if ((eventByTag.getObj() instanceof Empty)) {
                // TODO
                if (null == emptyEventCallback) {
                    System.out.println("mabi!");
                } else {
                    emptyEventCallback.onEmpty();
                }

                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public static interface EmptyEventCallback {

        /**
         * 返回为Empty的时候自定义处理！
         */
        public void onEmpty();
    }

}
