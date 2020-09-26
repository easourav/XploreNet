package com.sourav.rxnet.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sourav.rxnet.R
import com.sourav.rxnet.adapter.FtpAdapter
import com.sourav.rxnet.utils.Common
import com.sourav.rxnet.webapi.ApiService
import com.sourav.rxnet.webapi.RetrofitClient
import com.sourav.rxnet.webapi.response.FTP
import kotlinx.android.synthetic.main.fragment_entertainment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EntertainmentFragment : Fragment() {
    private lateinit var apiService: ApiService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entertainment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiService = RetrofitClient.getClient().create(ApiService::class.java)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        ftpRecyclerView.layoutManager = layoutManager

        fetchFTP()
    }

    private fun fetchFTP() {
        val bannerCall: Call<ArrayList<FTP>> = apiService.ftp
        bannerCall.enqueue(object : Callback<ArrayList<FTP>> {
            override fun onFailure(call: Call<ArrayList<FTP>>, t: Throwable) {
                progress_circular.visibility = View.GONE
                Common.onSNACK(main_view, "Something wrong, try again later")
            }

            override fun onResponse(
                call: Call<ArrayList<FTP>>,
                response: Response<ArrayList<FTP>>
            ) {
                if (response.isSuccessful){
                    val ftpList: ArrayList<FTP>? = response.body()
                    val ftpAdapter = ftpList?.let { FtpAdapter(it, activity) }
                    ftpRecyclerView.adapter = ftpAdapter
                }else{
                    Common.onSNACK(main_view, "Something wrong, try again later")
                }
                progress_circular.visibility = View.GONE
            }

        })
    }
}