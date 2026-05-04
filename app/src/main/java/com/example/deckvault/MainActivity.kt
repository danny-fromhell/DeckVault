package com.example.deckvault

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.deckvault.core.FragmentCommunicator

class MainActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var loadingScreen: View
    private lateinit var lottieLoading: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingScreen = findViewById(R.id.loadingScreen)
        lottieLoading = findViewById(R.id.lottieLoading)

        // Loader inicial solo una vez
        showInitialLoader()
    }

    private fun showInitialLoader() {
        manageLoader(true)

        loadingScreen.postDelayed({
            manageLoader(false)
        }, 2000)
    }

    override fun manageLoader(isVisible: Boolean) {
        loadingScreen.visibility = if (isVisible) View.VISIBLE else View.GONE

        if (isVisible) {
            if (!lottieLoading.isAnimating) {
                lottieLoading.playAnimation()
            }
        } else {
            lottieLoading.cancelAnimation()
        }
    }
}