package com.basic.cooknest

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val bottom_nav = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)

        loadFragment(MainFragment())

        bottom_nav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> loadFragment(MainFragment())
                R.id.cuisines -> loadFragment(CuisineFragment())
                R.id.wishlist -> loadFragment(WishlistFragment())
                R.id.profile -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentloader,fragment)
            .commit()
    }
}