package com.example.ilmapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ilmapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen:SplashScreen
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        splashAnimation()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNav()

    }
    private fun setupBottomNav() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Bottom Navigation sadece home_graph içindeki fragment'lerde görünsün
            if (destination.id == R.id.navigation_home ||
                destination.id == R.id.navigation_dashboard ||
                destination.id == R.id.navigation_notifications) {
                bottomNavigationView.visibility = View.VISIBLE
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
                bottomNavigationView.visibility = View.GONE
            }
        }

        // BottomNavigationView'i NavController'a bağla
        bottomNavigationView.setupWithNavController(navController)
    }
    private fun initViews(){

    }


    private fun initEvent(){

    }
    private fun splashAnimation(){
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // ObjectAnimator ile animasyonu ayarlıyoruz
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView.view,
                "translationY",
                splashScreenView.view.height.toFloat(),
                0f
            ).apply {
                duration = 200L
                interpolator = AnticipateInterpolator()
                // Animasyon tamamlandığında splash ekranını kaldırıyoruz
                doOnEnd { splashScreenView.remove() }
            }

            // Animasyonu başlatıyoruz
            slideUp.start()
        }

    }
}