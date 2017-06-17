package com.july.pigeon.eventbus;

import java.util.HashMap;
import java.util.Map;

public class EventByTag {

    private static String TAG;

    private static Object obj;

    private static Map<String, Object> eventMap = new HashMap<String, Object>();

    private static EventByTag instance;

    private EventByTag(Object obj, String TAG) {
        EventByTag.TAG = TAG;
        EventByTag.obj = obj;
        eventMap.put(TAG, obj);
    }

    public static EventByTag setDefault(Object obj, String TAG) {

        synchronized (EventByTag.class) {
            if (instance == null) {
                instance = new EventByTag(obj, TAG);
            } else {
                EventByTag.TAG = TAG;
                EventByTag.obj = obj;
                if (eventMap.containsKey(TAG)) {
                    eventMap.remove(TAG);
                }
                eventMap.put(TAG, obj);
            }
        }

        return instance;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public Object getObject() {
        return obj;
    }

    public Object getObjectByTAG(String TAG) {
        return eventMap.get(TAG);
    }

    public Object removeObjectByTAG(String TAG) {
        return eventMap.remove(TAG);
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
