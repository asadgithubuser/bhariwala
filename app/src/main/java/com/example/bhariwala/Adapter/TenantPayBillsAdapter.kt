package com.example.bhariwala.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.PayRent
import com.example.bhariwala.R

class TenantPayBillsAdapter(private val mContext: Context, private val mPayRentList: List<PayRent>)
    : RecyclerView.Adapter<TenantPayBillsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TenantPayBillsAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.tenant_pay_rent_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPayRentList.size
    }

    override fun onBindViewHolder(holder: TenantPayBillsAdapter.ViewHolder, position: Int) {
        var payRent = mPayRentList[position]



        holder.payRent_hlname.text = payRent.getHomeLordName()
        holder.payRent_fname.text = "Flat: "+payRent.getFlatName()
        holder.payRent_bname.text = "Building: "+payRent.getBuildingName()

//            holder.payRent_time.text = payRent.
//            holder.payRent_date.text = payRent.
        holder.payRent_message.text = "You have sent to homelord ${payRent.getHomeLordName()} only ${payRent.getPaidRentAmount()} " +
                "tk for monthly bill at ${payRent.getPaidRentMonth()}"
        holder.payRent_rantAmount.text = payRent.getPaidRentAmount()+" tk"
        holder.payRent_rentMOnth.text = payRent.getPaidRentMonth()
        holder.payRent_via_service.text = payRent.getPayViaService()


//        holder.tenant_details.setOnClickListener{
//            var intent = Intent(mContext, HomeLordBillsListAdapter::class.java)
//            intent.putExtra("tenantUserName", tenant!!.getTenantUserName())
//            mContext.startActivity(intent)
//        }

    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var payRent_hlname: TextView = itemView.findViewById(R.id.paidrent_hl_name)
        var payRent_time: TextView = itemView.findViewById(R.id.paidrent_time)
        var payRent_date: TextView = itemView.findViewById(R.id.paidrent_date)
        var payRent_bname: TextView = itemView.findViewById(R.id.paidrent_building)
        var payRent_fname: TextView = itemView.findViewById(R.id.paidrent_flat_name)
        var payRent_message: TextView = itemView.findViewById(R.id.paidrent_message)
        var payRent_rantAmount: TextView = itemView.findViewById(R.id.paidrent_rentAmount)
        var payRent_rentMOnth: TextView = itemView.findViewById(R.id.paidrent_rent_month)
        var payRent_via_service: TextView = itemView.findViewById(R.id.paidrent_via_service)
    }

}