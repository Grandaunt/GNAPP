package com.bcm.havoc.gnapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bcm.havoc.gnapp.Base.application.MyApplication;
import com.bcm.havoc.gnapp.Base.ui.BaseActivity;
import com.bcm.havoc.gnapp.Base.utils.logger.Logger;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaygoo.widget.RangeSeekBar;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;

public class BaseInfoActivity extends BaseActivity implements View.OnClickListener{
    public static String intenttag2="BaseInfo";
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter adapter;
    private Button btn_miss_fn;
    private  String str;
    private RangeSeekBar seekbar1,seekbar2,seekbar3;
    private List<String>  mDataList;
    private ImageView im_beer_1,im_beer_2,im_beer_3;
    //    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyApplication application;
    private  int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);

//        initWindow();
        initData();
        initView();
//        initAdapter();
    }

    private void initData() {
        mDataList=new ArrayList<>();
        mDataList.add("1");
        mDataList.add("1");
    }

    private void initView() {
        application=new MyApplication();
        Intent intent = getIntent();
        str = intent.getStringExtra(BaseInfoActivity.intenttag2);
        if(str.equals("registered")){
            setTitle("注册");
        }
        else if(str.equals("edit")){
            setTitle("编辑个人信息");
        }
        setBackBtn();
        findViewById(R.id.btn_miss_fn).setOnClickListener(this);
//        seekbar1 = findViewById(R.id.seekbar1);
//        seekbar1.setValue(0, 100);
//        seekbar2 = findViewById(R.id.seekbar2);
//        seekbar2.setValue(0, 100);
//        seekbar3 = findViewById(R.id.seekbar3);
//        seekbar3.setValue(0, 100);
        im_beer_1 = findViewById(R.id.im_beer_1);
        im_beer_1.setOnClickListener(this);
        im_beer_2 = findViewById(R.id.im_beer_2);
        im_beer_2.setOnClickListener(this);
        im_beer_3 = findViewById(R.id.im_beer_3);
        im_beer_3.setOnClickListener(this);

//        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));

    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    /**
     * 自定义单选
     * =
     */
    public void openRadio() {

        RxGalleryFinal
                .with(BaseInfoActivity.this)
                .image()
                .radio()
//                .cropAspectRatioOptions(0, new AspectRatio("9:9", 90, 90))
//                .crop()
                .imageLoader(ImageLoaderType.FRESCO)
                .subscribe(new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        String  path = imageRadioResultEvent.getResult().getOriginalPath();
                        Logger.i(path);
//                        Toast.makeText(BaseInfoActivity.this, "选中了图片路径：" + imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
//                        UImage(path);
                        if(flag==1){
                            x.image().bind(im_beer_1, path, application.imageOptions);
                        }
                        else     if(flag==2){
                            x.image().bind(im_beer_2, path, application.imageOptions);
                        }
                        else{
                            x.image().bind(im_beer_3, path, application.imageOptions);
                        }
                    }
                })
                .openGallery();


    }
    /**
     * OPEN 图片单选实现方法
     * <p>
     * 默认使用 第三个 ，如果运行sample,可自行改变Type，运行Demo查看效果
     */
    private void openImageSelectRadioMethod(int type) {
        RxGalleryFinalApi instance = RxGalleryFinalApi.getInstance(this);
        switch (type) {
            case 0:

                //打开单选图片，默认参数
                instance
                        .setImageRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                Logger.i("0imageRadioResultEvent");
                            }
                        }).open();

                break;
            case 1:

                //设置自定义的参数
                instance
                        .setType(RxGalleryFinalApi.SelectRXType.TYPE_IMAGE, RxGalleryFinalApi.SelectRXType.TYPE_SELECT_RADIO)
                        .setImageRadioResultEvent(new RxBusResultDisposable<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                Logger.i("1imageRadioResultEvent");
                            }
                        }).open();

                break;
            case 2:

                //快速打开单选图片,flag使用true不裁剪
                RxGalleryFinalApi
                        .openRadioSelectImage(this, new RxBusResultDisposable<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent o) throws Exception {
                                Logger.i("2imageRadioResultEvent");
                            }
                        }, true);

                break;
            case 3:

                //单选，使用RxGalleryFinal默认设置，并且带有裁剪
                instance
                        .openGalleryRadioImgDefault(
                                new RxBusResultDisposable<ImageRadioResultEvent>() {
                                    @Override
                                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                        Logger.i("3imageRadioResultEvent");
                                    }
                                })
                        .onCropImageResult(
                                new IRadioImageCheckedListener() {
                                    @Override
                                    public void cropAfter(Object t) {
                                        Logger.i("裁剪完成");
                                    }

                                    @Override
                                    public boolean isActivityFinish() {
                                        Logger.i("返回false不关闭，返回true则为关闭");
                                        return true;
                                    }
                                });

                break;
        }
    }

//    private void initAdapter() {
//        adapter= new MyAdapter(mDataList);
//        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN );//动画效果为缩放
////        动画默认只执行一次,如果想重复执行可设置
//        adapter.isFirstOnly(false);
////        adapter.addHeaderView(top);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });
//
//        mRecyclerView.setAdapter(adapter);
//    }
//    public class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
//                public MyAdapter(List data) {
//            super(R.layout.item_teixt_view,data);
//        }

    //        @Override
//        protected void convert(BaseViewHolder helper, String item) {
////        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetName);
//
//
//
//
//        }
//
//
//    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_miss_fn:
                if(str.equals("registered")){
                    Intent intent = new Intent(BaseInfoActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else if(str.equals("edit")){
                    finish();
                }
                Toast.makeText(BaseInfoActivity.this, "信息保存成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.im_beer_1:
                openRadio();
                flag=1;
                break;
            case R.id.im_beer_2:
                openRadio();
                flag=2;
                break;
            case R.id.im_beer_3:
                openRadio();
                flag=3;
                break;
//        case R.id.tv_new_furit_kind:
//
//            break;
//            case R.id.tv_new_area:
//                finish();
//                break;
//               default:
//                break;

        }

    }
    @Override
    protected void onResume() {

        super.onResume();
    }
}
