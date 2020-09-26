package com.sourav.rxnet.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sourav.rxnet.R
import com.sourav.rxnet.adapter.FtpAdapter
import com.sourav.rxnet.adapter.MenuAdapter
import com.sourav.rxnet.adapter.MenuList
import com.sourav.rxnet.webapi.response.FTP
import kotlinx.android.synthetic.main.fragment_entertainment.*

class PayBillFragment : Fragment() {
    var menus: ArrayList<MenuList>? = null
    var menu : MenuList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menus = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay_bill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        ftpRecyclerView.layoutManager = layoutManager

        addMenu()

        val menuAdapter = menus?.let { MenuAdapter(it, activity!!) }
        ftpRecyclerView.adapter = menuAdapter

    }

    private fun addMenu() {
        menus!!.add(MenuList("MOBILE BANKING", R.drawable.ic_smartphone, "","mbk"))
        menus!!.add(MenuList("ONLINE  PAYMENT", R.drawable.ic_digital_wallet, "","op"))
        //menus!!.add(MenuList("OFFLINE PAYMENT", R.drawable.ic_salary, "",""))
        menus!!.add(MenuList("ROUTER SETUP", R.drawable.ic_wireless_router, "","rou"))
        menus!!.add(MenuList("BILLING GUIDELINE", R.drawable.ic_bill, "","bill"))
    }
}