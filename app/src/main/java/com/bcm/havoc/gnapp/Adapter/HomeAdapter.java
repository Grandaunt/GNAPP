package com.bcm.havoc.gnapp.Adapter;


import com.bcm.havoc.gnapp.Entity.OrderInfoEntity;
import com.bcm.havoc.gnapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HomeAdapter extends BaseQuickAdapter<OrderInfoEntity, BaseViewHolder> {
    public HomeAdapter( List data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfoEntity item) {
//        helper.setImageResource(R.id.icon, item.getImageResource());
        helper.setText(R.id.item_home_name, item.getBeekeePerEntity().getFarm_name());
        helper.setText(R.id.item_home_status, item.getStatus());

    }
}
