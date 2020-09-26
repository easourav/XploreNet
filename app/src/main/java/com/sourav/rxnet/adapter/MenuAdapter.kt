package com.sourav.rxnet.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sourav.rxnet.R
import com.sourav.rxnet.ui.activity.DetailsActivity
import com.sourav.rxnet.ui.activity.HelpLineActivity
import com.sourav.rxnet.ui.activity.MenuActivity
import com.sourav.rxnet.ui.activity.WebViewActivity
import java.util.*

class MenuAdapter(var ftpArrayList: ArrayList<MenuList>, activity: FragmentActivity) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_ftp, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ftp = ftpArrayList[position]
        holder.textTv.text = ftp.getTitle()
        val url = ftp.getUrl()
        Glide.with(mContext)
            .load(ftp.image)
            .into(holder.icon)
        holder.itemView.setOnClickListener {
            /*Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);*/
            if (ftp.type.contains("mbk")){
                actionMobileBill()
            }else if (ftp.type.contains("op")){
                val intent = Intent(mContext, DetailsActivity::class.java)
                intent.putExtra("id", "3")
                intent.putExtra("title", "E-Bill")
                intent.putExtra("onOpen", "other")
                mContext.startActivity(intent)
            }else if (ftp.type.contains("rou")){
                val intent = Intent(mContext, MenuActivity::class.java)
                intent.putExtra("onOpen", "router")
                mContext.startActivity(intent)
            }else if (ftp.type.contains("bill")){
                val intent = Intent(mContext, DetailsActivity::class.java)
                intent.putExtra("id", "6")
                intent.putExtra("title", "Payment Guideline")
                intent.putExtra("onOpen", "other")
                mContext.startActivity(intent)
            }else if (ftp.type.contains("help")){
                val intent = Intent(mContext, HelpLineActivity::class.java)
                mContext.startActivity(intent)
            }else if (ftp.type.contains("web")){
                val intent = Intent(mContext, WebViewActivity::class.java)
                intent.putExtra("url", ftp.url)
                mContext.startActivity(intent)
            }

        }
    }

    private fun actionMobileBill() {
        val dialog = Dialog(mContext)
        dialog.setContentView(R.layout.mobile_bill_layout)
        dialog.setCanceledOnTouchOutside(true)

        val bkashPageTV: TextView = dialog.findViewById(R.id.bkashPageTV)
        val rocketTV: TextView = dialog.findViewById(R.id.rocketTV)

        bkashPageTV.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra("id", "5")
            intent.putExtra("title", "Bkash Payment")
            intent.putExtra("onOpen", "other")
            mContext.startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()

        rocketTV.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra("id", "8")
            intent.putExtra("title", "Rocket Payment")
            intent.putExtra("onOpen", "other")
            mContext.startActivity(intent)
            dialog.dismiss()
        }
    }
    override fun getItemCount(): Int {
        return ftpArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textTv: TextView
        var icon: ImageView

        init {
            textTv = itemView.findViewById(R.id.item_text)
            icon = itemView.findViewById(R.id.item_image)
        }
    }

    init {
        mContext = activity
    }
}