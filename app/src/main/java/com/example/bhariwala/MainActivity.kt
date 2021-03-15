package com.example.bhariwala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.bhariwala.Fragments.AddsFragment
import com.example.bhariwala.Fragments.HomeLordFragment
import com.example.bhariwala.Fragments.MessageFragment
import com.example.bhariwala.Fragments.TenantFragment
import com.example.bhariwala.Models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_menu_header.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var isFABopen : Boolean = false
    var selectFragment: Fragment? = null
    var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null
    private var msgCount = 4
    private var currentUserStatus = ""


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.home_nav_menu -> {
                if(currentUserStatus == "Homelord"){
                    selectedFramnetItem(HomeLordFragment())
                }else{
                    selectedFramnetItem(TenantFragment())
                }

                return@OnNavigationItemSelectedListener true
            }
            R.id.adds_nav_menu -> {
                selectedFramnetItem(AddsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.message_nav_menu -> {
                startActivity(Intent(this, MessageMainAcitivity::class.java))
               // selectedFramnetItem(MessageFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.setting_nav_menu -> {
                Toast.makeText(this, "Not yet set", Toast.LENGTH_LONG).show()
            }
        }
        if(selectFragment != null){
            supportFragmentManager.beginTransaction().replace(R.id.main_container_frameLayout, selectFragment!!).commit()
        }
        false
    }

    private fun selectedFramnetItem(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_container_frameLayout, fragment).commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        // ======= drawer toggole layout on action bar

        mDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, top_main_toolbar, R.string.dOpen, R.string.dClose)

        drawer_layout.bringToFront()
        drawer_layout.addDrawerListener(mDrawerToggle!!)
        mDrawerToggle!!.syncState()

        var drawer_nav_view: NavigationView = findViewById(R.id.drawer_navigationView)
        drawer_nav_view.setNavigationItemSelectedListener(this)
        drawer_nav_view.itemIconTintList = null

        //======= bottom navigation menu options
        var BottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_menu)
        BottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        //===== redirect homelord or tenant
        redirectHomelordORTenant()
        showUserInformation()


        //======== bottom floating operation


//        var fab = findViewById<FloatingActionButton>(R.id.fab)
//        var fab1 = findViewById<FloatingActionButton>(R.id.fab1)
//        var fab2 = findViewById<FloatingActionButton>(R.id.fab2)
//            fab.setOnClickListener {
//                if(!isFABopen){
//                    showFABMenu()
//                }else{
//                    closeFABMenu()
//                }
//            }


        showMessageCountInNM(BottomNavView, msgCount)


    }

    private fun showUserInformation() {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                        drawer_head_user_name.text = user!!.getName()
                        drawer_head_user_status.text = user!!.getUser()
                        Picasso.get().load(user!!.getImage()).into(drawer_head_user_image)
                }
            }
        })
    }

    private fun showMessageCountInNM(bottomNavView: BottomNavigationView, msgCount: Int) {
        if (msgCount == 0) {
            bottomNavView.removeBadge(R.id.message_nav_menu)
        } else {
            val badge = bottomNavView.getOrCreateBadge(R.id.message_nav_menu) // previously showBadge
            badge.number = msgCount
            badge.backgroundColor = getColor(R.color.teal_700)
            badge.badgeTextColor = getColor(R.color.white)
        }
    }


    private fun redirectHomelordORTenant() {
        var userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userRef.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

        override fun onDataChange(snapshot: DataSnapshot) {
            if(snapshot.exists()){
                    var user = snapshot.getValue(User::class.java)
                        if(user!!.getUser() == "Homelord"){
                            currentUserStatus = "Homelord"
                            selectedFramnetItem(HomeLordFragment())
                        }else{
                            currentUserStatus = "Tenant"
                            selectedFramnetItem(TenantFragment())
                        }
                }
            }
        })
    }




//    private fun closeFABMenu() {
//        isFABopen=true;
//        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_45));
//        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
//    }
//
//    private fun showFABMenu() {
//        isFABopen=false;
//        fab1.animate().translationY(toFloat(0))
//        fab2.animate().translationY(toFloat(0));
//    }
//    override fun onSupportNavigateUp(): Boolean {
//        drawerLayout.openDrawer(navView)
//        return true
//    }
//
//        // override the onBackPressed() function to close the Drawer when the back button is clicked
//        override fun onBackPressed() {
//            if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                this.drawerLayout.closeDrawer(GravityCompat.START)
//            } else {
//                super.onBackPressed()
//            }
//        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> {
                if(currentUserStatus == "Homelord"){
                    startActivity(Intent(this, MainActivity::class.java))
                }else{
                    startActivity(Intent(this, TenantActivity::class.java))
                }

            }
            R.id.drawer_message -> {
                var intent = Intent(this, MessageMainAcitivity::class.java)
                startActivity(intent)
            }
            R.id.drawerSignOut -> {
                mAuth!!.signOut()
                var intent = Intent(this, SignInAcitvity::class.java)
                startActivity(intent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}







