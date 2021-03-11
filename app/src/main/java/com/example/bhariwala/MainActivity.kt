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
import com.example.bhariwala.Fragments.HomeLordFragment
import com.example.bhariwala.Fragments.MessageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var isFABopen : Boolean = false

    var selectFragment: Fragment? = null
    var mDrawerToggle: ActionBarDrawerToggle? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.home -> {
                selectedFramnetItem(HomeLordFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.message -> {
                selectedFramnetItem(MessageFragment())
                return@OnNavigationItemSelectedListener true
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

        // ======= drawer toggole layout on action bar


        Toast.makeText(this, "clicked on home", Toast.LENGTH_LONG).show()

        mDrawerToggle = ActionBarDrawerToggle(this, drawer_layout, top_main_toolbar, R.string.dOpen, R.string.dClose)

        drawer_layout.bringToFront()
        drawer_layout.addDrawerListener(mDrawerToggle!!)
        mDrawerToggle!!.syncState()


        var nav_view: NavigationView = findViewById(R.id.drawer_navigationView)
        nav_view.setNavigationItemSelectedListener(this)





        //======= bottom navigation menu options
        var navView: BottomNavigationView = findViewById(R.id.bottom_nav_menu)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        selectedFramnetItem(HomeLordFragment())


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
                var intent = Intent(this, TenantActivity::class.java)
                startActivity(intent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}







