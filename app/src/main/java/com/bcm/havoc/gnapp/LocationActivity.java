package com.bcm.havoc.gnapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.bcm.havoc.gnapp.Base.application.MyApplication;
import com.bcm.havoc.gnapp.ServerBean.GPSBean;
import com.bcm.havoc.gnapp.ServerBean.resultBean;
import com.bcm.havoc.gnapp.service.LocationService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;


/***
 * 单点定位示例，用来展示基本的定位结果，配置在LocationService.java中
 * 默认配置也可以在LocationService中修改
 * 默认配置的内容自于开发者论坛中对开发者长期提出的疑问内容
 * 
 * @author baidu
 *
 */
public class LocationActivity extends Activity {
	private String TAG = this.getClass().getSimpleName();
    private String URL="http://yf.duobaohe.net/api/Receive/addgpsInfo/";
	private LocationService locationService;
	private TextView LocationResult;
	private Button startLocation,uploadLocation;
	private Gson gson;
	private String memberid;
	private BDLocation locationss;
	private EditText et_memberid;
	private final int SDK_PERMISSION_REQUEST = 127;
	private String permissionInfo;
	private SharedPreferences sharedPrefs;
//	private LocationClientOption option;
	//每隔多少秒上传地址
//	private int frequence=3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// -----------demo view config ------------
		setContentView(R.layout.location);
		x.view().inject(this);
		gson = new Gson();
		getPersimmions();
		LocationResult = (TextView) findViewById(R.id.textView1);
		LocationResult.setMovementMethod(ScrollingMovementMethod.getInstance());
//		startLocation = (Button) findViewById(R.id.addfence);
		et_memberid= (EditText) findViewById(R.id.memberid);
		String css=et_memberid.getText().toString();
		Log.i(TAG,"onCreate.css="+css);

