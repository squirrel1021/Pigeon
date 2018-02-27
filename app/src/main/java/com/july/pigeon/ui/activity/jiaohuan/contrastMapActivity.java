package com.july.pigeon.ui.activity.jiaohuan;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.reflect.TypeToken;
import com.july.pigeon.R;
import com.july.pigeon.bean.JHSturts;
import com.july.pigeon.bean.Jiaohuan;
import com.july.pigeon.bean.LaLoBean;
import com.july.pigeon.bean.Pigeon;
import com.july.pigeon.engine.BaseResponse;
import com.july.pigeon.engine.ConstantValues;
import com.july.pigeon.engine.GsonParser;
import com.july.pigeon.engine.RequestParam;
import com.july.pigeon.engine.RequestUtil;
import com.july.pigeon.engine.task.PigeonTask;
import com.july.pigeon.eventbus.EventByTag;
import com.july.pigeon.eventbus.EventTagConfig;
import com.july.pigeon.eventbus.EventUtils;
import com.july.pigeon.ui.activity.BaseActivity;
import com.july.pigeon.ui.activity.main.MapControlDemo;
import com.july.pigeon.util.ListUtils;
import com.july.pigeon.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/12/3 0003.
 */

public class contrastMapActivity extends BaseActivity implements SensorEventListener {


    /**
     * MapView 是地图主控件
     */
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    /**
     * 当前地点击点
     */
    private LatLng currentPt;
    private LatLng firstPt;
    private TextView switchMap;
    /**
     * 控制按钮
     */
    private Button zoomButton;
    private Button rotateButton;
    private Button overlookButton;
//	private Button saveScreenButton;

    private String touchType;
    private int count = 0;
    private boolean first=true;
    private List<indexBean> lastIndex=new ArrayList<indexBean>();
    private MarkerOptions firstoption;

    /**
     * 用于显示地图状态的面板
     */

    //	private Button animateStatus;


    // 定位相关
    LocationClient mLocClient;
    //    public contrastMapActivity.MyLocationListenner myListener = new contrastMapActivity.MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;


    // UI相关
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    Button requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;
    private int showType = 0;
    private Handler mHandler;
    private Runnable runnable;
    private List<List<LaLoBean>> totalList = new ArrayList<List<LaLoBean>>();
    private boolean isStart = true;
    private List<Lalo> lastLa = new ArrayList<Lalo>();
    private static double[] lo = {113.889203, 113.890784, 113.897683, 113.90336, 113.903288};
    private static double[] la = {22.567348, 22.564277, 22.564878, 22.564077, 22.560072};
    int i = 0;
    private String ringId = "";
    private TextView ringCodeA, fensuA, hightA, laloA, fensuB, hightB, laloB, startStop;
    private int[] niao = {R.drawable.niao1, R.drawable.niao2, R.drawable.niao3, R.drawable.niao4, R.drawable.niao5, R.drawable.niao6, R.drawable.niao7
            , R.drawable.niao8, R.drawable.niao9, R.drawable.niao10, R.drawable.niao11, R.drawable.niao12, R.drawable.niao13, R.drawable.niao14, R.drawable.niao15
            , R.drawable.niao16, R.drawable.niao17, R.drawable.niao18, R.drawable.niao19, R.drawable.niao20};

    private List<Integer> r = new ArrayList<Integer>();
    private List<Integer> g = new ArrayList<Integer>();
    private List<Integer> b = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contrast_map_view);
        ringId = getIntent().getStringExtra("code");
        ringCodeA = (TextView) findViewById(R.id.ringCodeA);
        ringCodeA.setText("环号:" + getIntent().getStringExtra("ringCode"));
        fensuA = (TextView) findViewById(R.id.fensuA);
        hightA = (TextView) findViewById(R.id.hightA);
        laloA = (TextView) findViewById(R.id.laloA);
        fensuB = (TextView) findViewById(R.id.fensuB);
        hightB = (TextView) findViewById(R.id.hightB);
        laloB = (TextView) findViewById(R.id.laloB);
        startStop = (TextView) findViewById(R.id.startStop);
//        getLastPointList();
        String ringArray[] = ringId.split(",");
