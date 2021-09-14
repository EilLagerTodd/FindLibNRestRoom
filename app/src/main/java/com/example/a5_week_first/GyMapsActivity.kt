package com.example.a5_week_first

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.a5_week_first.TTdata.Parking
import com.example.a5_week_first.databinding.ActivityGyMapsBinding
import com.example.a5_week_first.databinding.ActivityGyMapsBinding.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GyMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGyMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.gymap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        loadtesting()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        googleMap.isMyLocationEnabled = true
    }

    fun loadtesting() {
        val retrofit = Retrofit.Builder()
            .baseUrl(GyOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val busOpenService = retrofit.create(PubltoltOpen::class.java)
        busOpenService
            .getTest(GyOpenApi.API_KEY)
            .enqueue(object : Callback<Parking> {
                override fun onFailure(call: Call<Parking>, t: Throwable) {
                    Toast.makeText(baseContext
                        , "서버에서 데이터를 가져올 수 없습니다."
                        , Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<Parking>, response: Response<Parking>) {
                    showtesting(response.body() as Parking)
                }
            })
    }

    fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.test, fragment)
        transaction.commit()
    }


    fun showtesting(testing: Parking) {
        val latLngBounds = LatLngBounds.Builder()
        for (lib in testing.GetParkInfo.row) {
           val bitmapdraw = resources.getDrawable(R.drawable.parking
           ) as BitmapDrawable
            val b = bitmapdraw.bitmap
            val smallMarker = Bitmap.createScaledBitmap(b, 70, 70, false)
            val position = LatLng(lib.LAT.toDouble(), lib.LNG.toDouble())
            val marker = MarkerOptions().position(position).title(lib.PARKING_NAME).icon(
                BitmapDescriptorFactory.fromBitmap(smallMarker))
            var obj = mMap.addMarker(marker)
            obj.tag = "주차장명 : "+lib.PARKING_NAME + "\n" + "주차장 주소 : "+lib.ADDR + "\n" + "주차장 번호 : " + lib.TEL + "\n" +"유/무료 : " + lib.PAY_NM
            mMap.setOnMarkerClickListener {
                if (it.tag != null) {
                    var name = it.tag as String
                    val bundle = Bundle()
                    bundle.putString("name", name)
                    val dialog = CustomDialogFregment()
                    dialog.arguments = bundle
                    dialog.show(supportFragmentManager,dialog.tag)

                   // Toast.makeText(this, "$name", Toast.LENGTH_SHORT).show()
                   // val intent_dia = Intent(this, CustomDialogFregment::class.java)
                   // intent_dia.putExtra("name",name)
                    //setDataAtFragment(CustomDialogFregment(), "실험 성공")
                   /* val dialog = CustomDialogFregment()
                    dialog.show(supportFragmentManager, dialog.tag)*/
                }
                true
            }
            latLngBounds.include(marker.position)
        }

        val bounds = latLngBounds.build()
        val padding = 0
        val updated = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.moveCamera(updated)
    }
}

