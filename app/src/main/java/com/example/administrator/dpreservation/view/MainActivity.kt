package com.example.administrator.dpreservation.view

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.AMapOptions
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.model.*
import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.adapter.Event
import com.example.administrator.dpreservation.adapter.NearAdapter
import com.example.administrator.dpreservation.adapter.TypeAdapter
import com.example.administrator.dpreservation.core.DoctorManager
import com.example.administrator.dpreservation.core.UserManage
import com.example.administrator.dpreservation.data.Position
import com.example.administrator.dpreservation.data.doctor.Doctor
import com.example.administrator.dpreservation.databinding.ActivityMainBinding
import com.example.administrator.dpreservation.utilities.Util
import com.example.administrator.dpreservation.utilities.ViewModelFactory
import com.example.administrator.dpreservation.viewmodel.DoctorModel

class MainActivity : BaseActivity() , LocationSource, AMapLocationListener {

    lateinit var binding: ActivityMainBinding
    private var mLocationClient: AMapLocationClient?=null;
    private var mLocationOption: AMapLocationClientOption? =null;
    private var mListener: LocationSource.OnLocationChangedListener? =null;
    private var isFirst = true
    private var amap: AMap? = null
    private var markerMe: Marker? = null
    private lateinit var model: DoctorModel
    private val nearAdapter = NearAdapter()
    private var isLocate = false
    private val doctorMarkerMap = HashMap<Doctor,Marker>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.setLifecycleOwner(this)
        binding.mapView.onCreate(savedInstanceState)
        init()
        val factory = ViewModelFactory.getClinicModelFactory(this)
        model = ViewModelProviders.of(this,factory).get(DoctorModel::class.java)
        DoctorManager.requestDoctor(this){

        }
    }

    private fun initDoctorMarker(list:PagedList<Doctor>){
        if (amap == null)return
        list.forEach {
            if (!doctorMarkerMap.containsKey(it)){
                val option = MarkerOptions()
                    .position(LatLng(it.position.latitude,it.position.longitude))
                    .title(it.name)
                val marker = amap!!.addMarker(option)
                doctorMarkerMap[it] = marker
            }
        }
    }

    private fun init(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home_black_18dp)
        val nearRcView = binding.nearRcView
        val manage = LinearLayoutManager(this)
        nearRcView.layoutManager = manage
        nearRcView.adapter = nearAdapter
        amap = binding.mapView.map
        amap?.setLocationSource(this)
        val setting = amap!!.uiSettings
        setting.isZoomControlsEnabled = true
        setting.zoomPosition = AMapOptions.ZOOM_POSITION_RIGHT_CENTER
        setting.isMyLocationButtonEnabled = true
        amap?.isMyLocationEnabled = true
        mLocationClient= AMapLocationClient(this)
        mLocationClient?.setLocationListener(this)
        mLocationOption =  AMapLocationClientOption()
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        mLocationOption?.isNeedAddress = true
        mLocationOption?.isOnceLocation = false
        mLocationOption?.isWifiScan = true
        mLocationOption?.isMockEnable = false
        mLocationOption?.interval = 2000
        mLocationClient?.setLocationOption(mLocationOption)
        mLocationClient?.startLocation()
        nearAdapter.setEvent(object :Event{
            override fun event(vararg any: Any) {
                val d = any[0]
                if (d is Doctor){
                    val position = d.position
                    amap?.moveCamera(CameraUpdateFactory.changeLatLng( LatLng(position.latitude,position.longitude)));
                    mLocationClient?.stopLocation()
                }
            }
        })
    }

    private fun addMark(latLng: LatLng){
        val option = MarkerOptions()
            .position(latLng)
            .title("我")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_position_icon))
        markerMe?.remove()
        markerMe =  amap?.addMarker(option)
    }

    override fun deactivate() {
        mListener = null
    }

    override fun activate(listener: LocationSource.OnLocationChangedListener?) {
        mListener = listener
    }

    override fun onLocationChanged(amaplocation: AMapLocation?) {
        with(amaplocation){
            if (this == null){
                return
            }
            if (errorCode !=0) {
                return
            }
            val p = Position(country,province,city,district,streetNum,"",latitude,longitude)
            if (!isLocate){
                model.getNearClinic(city,district,streetNum).observe(this@MainActivity, Observer {
                    nearAdapter.submitList(it)
                    initDoctorMarker(it)
                })
                isLocate = true
            }
            UserManage.position = p
            binding.position = p
            if (isFirst){
                amap?.moveCamera(CameraUpdateFactory.zoomTo(10f));
                amap?.moveCamera(CameraUpdateFactory.changeLatLng( LatLng(latitude, longitude)));
                //点击定位按钮 能够将地图的中心移动到定位点
                mListener?.onLocationChanged(this)
                isFirst = false
            }
            addMark(LatLng(p.latitude,p.longitude))
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
        mLocationClient?.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val item = menu!!.findItem(R.id.search)
        item.setOnMenuItemClickListener {
            onSearchRequested()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.messageFragment->{
                val intent = Intent(this,MessageActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home->binding.drawlayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
        handlerSearch()
    }
    private fun handlerSearch(){
        if (Intent.ACTION_SEARCH == intent.action){
            val query = intent.getStringExtra(SearchManager.QUERY)
            val keyword = "%$query%"
            model.queryByKeyword(keyword).observe(this, Observer {
                nearAdapter.submitList(it)
            })
        }
    }
}
