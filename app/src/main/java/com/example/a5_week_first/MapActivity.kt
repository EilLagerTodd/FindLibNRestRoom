package com.example.a5_week_first

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.a5_week_first.databinding.ActivityMapBinding
import com.kakao.sdk.user.UserApiClient

class MapActivity : AppCompatActivity() {

    lateinit private var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()


        UserApiClient.instance.me { user, error ->
            binding.nickname.text = "${user?.kakaoAccount?.profile?.nickname}" + "\n님 환영합니다"

           var test = user?.kakaoAccount?.profile?.profileImageUrl

            Glide.with(this)
                .load("$test")
                .circleCrop()
                .into(binding.profile)
        }

        binding.mapbtn.setOnClickListener {
            val intent_maps = Intent(this, MapsActivity::class.java)
            startActivity(intent_maps.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        binding.gymapBtn.setOnClickListener {
            val intent_gymap = Intent(this, GyMapsActivity::class.java)
            startActivity(intent_gymap.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        binding.logoutBtn.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(this, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
            }
        }

        binding.removeBtn.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(this, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }
    }
}