//        RequestUtil.postRequest(this, ConstantValues.getStatus, RequestParam.getStatus(ringArray[0]), new BaseResponse(this, "加载中") {
//            @Override
//            public void onFailure(String message) {
//                Log.i("messagesdfds", message);
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                Log.i("resultdsf", result);
//                if (!StringUtils.isEmpty(result)&&!result.equals("{}")) {
//
//                    List<JHSturts> list = new GsonParser().parseList(result, new TypeToken<List<JHSturts>>() {
//                    });
//                    if (list.get(0).isStatus()) {
//                        startStop.setText("停止对比");
//                        isStart = true;
//                        getLastPointList();
//                    } else {
//                        startStop.setText("开始对比");
//                        isStart = false;
//                    }
//                }
//            }
//        });
        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isStart) {
                    String ringArray[] = ringId.split(",");
                    for (int i = 0; i < ringArray.length; i++) {
                        new PigeonTask().end(contrastMapActivity.this, ringArray[i]);
                    }
                    mHandler.removeCallbacks(runnable);
                    startStop.setText("开始对比");
                    isStart = false;
                } else {
                    String ringArray[] = ringId.split(",");
                    for (int i = 0; i < ringArray.length; i++) {
                        new PigeonTask().start(contrastMapActivity.this, ringArray[i]);
                    }
                    startDrawPoint();
                    startStop.setText("停止对比");
                    isStart = true;
                }
            }
        });

        for (int i = 0; i < 20; i++) {
            List<LaLoBean> laloList = new ArrayList<LaLoBean>();
            totalList.add(laloList);
            Random random = new Random();
            r.add(random.nextInt(256));
            g.add(random.nextInt(256));
            b.add(random.nextInt(256));
        }

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        initListener();
        switchMap = (TextView) findViewById(R.id.switchMap);
        switchMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showType == 0) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    showType = 1;
                } else {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    showType = 0;
                }
            }
        });

        startDrawPoint();
    }

    //获取之前的历史轨迹
    private void getLastPointList() {
        new PigeonTask().getLastPointList(this, ringId);

    }

    private void startDrawPoint() {
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                new PigeonTask().getLastPointList(contrastMapActivity.this, ringId);
//                new PigeonTask().getLastPoint(contrastMapActivity.this, ringId);
                mHandler.postDelayed(this, 3000 * 10);
            }
        };
        mHandler.post(runnable);
    }

    public boolean isdengyu(double a, double b) {
        if (Double.doubleToLongBits(a) == Double.doubleToLongBits(b)) {
            return true;
        }
        return false;
    }

    // 接口回调
    public void onEventMainThread(EventByTag eventByTag) {
        //获取实时脚环上一个坐标
        if (EventUtils.isValid(eventByTag, EventTagConfig.getLastUpData, null)) {
            try {
                String trajectory = new JSONObject(eventByTag.getObj() + "").getString("trajectorys");
//                LaLoBean lalo = new GsonParser().parseObject(trajectory, LaLoBean.class);
                List<LaLoBean> resultList = new GsonParser().parseList(trajectory, new TypeToken<List<LaLoBean>>() {
                });

                if (!ListUtils.isEmpty(resultList)) {
                    for (int i = 0; i < resultList.size(); i++) {
                        totalList.get(i).add(resultList.get(i));
                        if (i == 0) {
                            hightA.setText("高度:" + resultList.get(i).getHeight());
                            fensuA.setText("分速:" + resultList.get(i).getSpeed());
                            laloA.setText("经纬度:" + resultList.get(i).getLatitude() + "," + resultList.get(i).getLongitude());
                        }
                        if (i == 1) {
                            hightB.setText("高度:" + resultList.get(i).getHeight());
                            fensuB.setText("分速:" + resultList.get(i).getSpeed());
                            laloB.setText("经纬度:" + resultList.get(i).getLatitude() + "," + resultList.get(i).getLongitude());
                        }
                    }
                }


//                laloList.add(lalo);
                for (int i = 0; i < totalList.size(); i++) {
                    if (!ListUtils.isEmpty(totalList.get(i))) {
                        addPoint(i);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//获取多脚环上报数据列表
        if (EventUtils.isValid(eventByTag, EventTagConfig.getUpDatas, null)) {
            totalList.clear();
            Log.i("ds", eventByTag.getObj() + "");
            String trajectory = null;
            try {
                trajectory = new JSONObject(eventByTag.getObj() + "").getString("trajectorys");
                List<LaLoBean> resultList = new GsonParser().parseList(trajectory, new TypeToken<List<LaLoBean>>() {
                });

                if (!ListUtils.isEmpty(resultList)) {
                    for (int i = 0; i < resultList.size(); i++) {

                        boolean hasData=false;
                        for(int j=0;j<totalList.size();j++){
                            if(resultList.get(i).getRingName().equals(totalList.get(j).get(0).getRingName())){
                                totalList.get(j).add(resultList.get(i));
                                hasData=true;
                            }
                        }
                        if(!hasData){

                            List<LaLoBean> list=new ArrayList<LaLoBean>();
                            list.add(resultList.get(i));
                            totalList.add(list);
                        }

                    }
                    for(int i=0;i<totalList.size();i++){
                        if(ListUtils.isEmpty(lastIndex)&&first){
                            indexBean bean=new indexBean();
                            bean.setIndex(0);
                            lastIndex.add(bean);
                        }
                        if (i == 0) {
                            hightA.setText("高度:" + totalList.get(i).get(0).getHeight());
                            fensuA.setText("分速:" + totalList.get(i).get(0).getSpeed());
                            laloA.setText("经纬度:" + totalList.get(i).get(0).getLatitude() + "," + totalList.get(i).get(0).getLongitude());
                        }
                        if (i == 1) {
                            hightB.setText("高度:" +totalList.get(i).get(0).getHeight());
                            fensuB.setText("分速:" + totalList.get(i).get(0).getSpeed());
                            laloB.setText("经纬度:" + totalList.get(i).get(0).getLatitude() + "," + totalList.get(i).get(0).getLongitude());
                        }
                    }
                    first=false;
                    for (int i = 0; i < totalList.size(); i++) {
                        if (!ListUtils.isEmpty(totalList.get(i))) {
                            addPoint(i);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//                LaLoBean lalo = new GsonParser().parseObject(trajectory, LaLoBean.class);

        }

    }

    private LatLng pianyi(double lat, double lon) {
        double x = lon;
        double y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double temp = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);

        double bdLon = z * Math.cos(temp) + 0.005;
        double bdLat = z * Math.sin(temp) - 0.003;
        LatLng newcenpt = new LatLng(bdLat, bdLon);
        return newcenpt;
    }

    private void addPoint(int index) {
        List<LaLoBean> laloList = totalList.get(index);
        BitmapDescriptor bdA = BitmapDescriptorFactory
                .fromResource(niao[index]);
        int nowIndex=lastIndex.get(index).getIndex();
        for(int i=nowIndex;i<laloList.size();i++){
            if (i == 0) {
                //定义Maker坐标点

                LatLng p1 = pianyi(laloList.get(0).getLongitude(), laloList.get(0).getLatitude());
                LatLng p2 = pianyi(laloList.get(0).getLongitude(), laloList.get(0).getLatitude());
                MarkerOptions ooA = new MarkerOptions().position(p2).icon(
                        bdA);
                List<LatLng> points = new ArrayList<LatLng>();
                points.add(p1);
                points.add(p2);

                OverlayOptions ooPolyline = new PolylineOptions().width(5)
                        .color(Color.rgb(r.get(index), g.get(index), b.get(index))).points(points);
                Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
                float f = mBaiduMap.getMaxZoomLevel();// 19.0 最小比例尺
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(p1, f - 5);// 设置缩放比例
                mBaiduMap.animateMapStatus(u);
                mBaiduMap.addOverlay(ooA);
            } else {
                // 定义Maker坐标点

                LatLng p1 = pianyi(laloList.get(i-1).getLongitude(), laloList.get(i-1).getLatitude());
                LatLng p2 = pianyi(laloList.get(i).getLongitude(), laloList.get(i).getLatitude());
                List<LatLng> points = new ArrayList<LatLng>();
                points.add(p1);
                points.add(p2);
                MarkerOptions ooA = new MarkerOptions().position(p2).icon(
                        bdA);

                OverlayOptions ooPolyline = new PolylineOptions().width(5)
                        .color(Color.rgb(r.get(index), g.get(index), b.get(index))).points(points);
                Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(p2).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                mBaiduMap.addOverlay(ooA);
            }
        }
        lastIndex.get(index).setIndex(laloList.size()-1);
//        i++;
    }

    /**
     * 对地图事件的消息响应
     */
    private void initListener() {
        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {

            @Override
            public void onTouch(MotionEvent event) {

            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            /**
             * 单击地图
             */
            public void onMapClick(LatLng point) {
                touchType = "单击地图";
                currentPt = point;
                if (count == 0) {
                    firstPt = point;
                }
//                updateMapState();
            }

            /**
             * 单击地图中的POI点
             */
            public boolean onMapPoiClick(MapPoi poi) {
                touchType = "单击POI点";
                currentPt = poi.getPosition();
                if (count == 0) {
                    firstPt = poi.getPosition();
                }
//                updateMapState();
                return false;
            }
        });
        mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            /**
             * 长按地图
             */
            public void onMapLongClick(LatLng point) {
                touchType = "长按";
                currentPt = point;
                if (count == 0) {
                    firstPt = point;
                }
//                updateMapState();
            }
        });
        mBaiduMap.setOnMapDoubleClickListener(new BaiduMap.OnMapDoubleClickListener() {
            /**
             * 双击地图
             */
            public void onMapDoubleClick(LatLng point) {
                touchType = "双击";
                currentPt = point;
                if (count == 0) {
                    firstPt = point;
                }
//                updateMapState();
            }
        });

        /**
         * 地图状态发生变化
         */
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            public void onMapStatusChangeStart(MapStatus status) {
                // updateMapState();
            }

            public void onMapStatusChangeFinish(MapStatus status) {
                // updateMapState();
            }

            public void onMapStatusChange(MapStatus status) {
                // updateMapState();
            }
        });
        zoomButton = (Button) findViewById(R.id.zoombutton);
        rotateButton = (Button) findViewById(R.id.rotatebutton);
        overlookButton = (Button) findViewById(R.id.overlookbutton);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.equals(zoomButton)) {
                    perfomZoom();
                } else if (view.equals(rotateButton)) {
                    perfomRotate();
                } else if (view.equals(overlookButton)) {
                    perfomOverlook();
                }
            }

        };
        zoomButton.setOnClickListener(onClickListener);
        rotateButton.setOnClickListener(onClickListener);
        overlookButton.setOnClickListener(onClickListener);
    }

    /**
     * 处理缩放 sdk 缩放级别范围： [3.0,19.0]
     */
    private void perfomZoom() {
        EditText t = (EditText) findViewById(R.id.zoomlevel);
        try {
            float zoomLevel = Float.parseFloat(t.getText().toString());
            MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(zoomLevel);
            mBaiduMap.animateMapStatus(u);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入正确的缩放级别", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理旋转 旋转角范围： -180 ~ 180 , 单位：度 逆时针旋转
     */
    private void perfomRotate() {
        EditText t = (EditText) findViewById(R.id.rotateangle);
        try {
            int rotateAngle = Integer.parseInt(t.getText().toString());
            MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus())
                    .rotate(rotateAngle).build();
            MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
            mBaiduMap.animateMapStatus(u);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入正确的旋转角度", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理俯视 俯角范围： -45 ~ 0 , 单位： 度
     */
    private void perfomOverlook() {
        EditText t = (EditText) findViewById(R.id.overlookangle);
        try {
            int overlookAngle = Integer.parseInt(t.getText().toString());
            MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus())
                    .overlook(overlookAngle).build();
            MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
            mBaiduMap.animateMapStatus(u);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入正确的俯角", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理所有参数，改变地图状态
     */
    private void perfomAll() {
        EditText etOverlookAngle = (EditText) findViewById(R.id.overlookangle);
        EditText etZoomLevel = (EditText) findViewById(R.id.zoomlevel);
        EditText etRotateAngle = (EditText) findViewById(R.id.rotateangle);
        try {
            float zoomLevel = Float
                    .parseFloat(etZoomLevel.getText().toString());
            int rotateAngle = Integer.parseInt(etRotateAngle.getText()
                    .toString());
            int overlookAngle = Integer.parseInt(etOverlookAngle.getText()
                    .toString());
            MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus())
                    .rotate(rotateAngle).zoom(zoomLevel)
                    .overlook(overlookAngle).build();
            MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
            mBaiduMap.animateMapStatus(u);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入正确参数，旋转角和俯角需为整数", Toast.LENGTH_SHORT)
                    .show();
        }
    }


//    /**
//     * 定位SDK监听函数
//     */
//    public class MyLocationListenner implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // map view 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null) {
//                return;
//            }
//            mCurrentLat = location.getLatitude();
//            mCurrentLon = location.getLongitude();
//            mCurrentAccracy = location.getRadius();
//            locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(mCurrentDirection).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }
//        }
//
//        public void onReceivePoi(BDLocation poiLocation) {
//        }
//    }

    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class Lalo {
        double la;//经度
        double lo;//纬度

        public double getLa() {
            return la;
        }

        public void setLa(double la) {
            this.la = la;
        }

        public double getLo() {
            return lo;
        }

        public void setLo(double lo) {
            this.lo = lo;
        }
    }
    class indexBean{
int index;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}