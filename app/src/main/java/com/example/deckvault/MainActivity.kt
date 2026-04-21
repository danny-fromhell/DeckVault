package com.example.deckvault

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.deckvault.core.FragmentCommunicator

class MainActivity : AppCompatActivity(), FragmentCommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun manageLoader(isVisible: Boolean) {
        val loadingScreen = findViewById<View>(R.id.loadingScreen)
        val lottieLoading = findViewById<LottieAnimationView>(R.id.lottieLoading)

        loadingScreen.visibility = if (isVisible) View.VISIBLE else View.GONE

        if (isVisible) {
            lottieLoading.playAnimation()
        } else {
            lottieLoading.cancelAnimation()
        }
    }
}