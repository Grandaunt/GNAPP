package com.bcm.havoc.gnapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bcm.havoc.gnapp.Adapter.HomeAdapter;
import com.bcm.havoc.gnapp.Base.application.MyApplication;
import com.bcm.havoc.gnapp.Entity.BeekeePerEntity;
import com.bcm.havoc.gnapp.Entity.BeesInfoEntity;
import com.bcm.havoc.gnapp.Entity.FruitGrowersEntity;
import com.bcm.havoc.gnapp.Entity.FruitTreesInfoEntity;
import com.bcm.havoc.gnapp.Entity.OrderInfoEntity;
import com.bcm.havoc.gnapp.MainActivity;
import com.bcm.havoc.gnapp.NewActivity;
import com.bcm.havoc.gnapp.OrderInfoActivity;
import com.bcm.havoc.gnapp.R;
import com.bcm.havoc.gnapp.service.LocationService;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private LocationService locationService;
    private TextView LocationResult;
    private Button startLocation;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MyApplication application;
    private RecyclerView mRecyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View top;
    private OnFragmentInteractionListener mListener;
    private ArrayList<OrderInfoEntity> mDataList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View view;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static android.support.v4.app.Fragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
//        return inflater.inflate(R.layout.fragment_home, container, false);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        initAdapter();
        return view;
    }
    private void initView(View view) {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        application = (MyApplication) getActivity().getApplication();
//        LocationResult = (TextView) view.findViewById(R.id.textView1);
//        LocationResult.setMovementMethod(ScrollingMovementMethod.getInstance());
//        startLocation = (Button) view.findViewById(R.id.addfence);
        mRecyclerView = (RecyclerView)  view.findViewById(R.id.rv_list);
        mRecyclerView = (RecyclerView) view. findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        top = getLayoutInflater().inflate(R.layout.top_view, (ViewGroup) mRecyclerView.getParent(), false);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),NewActivity.class);
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) view. findViewById(R.id.swipeLayout);
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
        initView(view);
        initAdapter();
        mSwipeRefreshLayout.setRefreshing(false);

    }
    @SuppressWarnings("unchecked")
    private void initAdapter() {
        BaseQuickAdapter homeAdapter = new HomeAdapter( mDataList);
        homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN );//动画效果为缩放
//        动画默认只执行一次,如果想重复执行可设置
        homeAdapter.isFirstOnly(false);
        homeAdapter.addHeaderView(top);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(),OrderInfoActivity.class);
                if(position<1) {
                    intent.putExtra(MainActivity.intenttag1, "old");
                }
                else{
                    intent.putExtra(MainActivity.intenttag1, "over");
                }
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(homeAdapter);
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
     List<BeekeePerEntity>   mBekDataList = new ArrayList<>();
        BeekeePerEntity bentity1 = new BeekeePerEntity("1", "张卫华", "昌平福事多蜂业有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "3.5万-6万", "52.2km", "1",
                "65", "2", "20000", "200",R.mipmap.ic_fsd_logo1, beerlist);
        BeekeePerEntity bentity2 = new BeekeePerEntity("2", "张卫华", "百花蜂业有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "15万-19万", "52.7km", "1",
                "65", "2", "20000", "200",R.mipmap.ic_fsd_logo2,  beerlist);
        BeekeePerEntity bentity3 = new BeekeePerEntity("3", "张卫华", "北京市蜂业公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "12万-17万", "52.9km", "1",
                "65", "2", "20000", "200", R.mipmap.ic_fsd_logo3, beerlist);
        BeekeePerEntity bentity4 = new BeekeePerEntity("4", "张卫华", "知蜂堂有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "12万-17万", "53.2km", "1",
                "65", "2", "20000", "200",R.mipmap.ic_fsd_logo4,  beerlist);
        BeekeePerEntity bentity5 = new BeekeePerEntity("5", "张卫华", "明园蜂业有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "9.6万-15万", "58.8km", "1",
                "65", "2", "20000", "200",R.mipmap.ic_fsd_logo5,  beerlist);
        BeekeePerEntity bentity6 = new BeekeePerEntity("6", "张卫华", "蜂之语蜂业有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "9.2万-15万", "61.4km", "1",
                "65", "2", "20000", "200",R.mipmap.ic_fsd_logo6,  beerlist);
        BeekeePerEntity bentity7 = new BeekeePerEntity("7", "张卫华", "汪氏蜜蜂园有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "9.1万-15万", "64.7km", "1",
                "65", "2", "20000", "200",R.mipmap.ic_fsd_logo7,  beerlist);
        BeekeePerEntity bentity8 = new BeekeePerEntity("8", "张卫华", "冠生园蜂制品有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "9.1万-15万", "69.1km", "1",
                "65", "2", "20000", "200", R.mipmap.ic_fsd_logo8, beerlist);
        BeekeePerEntity bentity9= new BeekeePerEntity("9", "张卫华", "颐寿园（北京）有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "8.6万-14万", "72.5km", "1",
                "65", "2", "20000", "200",R.mipmap.ic_fsd_logo9,  beerlist);
        BeekeePerEntity bentity10= new BeekeePerEntity("10", "张卫华", "王巢蜂业有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "7.8万-9万", "78.6km", "1",
                "65", "2", "20000", "200",R.mipmap.ic_fsd_logo10,  beerlist);
        BeekeePerEntity bentity11= new BeekeePerEntity("11", "张卫华", "趣蜂场有限公司", "北京市昌平区南口镇", "12",
                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "3.5万-6万", "81.7km", "1",
                "65", "2", "20000", "200",R.mipmap.ic_fsd_logo11,  beerlist);
        mBekDataList.add(bentity1);
        mBekDataList.add(bentity2);
        mBekDataList.add(bentity3);
        mBekDataList.add(bentity4);
        mBekDataList.add(bentity5);
        mBekDataList.add(bentity6);
        mBekDataList.add(bentity7);
        mBekDataList.add(bentity8);
        mBekDataList.add(bentity9);
        mBekDataList.add(bentity10);
        mBekDataList.add(bentity11);
//        BeekeePerEntity bentity = new BeekeePerEntity("", "张卫华", "昌平福事多蜂业有限公司", "北京市昌平区南口镇", "12",
//                "18651002639", "http://cpfyxh.cpweb.gov.cn/", "35000", "12",
//                "1", "65", "2", "20000", "200", beerlist);
//
        List<FruitTreesInfoEntity> fruitTreesInfoEntityList = new ArrayList<>();
        FruitGrowersEntity fentity = new FruitGrowersEntity("","","","","","","","",fruitTreesInfoEntityList );
        for(int i=0;i<11;i++) {
            String   status ="已完成";
            if(i==0){
                status ="未完成";
            }
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity(fentity,mBekDataList.get(i),status);

            mDataList.add(orderInfoEntity);
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





}