		sharedPrefs = getSharedPreferences("TimeGPS", Context.MODE_PRIVATE);
		if(sharedPrefs.getString("memberid", "1").equals("")){
			Log.i(TAG,"onCreate.et_memberid=null");
			et_memberid.setText("1");
		}
		et_memberid.setText(sharedPrefs.getString("memberid", "1").toString());
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString("memberid",et_memberid.getText().toString());
		editor.commit();
		String cs=sharedPrefs.getString("memberid", "1");
		Log.i(TAG,"onCreate.cs="+cs);
		onStart();



//		{ "MemberId": 10,"PointType":"mb","Lng": “116.288940”,"Lat": “39.824745”,"CreateTime":"2016-12-13 14:31:15.387" }
//		uploadLocation = (Button) findViewById(R.id.upload);
//		uploadLocation.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				uploadGPS();
//			}
//		});
//		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//
//		option.setScanSpan(frequence);
	}

	/**
	 * 显示请求字符串
	 * 
	 * @param str
	 */
	public void logMsg(String str) {
		final String s = str;
		try {
			if (LocationResult != null){
				new Thread(new Runnable() {
					@Override
					public void run() {
						LocationResult.post(new Runnable() {
							@Override
							public void run() {
								Log.i(TAG, "LocationResult"+s );
								LocationResult.setText(s);
								uploadGPS();
							}
						});

					}
				}).start();
			}
			//LocationResult.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/***
	 * Stop location service
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		String css=et_memberid.getText().toString();
		Log.i(TAG,"onStop.css="+css);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString("memberid",et_memberid.getText().toString());
		editor.commit();
		String cs=sharedPrefs.getString("memberid", "1");
		Log.i(TAG,"onStop.cs="+cs);
//		locationService.unregisterListener(mListener); //注销掉监听
//		locationService.stop(); //停止定位服务
		super.onStop();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		String css=et_memberid.getText().toString();
		Log.i(TAG,"onStart.css="+css);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString("memberid",et_memberid.getText().toString());
		editor.commit();
		String cs=sharedPrefs.getString("memberid", "1");
		Log.i(TAG,"onStart.cs="+cs);
		// -----------location config ------------
		locationService = ((MyApplication) getApplication()).locationService;
		//获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(mListener);
		//注册监听
		int type = getIntent().getIntExtra("from", 0);
		if (type == 0) {
			locationService.setLocationOption(locationService.getDefaultLocationClientOption());
		} else if (type == 1) {
			locationService.setLocationOption(locationService.getOption());
		}
//		startLocation.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (startLocation.getText().toString().equals(getString(R.string.startlocation))) {
					Log.i(TAG, "locationService.start()" );
					locationService.start();// 定位SDK
//											// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
//					startLocation.setText(getString(R.string.stoplocation));
//				} else {
//					Log.i(TAG, "locationService.stop()" );
//					locationService.stop();
//					startLocation.setText(getString(R.string.startlocation));
//					locationService.unregisterListener(mListener);
//				}
//			}
//		});
	}

	
	/*****
	 *
	 * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 *
	 */
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			locationss=location;
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				/**
				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
				 */
				sb.append(SystemClock.elapsedRealtime());
				sb.append("\nlocType : ");// 定位类型
				sb.append(location.getLocType());
				sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
				sb.append("\nlatitude : ");// 纬度
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");// 经度
				sb.append(location.getLongitude());
				sb.append("\nradius : ");// 半径
				sb.append(location.getRadius());
				sb.append("\nCountryCode : ");// 国家码
				sb.append(location.getCountryCode());
				sb.append("\nCountry : ");// 国家名称
				sb.append(location.getCountry());
				sb.append("\ncitycode : ");// 城市编码
				sb.append(location.getCityCode());
				sb.append("\ncity : ");// 城市
				sb.append(location.getCity());
				sb.append("\nDistrict : ");// 区
				sb.append(location.getDistrict());
				sb.append("\nStreet : ");// 街道
				sb.append(location.getStreet());
				sb.append("\naddr : ");// 地址信息
				sb.append(location.getAddrStr());
				sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
				sb.append(location.getUserIndoorState());
				sb.append("\nDirection(not all devices have value): ");
				sb.append(location.getDirection());// 方向
				sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
				sb.append("\nPoi: ");// POI信息
				if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
					for (int i = 0; i < location.getPoiList().size(); i++) {
						Poi poi = (Poi) location.getPoiList().get(i);
						sb.append(poi.getName() + ";");
					}
				}
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// 速度 单位：km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());// 卫星数目
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// 海拔高度 单位：米
					sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
					sb.append("\ndescribe : ");
					sb.append("gps定位成功");
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					// 运营商信息
				    if (location.hasAltitude()) {// *****如果有海拔高度*****
				        sb.append("\nheight : ");
	                    sb.append(location.getAltitude());// 单位：米
				    }
					sb.append("\noperationers : ");// 运营商信息
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("网络定位成功");
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
					sb.append("\ndescribe : ");
					sb.append("离线定位成功，离线定位结果也是有效的");
				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
				}
				logMsg(sb.toString());
			}
		}

		public void onConnectHotSpotMessage(String s, int i){
        }
	};

	private void uploadGPS() {
		RequestParams requestparams = new RequestParams(URL);
		/***
		 * "{ "MemberId": 10,"PointType":"mb","Lng": “116.288940”,"Lat": “39.824745”,"CreateTime":"2016-12-13 14:31:15.387" }
		 */
//		String MemberId ="10";
//		String PointType ="mb";
		String Lng =locationss.getLongitude()+"";
		String Lat =locationss.getLatitude()+"";
//		String CreateTime =locationss.getTime();
		String css=et_memberid.getText().toString();
		Log.i(TAG,"uploadGPS.css="+css);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString("memberid",et_memberid.getText().toString());
		editor.commit();
		String cs=sharedPrefs.getString("memberid", "1");
		Log.i(TAG,"uploadGPS.cs="+cs);
//		GPSBean bean = new GPSBean(sharedPrefs.getString("memberid", "1"),"mb",Lng,Lat,SystemClock.elapsedRealtime()+"");
        GPSBean bean = new GPSBean(sharedPrefs.getString("memberid", "1"),"mb",Lng,Lat);
		String RequestStr = gson.toJson(bean);
//        requestparams.addParameter("aaa", RequestStr);
		requestparams.setAsJsonContent(true);
		requestparams.setBodyContent(RequestStr);
		requestparams.setCharset("UTF-8");
		Log.i(TAG, "requestparams：" + requestparams);
		Log.i(TAG, "RequestStr：" + RequestStr);
		x.http().post(requestparams, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Log.i(TAG, "uploadGPS：" + result);
				//{"Status":0,"Msg":"OK"}
				java.lang.reflect.Type  Type =new TypeToken<resultBean>() {}.getType();
				resultBean relt = gson.fromJson(result,Type);
				String Msg = relt.getMsg();
				Log.i(TAG,"uploadGPS="+Msg);

			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Log.i(TAG,"uploadGPS="+ex);
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Log.i(TAG,"uploadGPS="+cex);
			}

			@Override
			public void onFinished() {

			}
		});
	}
	@TargetApi(23)
	private void getPersimmions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			ArrayList<String> permissions = new ArrayList<String>();
			/***
			 * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
			 */
			// 定位精确位置
			if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
			}
			if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
			}
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
			// 读写权限
			if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
			}
			// 读取电话状态权限
			if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
				permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
			}

			if (permissions.size() > 0) {
				requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
			}
		}
	}
	@TargetApi(23)
	private boolean addPermission(ArrayList<String> permissionsList, String permission) {
		if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
			if (shouldShowRequestPermissionRationale(permission)){
				return true;
			}else{
				permissionsList.add(permission);
				return false;
			}

		}else{
			return true;
		}
	}

	@TargetApi(23)
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		// TODO Auto-generated method stub
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		String css=et_memberid.getText().toString();
		Log.i(TAG,"onResume.css="+css);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString("memberid",et_memberid.getText().toString());
		editor.commit();
		String cs=sharedPrefs.getString("memberid", "1");
		Log.i(TAG,"onResume.cs="+cs);
	}
}
