package com.example.technews

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.technews.fragment.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {



    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar : Toolbar = findViewById(R.id.toolbar1)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Tech News"

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val name = sharedPreferences.getString(NAME,"").toString()

        val image = sharedPreferences.getString(IMAGE,"").toString()



        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)

        val navView : NavigationView = findViewById(R.id.nav_view)


        val headerLayout : View = navView.inflateHeaderView(R.layout.nav_header)


        val profileImage : ImageView = headerLayout.findViewById(R.id.header_image)

        val profileName : TextView = headerLayout.findViewById(R.id.header_name)


        Glide.with(profileImage).load("http://192.168.157.1:3000/img/" + image)
            .fitCenter()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(profileImage)

        profileName.text = name


        profileImage.setOnClickListener {

            val intent = Intent(this,ProfileActivity::class.java)

            startActivity(intent)

        }



        toolbar.setNavigationOnClickListener {


            drawerLayout.open()
        }



        navView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.nav_home -> changeFragment(HomeFragment(),"")

                R.id.nav_shop -> changeFragment(ShopFragment(),"")

                R.id.nav_events-> changeFragment(EventsFragment(),"")

                R.id.nav_friends-> changeFragment(FriendsFragment(),"")

                R.id.nav_messages-> changeFragment(MessagesFragment(),"")

                R.id.nav_settings-> changeFragment(SettingsFragment(),"")

                R.id.nav_communities-> changeFragment(CommunitiesFragment(),"")

            }


            it.isChecked = true


            drawerLayout.close()


            true
        }






        supportFragmentManager.beginTransaction().add(R.id.fragment_container, HomeFragment()).commit()


    }



    private fun changeFragment(fragment: Fragment, name: String) {

        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("").commit()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_notifications){

            changeFragment(NotificationsFragment(),"")
        }

        return super.onOptionsItemSelected(item)
    }

}
