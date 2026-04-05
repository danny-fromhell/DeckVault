package com.example.deckvault

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.util.Calendar

class PersonalInfoFragment : Fragment() {

    private lateinit var etNombre: EditText
    private lateinit var etPrimerApellido: EditText
    private lateinit var etSegundoApellido: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etFechaNacimiento: EditText
    private lateinit var btnFinalizar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etNombre = view.findViewById(R.id.etNombre)
        etPrimerApellido = view.findViewById(R.id.etPrimerApellido)
        etSegundoApellido = view.findViewById(R.id.etSegundoApellido)
        etTelefono = view.findViewById(R.id.etTelefono)
        etFechaNacimiento = view.findViewById(R.id.etFechaNacimiento)
        btnFinalizar = view.findViewById(R.id.btnFinalizar)

        etFechaNacimiento.setOnClickListener {
            mostrarDatePicker()
        }

        btnFinalizar.setOnClickListener {
            if (validarCampos()) {
                Toast.makeText(requireContext(), "Registro completado", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_personalInfoFragment_to_loginFragment)
            }
        }
    }

    private fun mostrarDatePicker() {
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val fecha = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                etFechaNacimiento.setText(fecha)
            },
            anio,
            mes,
            dia
        )

        datePickerDialog.show()
    }

    private fun validarCampos(): Boolean {
        val nombre = etNombre.text.toString().trim()
        val primerApellido = etPrimerApellido.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val fechaNacimiento = etFechaNacimiento.text.toString().trim()

        if (nombre.isEmpty()) {
            etNombre.error = "Ingresa tu nombre"
            etNombre.requestFocus()
            return false
        }

        if (primerApellido.isEmpty()) {
            etPrimerApellido.error = "Ingresa tu primer apellido"
            etPrimerApellido.requestFocus()
            return false
        }

        if (telefono.isEmpty()) {
            etTelefono.error = "Ingresa tu teléfono"
            etTelefono.requestFocus()
            return false
        }

        if (telefono.length < 10) {
            etTelefono.error = "Debe tener 10 dígitos"
            etTelefono.requestFocus()
            return false
        }

        if (fechaNacimiento.isEmpty()) {
            etFechaNacimiento.error = "Selecciona tu fecha de nacimiento"
            etFechaNacimiento.requestFocus()
            return false
        }

        return true
    }
}