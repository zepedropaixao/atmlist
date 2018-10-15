package me.paixao.atmlist.ui

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.nav_content_main.rv_atm
import me.paixao.atmlist.R
import me.paixao.atmlist.data.Atm
import timber.log.Timber

class MainAct : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private val disposables = CompositeDisposable()
    private lateinit var viewModel: MainActViewModel
    private lateinit var mMap: GoogleMap

    inner class MyDrawerToggle(activity: Activity?,
                         drawerLayout: DrawerLayout?,
                         toolbar: Toolbar?,
                         openDrawerContentDescRes: Int,
                         closeDrawerContentDescRes: Int) :
            ActionBarDrawerToggle(activity,
                drawerLayout,
                toolbar,
                openDrawerContentDescRes,
                closeDrawerContentDescRes) {

        override fun onDrawerSlide(drawerView: View, offset: Float) {
            val container = findViewById<View>(R.id.main_content)
            container.translationX = offset * drawerView.width
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupRecyclerView()

        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainActViewModel::class.java)
        viewModel.initViewModel(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val toggle = MyDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    lateinit var adapter: AtmListAdapter

    fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(this)
        rv_atm.layoutManager = layoutManager
        adapter = AtmListAdapter(mutableListOf())

        disposables.add(adapter.getViewClickedObservable()
                .subscribe({
                    drawer_layout.closeDrawer(GravityCompat.START)
                    moveCamera(it)
                }, Timber::e))

        rv_atm.adapter = adapter
    }

    fun addAtms(atms: List<Atm>) {
        adapter.addAtms(atms)
        if(mapReady) {
            addMarkers(atms)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private var mapReady : Boolean = false

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mapReady = true
        addMarkers(adapter.atms)
    }

    fun addMarkers(atms: List<Atm>) {
        if(atms.isNotEmpty()) {
            for(atm in atms) addMarker(atm)
            moveCamera(atms.last())
        }
    }

    fun moveCamera(atm: Atm) {
        val currentLocation = atm.getLatLng()
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 14f)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        mMap.animateCamera(cameraUpdate)
    }

    fun addMarker(atm: Atm) = mMap.addMarker(MarkerOptions().position(atm.getLatLng()).title(atm.name))

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
