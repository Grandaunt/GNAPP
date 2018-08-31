package com.bcm.havoc.gnapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bcm.havoc.gnapp.FnInfoActivity;
import com.bcm.havoc.gnapp.R;

import static android.content.Context.SENSOR_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LookListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LookListFragment extends Fragment implements SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
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

    MapView mMapView;
    BaiduMap mBaiduMap;

    // UI相关
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    Button requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;
    private Marker mMarkerA;
    private Marker mMarkerB;
    private Marker mMarkerC;
    private Marker mMarkerD;
    private Marker mMarkerE;
    private Marker mMarkerF;
    private Marker mMarkerG;
    private Marker mMarkerH;
    private Marker mMarkerI;
    private Marker mMarkerJ;
    private Marker mMarkerK;
    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.mipmap.ic_loc_fn);
    private InfoWindow mInfoWindow;
    public LookListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LookListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LookListFragment newInstance(String param1, String param2) {
        LookListFragment fragment = new LookListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_look_list, container, false);
        initView(view);
        initData();

        return view;
    }



    private void initView(View view) {
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        // 地图初始化
        mMapView = (MapView) view.findViewById(R.id.mmap);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();



        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_loc_gn);
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker,accuracyCircleFillColor,accuracyCircleStrokeColor));
        initOverlay();
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                Button button = new Button(getActivity());
                button.setBackgroundResource(R.drawable.popup);
                InfoWindow.OnInfoWindowClickListener listener = null;
//                if (marker == mMarkerA || marker == mMarkerD) {
                    button.setText("福事多");
                    button.setTextColor(Color.BLACK);
                    button.setWidth(300);

                    listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
//                            LatLng ll = marker.getPosition();
//                            LatLng llNew = new LatLng(ll.latitude + 0.005,
//                                    ll.longitude + 0.005);
//                            marker.setPosition(llNew);
//                            mBaiduMap.hideInfoWindow();
                            Intent intent = new Intent(getActivity(),FnInfoActivity.class);
                            startActivity(intent);
                        }
                    };
                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);

                return true;
            }
        });

    }
    public void initOverlay() {
        // add marker overlay
        LatLng llA = new LatLng(40.263175, 116.200244);
        LatLng llB = new LatLng(40.342821, 116.229199);
        LatLng llC = new LatLng(40.236723, 116.615541);
        LatLng llD = new LatLng(40.306965, 116.511394);
        LatLng llE = new LatLng(40.336965, 116.421394);
        LatLng llF = new LatLng(40.326965, 116.261394);
        LatLng llG = new LatLng(40.316965, 116.201394);
        LatLng llH = new LatLng(40.306965, 116.171394);
        LatLng llI = new LatLng(40.406965, 116.401394);
        LatLng llJ = new LatLng(40.206965, 116.311394);
        LatLng llK = new LatLng(40.256965, 116.251394);

        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
                .zIndex(9).draggable(true);
//
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
        MarkerOptions ooB = new MarkerOptions().position(llB).icon(bdA)
                .zIndex(5);
        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
        MarkerOptions ooC = new MarkerOptions().position(llC).icon(bdA)
                .zIndex(7).draggable(true);
        mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
        MarkerOptions ooD = new MarkerOptions().position(llD).icon(bdA)
                .zIndex(7).draggable(true);
        mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
        MarkerOptions ooE = new MarkerOptions().position(llE).icon(bdA)
                .zIndex(7).draggable(true);
        mMarkerE = (Marker) (mBaiduMap.addOverlay(ooE));
        MarkerOptions ooF = new MarkerOptions().position(llF).icon(bdA)
                .zIndex(7).draggable(true);
        mMarkerF = (Marker) (mBaiduMap.addOverlay(ooF));
        MarkerOptions ooG = new MarkerOptions().position(llG).icon(bdA)
                .zIndex(7).draggable(true);
        mMarkerG = (Marker) (mBaiduMap.addOverlay(ooG));
        MarkerOptions ooH = new MarkerOptions().position(llH).icon(bdA)
                .zIndex(7).draggable(true);
        mMarkerH = (Marker) (mBaiduMap.addOverlay(ooH));
        MarkerOptions ooI = new MarkerOptions().position(llI).icon(bdA)
                .zIndex(7).draggable(true);
        mMarkerI = (Marker) (mBaiduMap.addOverlay(ooI));
        MarkerOptions ooJ = new MarkerOptions().position(llJ).icon(bdA)
                .zIndex(7).draggable(true);
        mMarkerJ = (Marker) (mBaiduMap.addOverlay(ooJ));
        MarkerOptions ooK = new MarkerOptions().position(llK).icon(bdA)
                .zIndex(7).draggable(true);
        mMarkerK = (Marker) (mBaiduMap.addOverlay(ooK));


//        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
//            public void onMarkerDrag(Marker marker) {
//            }
//
//            public void onMarkerDragEnd(Marker marker) {
//                Toast.makeText(
//                       getActivity(),
//                        "拖拽结束，新位置：" + marker.getPosition().latitude + ", "
//                                + marker.getPosition().longitude,
//                        Toast.LENGTH_LONG).show();
//            }
//
//            public void onMarkerDragStart(Marker marker) {
//            }
//        });

    }

    private void initData() {

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
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

//    public void initOverlay() {
//        // add marker overlay
//
//        LatLng llA = new LatLng(39.963175, 116.400244);
//        LatLng llB = new LatLng(39.942821, 116.369199);
//        LatLng llC = new LatLng(39.939723, 116.425541);
//        LatLng llD = new LatLng(39.906965, 116.401394);
//
//        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
//                .zIndex(9).draggable(true);
//        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//
//        MarkerOptions ooC = new MarkerOptions().position(llC).icon(bdA)
//                .perspective(false).anchor(0.5f, 0.5f).rotate(30).zIndex(7);
//        mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
//        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//        giflist.add(bdA);
//        giflist.add(bdA);
//        giflist.add(bdA);
//        MarkerOptions ooD = new MarkerOptions().position(llD).icons(giflist)
//                .zIndex(0).period(10);
//        mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
//
//        // add ground overlay
//        LatLng southwest = new LatLng(39.92235, 116.380338);
//        LatLng northeast = new LatLng(39.947246, 116.414977);
//        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
//                .include(southwest).build();
//
//
//        MapStatusUpdate u = MapStatusUpdateFactory
//                .newLatLng(bounds.getCenter());
//        mBaiduMap.setMapStatus(u);
//
//        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
//            public void onMarkerDrag(Marker marker) {
//            }
//
//            public void onMarkerDragEnd(Marker marker) {
//                Toast.makeText(
//                       getActivity(),
//                        "拖拽结束，新位置：" + marker.getPosition().latitude + ", "
//                                + marker.getPosition().longitude,
//                        Toast.LENGTH_LONG).show();
//            }
//
//            public void onMarkerDragStart(Marker marker) {
//            }
//        });
//    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//
//        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        bdA.recycle();
    }

}
