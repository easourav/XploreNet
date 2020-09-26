package com.sourav.rxnet.webapi;



import com.sourav.rxnet.webapi.response.AllBranch;
import com.sourav.rxnet.webapi.response.AllMainMenu;
import com.sourav.rxnet.webapi.response.AllMenuInfo;
import com.sourav.rxnet.webapi.response.Banner;
import com.sourav.rxnet.webapi.response.BillingInfo;
import com.sourav.rxnet.webapi.response.BillingTitle;
import com.sourav.rxnet.webapi.response.CovaregArea;
import com.sourav.rxnet.webapi.response.FTP;
import com.sourav.rxnet.webapi.response.GetAllThana;
import com.sourav.rxnet.webapi.response.HelpLine;
import com.sourav.rxnet.webapi.response.MyIp;
import com.sourav.rxnet.webapi.response.Notification;
import com.sourav.rxnet.webapi.response.Packages;
import com.sourav.rxnet.webapi.response.RouterInfo;
import com.sourav.rxnet.webapi.response.RouterTitle;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("public/api/GetAllMainMenu")
    Call<ArrayList<AllMainMenu>> getAllMainMenu();

    @GET("public/api/GetAllThana")
    Call<ArrayList<GetAllThana>> getAllThana();

    @GET("public/api/GetAllMenuInfo")
    Call<ArrayList<AllMenuInfo>> getAllMenuInfo();

    @GET("public/api/GetBillTitles")
    Call<ArrayList<BillingTitle>> getBillingTitle();

    @GET("public/api/GetBillInfos")
    Call<ArrayList<BillingInfo>> getBillingInfo();

    @GET("public/api/GetHelplineNo")
    Call<ArrayList<HelpLine>> getHelpLine();

    @GET("public/api/GetAllBranches")
    Call<ArrayList<AllBranch>> getAllBranch();

    @GET("public/api/GetRouterSetupTitles")
    Call<ArrayList<RouterTitle>> getRouterTitle();

    @GET("public/api/GetRouterSetupInfos")
    Call<ArrayList<RouterInfo>> getRouterInfo();

    @GET("public/api/GetAllCoverage")
    Call<ArrayList<CovaregArea>> getCoverageArea();

    @GET("public/api/GetBannerDetails")
    Call<ArrayList<Banner>> getBanner();

    @GET("public/api/GetFtpDetails")
    Call<ArrayList<FTP>> getFtp();

    @GET("public/api/GetAllPackages")
    Call<ArrayList<Packages>> getPackages();

    @GET("public/api/GetAllNotificationList")
    Call<ArrayList<Notification>> getNotification();

    @GET("/")
    Call<MyIp> getIp();

}
