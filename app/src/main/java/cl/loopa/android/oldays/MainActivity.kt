package cl.loopa.android.oldays

import android.os.Bundle
import android.view.Menu


import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
//import androidx.navigation.fragment.NavHostFragment
import cl.loopa.android.oldays.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


//import android.view.MenuItem
//import android.view.View

//import androidx.annotation.NonNull
//import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.appcompat.widget.Toolbar
//import androidx.core.view.GravityCompat
//import androidx.navigation.NavController
//import androidx.navigation.Navigation
//import androidx.navigation.ui.NavigationUI

//import android.R

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    // https://proandroiddev.com/migrating-the-deprecated-kotlin-android-extensions-compiler-plugin-to-viewbinding-d234c691dec7
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // https://proandroiddev.com/migrating-the-deprecated-kotlin-android-extensions-compiler-plugin-to-viewbinding-d234c691dec7
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        /* When creating the NavHostFragment using FragmentContainerView or
        if manually adding the NavHostFragment to your activity via a FragmentTransaction,
        attempting to retrieve the NavController in onCreate() of an Activity via
        Navigation.findNavController(Activity, @IdRes int) will fail.
        You should retrieve the NavController directly from the NavHostFragment instead.
        https://developer.android.com/guide/navigation/navigation-getting-started#navigate */

        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        //val navHostFragment =
        //supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        //val navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.defaultFragment, R.id.aboutFragment
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    /*
        override fun onBackPressed() {
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }
    */

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
