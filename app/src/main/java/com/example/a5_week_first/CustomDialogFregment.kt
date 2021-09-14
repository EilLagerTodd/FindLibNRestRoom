package com.example.a5_week_first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.a5_week_first.databinding.ActivityDialogBinding



class CustomDialogFregment : DialogFragment() {
    lateinit private var binding: ActivityDialogBinding

    private var name: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      name = arguments?.getString("name")!!

      //title = GyMapsActivity()?.intent?.getStringExtra("name")
      //Log.d("name", "$title")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityDialogBinding.inflate(layoutInflater)
        binding.textView.text = name.toString()
        binding.button.setOnClickListener {
            this.dismiss()
        }
        return binding.root
    }
}