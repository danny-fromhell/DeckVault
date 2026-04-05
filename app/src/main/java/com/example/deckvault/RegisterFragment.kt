package com.example.deckvault

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnContinuar = view.findViewById<Button>(R.id.btnContinuar)
        val tvIniciarSesion = view.findViewById<TextView>(R.id.tvIniciarSesion)

        btnContinuar.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        tvIniciarSesion.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_personalInfoFragment)
        }
    }
}