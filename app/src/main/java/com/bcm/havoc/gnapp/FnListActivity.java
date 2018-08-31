package com.bcm.havoc.gnapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bcm.havoc.gnapp.Base.ui.BaseActivity;
import com.bcm.havoc.gnapp.Entity.BeekeePerEntity;
import com.bcm.havoc.gnapp.Entity.BeesInfoEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FnListActivity  extends BaseActivity implements View.OnClickListener  {
    public static String intenttag="fnno";
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter adapter;
    private List<BeekeePerEntity>  mDataList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //距离计算两地距离
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fn_list);
//        initDb();
        initData();
        initView();
        initAdapter();
    }

    private void initData() {
        //假数据
        mDataList = new ArrayList<>();
        List<String> photos = new ArrayList<>();
        photos.add("R.mipmap.im_beerx_1");
        photos.add("R.mipmap.im_beerx_2");
        photos.add("R.mipmap.im_beerx_3");
        BeesInfoEntity bees= new BeesInfoEntity("1","熊蜂",photos,"2500");
        List<BeesInfoEntity> beerlist = new ArrayList<>();
        beerlist.add(bees);
        mDataList = new ArrayList<>();
        BeekeePerEntity bentity1 = new BeekeePerEntity("1", "张卫华", "昌平福事多蜂业有限公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "3.5万-6万", "52.2km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity2 = new BeekeePerEntity("2", "张卫华", "百花蜂业有限公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "15万-19万", "52.7km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity3 = new BeekeePerEntity("3", "张卫华", "北京市蜂业公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "12万-17万", "52.9km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity4 = new BeekeePerEntity("4", "张卫华", "知蜂堂有限公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "12万-17万", "53.2km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity5 = new BeekeePerEntity("5", "张卫华", "明园蜂业有限公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "9.6万-15万", "58.8km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity6 = new BeekeePerEntity("6", "张卫华", "蜂之语蜂业有限公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "9.2万-15万", "61.4km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity7 = new BeekeePerEntity("7", "张卫华", "汪氏蜜蜂园有限公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "9.1万-15万", "64.7km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity8 = new BeekeePerEntity("8", "张卫华", "冠生园蜂制品有限公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "9.1万-15万", "69.1km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity9= new BeekeePerEntity("9", "张卫华", "颐寿园（北京）有限公司", "北京市昌平区南口镇", "12",
                                                      "18651002639", "http://cpfyxh.cpweb.gov.cn/", "8.6万-14万", "72.5km", "1",
                                                      "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity10= new BeekeePerEntity("10", "张卫华", "王巢蜂业有限公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "7.8万-9万", "78.6km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        BeekeePerEntity bentity11= new BeekeePerEntity("11", "张卫华", "趣蜂场有限公司", "北京市昌平区南口镇", "12",
                                                       "18651002639", "http://cpfyxh.cpweb.gov.cn/", "3.5万-6万", "81.7km", "1",
                                                       "65", "2", "20000", "200", beerlist);
        mDataList.add(bentity1);
        mDataList.add(bentity2);
        mDataList.add(bentity3);
        mDataList.add(bentity4);
        mDataList.add(bentity5);
        mDataList.add(bentity6);
        mDataList.add(bentity7);
        mDataList.add(bentity8);
        mDataList.add(bentity9);
        mDataList.add(bentity10);
        mDataList.add(bentity11);
        findViewById(R.id.btn_pj).setOnClickListener(this);
        findViewById(R.id.btn_zh).setOnClickListener(this);
        findViewById(R.id.btn_jl).setOnClickListener(this);
    }

    private void initView() {
        setTitle("附近蜂场");
        setBackBtn();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

    }

    private void refresh() {
        initData();
        initView();
        initAdapter();
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private void initAdapter() {
        adapter= new MyAdapter(mDataList);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN );//动画效果为缩放
//        动画默认只执行一次,如果想重复执行可设置
        adapter.isFirstOnly(false);
//        adapter.addHeaderView(top);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(FnListActivity.this, FnInfoActivity.class);
//                intent.putExtra(FnListActivity.intenttag, mDataList.get(position));
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(adapter);
    }



    public class MyAdapter extends BaseQuickAdapter<BeekeePerEntity, BaseViewHolder> {
        public MyAdapter(List data) {
            super(R.layout.item_fn_info,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BeekeePerEntity item) {
//        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetName);

            helper.setText(R.id.item_home_name, item.getFarm_name());
            helper.setText(R.id.item_home_beer_num, item.getTotal_num());
            helper.setText(R.id.item_fn_distance, item.getService_area_hight());

//            helper.setText(R.id.tv_rk_time, item.getStrInTime());
            switch (item.getId()) {
                case "1":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo1);
                    break;
                case "2":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo2);
                    break;
                case "3":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo3);
                    break;
                case "4":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo4);
                    break;
                case "5":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo5);
                    break;
                case "6":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo6);
                    break;
                case "7":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo7);
                    break;
                case "8":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo8);
                    break;
                case "9":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo9);
                    break;
                case "10":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo10);
                    break;

                case "11":
                    helper.setImageResource(R.id.ic_item_fn_logo,R.mipmap.ic_fn_com_logo11);
                    break;




                default:
                    break;

            }

        }


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pj:
                refresh();
                break;
            case R.id.btn_zh:
                refresh();
                break;
            case R.id.btn_jl:
                refresh();
                break;
           default:
                break;

        }

    }
    @Override
    protected void onResume() {
        refresh();
        super.onResume();
    }
}
