package com.example.bhariwala.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.AssignTenantActivity
import com.example.bhariwala.FlatDetailsActivity
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.R
import com.example.bhariwala.TenantDetailsActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.flat_item.view.*

class FlatAdapter(private var mContext: Context, private var flatList: List<Flat>): RecyclerView.Adapter<FlatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlatAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.flat_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flatList.size
    }

    override fun onBindViewHolder(holder: FlatAdapter.ViewHolder, position: Int) {
        var flat = flatList[position]

        holder.flat_name.text = flat!!.getFlatName()
        holder.building_name.text = flat!!.getPropertyName()


        holder.flat_details.setOnClickListener {
            var intent = Intent(mContext, FlatDetailsActivity::class.java)
            intent.putExtra("flatId", flat!!.getFlatId())
            mContext.startActivity(intent)
        }

        updateAddTenantButtonStatus(holder, flat!!.getFlatId())

        holder.flat_add_OR_details.setOnClickListener {
            if(holder.flat_add_OR_details.text == "Add Tenant"){
                var intent = Intent(mContext, AssignTenantActivity::class.java)
                intent.putExtra("flatId", flat!!.getFlatId())
                mContext.startActivity(intent)
            }else{
                goToTenantDetails(flat!!.getFlatId())
            }

        }


    }

    private fun goToTenantDetails(flatId: String) {
        var tenantRef = FirebaseDatabase.getInstance().reference.child("Tenants")
        tenantRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var tenant = item.getValue(Tenant::class.java)
                        if(tenant!!.getFlatId().equals(flatId)){
                            var intent = Intent(mContext, TenantDetailsActivity::class.java)
                            intent.putExtra("tenantUserId", tenant!!.getTenantId())
                            mContext.startActivity(intent)
                        }
                    }
                }
            }
        })
    }

    private fun updateAddTenantButtonStatus(holder: FlatAdapter.ViewHolder, flatId: String) {
        var addtenantBtn = holder.flat_add_OR_details
        var flatRef = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId)
        flatRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)
                    if(flat!!.getIsBooked() == "1"){
                        addtenantBtn.text = "Tenant Details"
                    }else{
                        addtenantBtn.setText("Add Tenant")
                    }
                }
            }
        })
    }


    inner class ViewHolder(@NonNull itemView: View):RecyclerView.ViewHolder(itemView) {
        var flat_name : TextView = itemView.flat_flat_name
        var building_name : TextView = itemView.flat_building_name
        var flat_details :AppCompatButton = itemView.flat_details_show_btn
        var flat_add_OR_details :AppCompatButton = itemView.add_tenant_or_details_btn
    }
}
