package com.example.bhariwala.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.bhariwala.Models.Flat
import com.example.bhariwala.Models.Tenant
import com.example.bhariwala.Models.User
import com.example.bhariwala.R
import com.example.bhariwala.TenantDetailsActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class TenantsListHLAdapter(private val mContext: Context, private val mTenantList: List<Tenant>)
    : RecyclerView.Adapter<TenantsListHLAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TenantsListHLAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.tenant_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mTenantList.size
    }

    override fun onBindViewHolder(holder: TenantsListHLAdapter.ViewHolder, position: Int) {
        var tenant = mTenantList[position]


        getTenantNameandImage(holder, tenant.getTenantId())
        getFlatNameDetails(holder, tenant.getFlatId())

        holder.tenant_details.setOnClickListener{
            var intent = Intent(mContext, TenantDetailsActivity::class.java)
            intent.putExtra("tenantUserId", tenant!!.getTenantId())
            mContext.startActivity(intent)
        }

    }
    private fun getFlatNameDetails(holder: ViewHolder, flatId: String) {
        var ref = FirebaseDatabase.getInstance().reference.child("Flats").child(flatId)
        ref.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var flat = snapshot.getValue(Flat::class.java)
                    holder.flat_name.text = flat!!.getFlatName()
                    holder.building_name.text = flat.getPropertyName()
                }
            }
        })
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
                    Picasso.get().load(user!!.getImage()).into(holder.tenantItem_image)
                }
            }
        })
    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var tenant_name:TextView = itemView.findViewById(R.id.tHL_tenant_name)
        var flat_name:TextView = itemView.findViewById(R.id.tHL_flat_name)
        var building_name:TextView = itemView.findViewById(R.id.tHL_building_name)
        var tenant_details: AppCompatButton = itemView.findViewById(R.id.tHL_tenant_details_show_btn)
        var tenantItem_image: ImageView = itemView.findViewById(R.id.tenantItem_image)
    }

}