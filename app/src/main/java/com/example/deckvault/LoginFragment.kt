package com.example.deckvault

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvRegistrarse = view.findViewById<TextView>(R.id.tvRegistrarse)
        val tvForgotPassword = view.findViewById<TextView>(R.id.tvForgotPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        tvRegistrarse.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_personalInfoFragment)
        }
    }
}