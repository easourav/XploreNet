package com.sourav.rxnet.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.sourav.rxnet.R
import com.sourav.rxnet.adapter.FtpAdapter
import com.sourav.rxnet.adapter.MenuAdapter
import com.sourav.rxnet.adapter.MenuList
import com.sourav.rxnet.ui.activity.MainActivity
import com.sourav.rxnet.ui.activity.NoInternetActivity
import com.sourav.rxnet.utils.Common
import com.sourav.rxnet.webapi.ApiService
import com.sourav.rxnet.webapi.RetrofitClient
import com.sourav.rxnet.webapi.response.Banner
import com.sourav.rxnet.webapi.response.FTP
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var apiService: ApiService
    private var fragment: Fragment? = null
    var menus: ArrayList<MenuList>? = null
    var menusHelpLine: ArrayList<MenuList>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menus = ArrayList()
        menusHelpLine = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiService = RetrofitClient.getClient().create(ApiService::class.java)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val layoutManager2 = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val layoutManager3 = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        en_rv.layoutManager = layoutManager
        bill_rv.layoutManager = layoutManager2
        help_rv.layoutManager = layoutManager3

        if (Common.isNetworkAvailable(activity!!)){
            fetchBanner()
            fetchFTP()

            addMenu()

            val menuAdapter = menus?.let { MenuAdapter(it, activity!!) }
            val menuAdapterHelpLine = menusHelpLine?.let { MenuAdapter(it, activity!!) }
            bill_rv.adapter = menuAdapter
            help_rv.adapter = menuAdapterHelpLine
        }else{
            //image_view_online.setImageResource(R.drawable.off_line_bg)
            Handler().postDelayed({
                if(Common.isNetworkAvailable(activity!!)){
                    startActivity(Intent(activity!!, MainActivity::class.java))
                    activity!!.finish()
                } else {
                    startActivity(Intent(activity!!, NoInternetActivity::class.java))
                    activity!!.finish()
                }
            }, 1500)

            Common.onSNACK(
                main_view_home,
                "Please Connect Internet !!"
            )
        }
    }

    private fun fetchFTP() {
        val bannerCall: Call<ArrayList<FTP>> = apiService.ftp
        bannerCall.enqueue(object : Callback<ArrayList<FTP>> {
            override fun onFailure(call: Call<ArrayList<FTP>>, t: Throwable) {
                progress_circular.visibility = View.GONE
                content_layout.visibility = View.GONE
                empty_iv.visibility = View.VISIBLE
                Common.onSNACK(main_view_home, "Something wrong, try again later")
            }

            override fun onResponse(
                call: Call<ArrayList<FTP>>,
                response: Response<ArrayList<FTP>>
            ) {
                if (response.isSuccessful){
                    val ftpList: ArrayList<FTP>? = response.body()
                    val ftpAdapter = ftpList?.let { FtpAdapter(it, activity) }
                    en_rv.adapter = ftpAdapter
                    content_layout.visibility = View.VISIBLE
                    empty_iv.visibility = View.GONE
                }else{
                    content_layout.visibility = View.GONE
                    empty_iv.visibility = View.VISIBLE
                    Common.onSNACK(main_view_home, "Something wrong, try again later")
                }
                progress_circular.visibility = View.GONE
            }

        })
    }


    private fun fetchBanner() {
        val bannerCall: Call<ArrayList<Banner>> = apiService.banner
        bannerCall.enqueue(object : Callback<ArrayList<Banner>> {
            override fun onFailure(call: Call<ArrayList<Banner>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ArrayList<Banner>>,
                response: Response<ArrayList<Banner>>
            ) {
                if (response.isSuccessful){
                    val bannerResponse = response.body()
                    val imageList = ArrayList<SlideModel>() // Create image list
                    for (banner in bannerResponse!!) {
                        //val bannerContent = bannerResponse[a]
                        val img =  "https://rsnetbd.ispms.net/bannerImage/"+banner.bannerImage
                        imageList.add(SlideModel(img))
                    }
                    if(imageList.size > 0){
                        imageSlider.setImageList(imageList)
                    }
                    //imageSlider.startSliding(3000)

                }else{
                }
            }
        })
    }

    private fun addMenu() {
        menus!!.add(MenuList("MOBILE BANKING", R.drawable.ic_smartphone, "","mbk"))
        menus!!.add(MenuList("ONLINE  PAYMENT", R.drawable.ic_digital_wallet, "","op"))
        //menus!!.add(MenuList("OFFLINE PAYMENT", R.drawable.ic_salary, "",""))
        menus!!.add(MenuList("ROUTER SETUP", R.drawable.ic_wireless_router, "","rou"))
        menus!!.add(MenuList("BILLING  GUIDELINE", R.drawable.ic_bill, "","bill"))

        menusHelpLine!!.add(MenuList("HELP LINE", R.drawable.ic_help_line, "","help"))
        menusHelpLine!!.add(MenuList("SELF CARE", R.drawable.ic_psychology, "","web"))
        menusHelpLine!!.add(MenuList("LOCATION", R.drawable.ic_connection, getString(R.string.location_url),"web"))
        menusHelpLine!!.add(MenuList("FACEBOOK CHAT", R.drawable.ic_facebook, "www.facebook.com","web"))
        menusHelpLine!!.add(MenuList("FACEBOOK GROUP", R.drawable.ic_group, "www.facebook.com","web"))
        menusHelpLine!!.add(MenuList("YOUTUBE", R.drawable.ic_youtube, "https://www.youtube.com/","web"))
    }

}