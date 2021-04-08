package com.example.bhariwala.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.PayRent
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.Models.User
import com.example.bhariwala.PayRentBillsActivity
import com.example.bhariwala.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class HomeLordBillsListAdapter(private val mContext: Context, private val mPayRentList: List<Tenant>)
    : RecyclerView.Adapter<HomeLordBillsListAdapter.ViewHolder>(){



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeLordBillsListAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.tenant_list_for_bill_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPayRentList.size
    }

    override fun onBindViewHolder(holder: HomeLordBillsListAdapter.ViewHolder, position: Int) {
        var tenant = mPayRentList[position]

        getTenantNameandImage(holder, tenant.getHomeLordId())
        getFlatNameDetails(holder, tenant.getFlatId())


        holder.all_received_bills.setOnClickListener{
           var  intent = Intent(mContext, PayRentBillsActivity::class.java)
            intent.putExtra("tenantName", tenant.getTenantUserName())
            mContext.startActivity(intent)
        }

    }

    private fun getTenantNameandImage(holder: ViewHolder, userId: String) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
        tenantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user = snapshot.getValue(User::class.java)
                    holder.tenant_name.text = user!!.getName()
                    Picasso.get().load(user!!.getImage()).into(holder.tenant_img)
                }
            }
        })
    }

    private fun getFlatNameDetails(holder: ViewHolder, flatId: String) {
        var ref = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId)
         ref.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)
                    holder.flat_name.text = "Flat: "+flat!!.getFlatId()
                    holder.building_name.text = "Building: "+flat.getPropertyName()
                }
            }
        })
    }


    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var tenant_name: TextView = itemView.findViewById(R.id.payRent_tenant_name)
        var flat_name: TextView = itemView.findViewById(R.id.payRent_flat_name)
        var building_name: TextView = itemView.findViewById(R.id.payRent_building_name)
        var all_received_bills: TextView = itemView.findViewById(R.id.payRent_show_all_bills_list_for_tenant)
        var tenant_img: ImageView = itemView.findViewById(R.id.tenant_img_bill_item)
    }

}