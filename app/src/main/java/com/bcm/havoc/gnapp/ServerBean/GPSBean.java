package com.bcm.havoc.gnapp.ServerBean;

/**
 * Created by SJS on 2017/4/10.
 */

public  class GPSBean {
    String MemberId;
    String PointType;
    String Lng ;
    String Lat;
//    String CreateTime ;

    public GPSBean(String MemberId, String PointType, String Lng, String Lat) {
        this.MemberId=MemberId;
        this.PointType=PointType;
        this.Lng=Lng;
        this.Lat=Lat;
//        this.CreateTime=CreateTime;
    }
    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getPointType() {
        return PointType;
    }

    public void setPointType(String pointType) {
        PointType = pointType;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

//    public String getCreateTime() {
//        return CreateTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        CreateTime = createTime;
//    }

    @Override
    public String toString() {
        return "GPSBean{" +
                "MemberId='" + MemberId + '\'' +
                ", PointType='" + PointType + '\'' +
                ", Lng='" + Lng + '\'' +
                ", Lat='" + Lat + '\'' +
//                ", CreateTime='" + CreateTime + '\'' +
                '}';
    }
}