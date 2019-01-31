package com.example.administrator.dpreservation.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.Marker
import com.amap.api.maps2d.model.MarkerOptions

import com.example.administrator.dpreservation.R
import com.example.administrator.dpreservation.adapter.NearAdapter
import com.example.administrator.dpreservation.adapter.TypeAdapter
import com.example.administrator.dpreservation.data.Position
import com.example.administrator.dpreservation.databinding.FragmentMapBinding
import com.example.administrator.dpreservation.utilities.DialogUtil
import com.example.administrator.dpreservation.utilities.Util
import com.example.administrator.dpreservation.utilities.ViewModelFactory
import com.example.administrator.dpreservation.viewmodel.ClinicModel
import com.google.android.material.snackbar.Snackbar

class MapFragment : Fragment() , LocationSource,AMapLocationListener{


    lateinit var binding:FragmentMapBinding
    private var mLocationClient:AMapLocationClient?=null;
    private var mLocationOption: AMapLocationClientOption? =null;
    private var mListener: LocationSource.OnLocationChangedListener? =null;
    private var isFirst = true
    private var amap:AMap? = null
    private var markerMe:Marker? = null
    private lateinit var model:ClinicModel
    private val nearAdapter = NearAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMapBinding.inflate(LayoutInflater.from(context),null,false)
        binding.setLifecycleOwner(this)
        init()
        binding.mapView.onCreate(savedInstanceState)
        val factory = ViewModelFactory.getClinicModelFactory(requireContext())
        model = ViewModelProviders.of(this,factory).get(ClinicModel::class.java)
        model.initData()
       // DialogUtil.showSheetDialog(requireContext(),nearAdapter)
        model.getNearClinic().observe(this, Observer {
            nearAdapter.submitList(it)
        })
        return binding.root
    }



    private fun init(){
        val nearRcView = binding.nearRcView
        nearRcView.layoutManager = LinearLayoutManager(requireContext())
        nearRcView.adapter = nearAdapter
        binding.typeRc.adapter = TypeAdapter()
        binding.typeRc.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        amap = binding.mapView.map
        amap?.setLocationSource(this)
        val setting = amap!!.uiSettings
        setting.isMyLocationButtonEnabled = true
        amap?.isMyLocationEnabled = true
        mLocationClient= AMapLocationClient(context)
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
    }

    private fun addMark(latLng: LatLng){
        val option = MarkerOptions()
            .position(latLng)
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
           val p = Position(country,province,city,district,streetNum,latitude,longitude)
           binding.position = p
           if (isFirst){
               amap?.moveCamera(CameraUpdateFactory.zoomTo(17f));
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
      //  binding.scrollView.smoothScrollTo(100,-800)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item?.onNavDestinationSelected(Navigation.findNavController(binding.root))?:false||super.onOptionsItemSelected(item)
    }
}
