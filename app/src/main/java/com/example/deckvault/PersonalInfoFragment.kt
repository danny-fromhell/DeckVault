package com.example.deckvault

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deckvault.databinding.FragmentPersonalInfoBinding
import java.util.Calendar

class PersonalInfoFragment : Fragment() {

    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etFechaNacimiento.apply {
            keyListener = null
            isFocusable = false
            isClickable = true
            setOnClickListener {
                mostrarDatePicker()
            }
        }

        binding.btnFinalizar.setOnClickListener {
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
                binding.etFechaNacimiento.setText(fecha)
            },
            anio,
            mes,
            dia
        )

        datePickerDialog.datePicker.maxDate = calendario.timeInMillis
        datePickerDialog.show()
    }

    private fun validarCampos(): Boolean {
        val nombre = binding.etNombre.text?.toString()?.trim().orEmpty()
        val primerApellido = binding.etPrimerApellido.text?.toString()?.trim().orEmpty()
        val segundoApellido = binding.etSegundoApellido.text?.toString()?.trim().orEmpty()
        val telefono = binding.etTelefono.text?.toString()?.trim().orEmpty()
        val fechaNacimiento = binding.etFechaNacimiento.text?.toString()?.trim().orEmpty()

        if (nombre.isEmpty()) {
            binding.etNombre.error = "Ingresa tu nombre"
            binding.etNombre.requestFocus()
            return false
        }

        if (primerApellido.isEmpty()) {
            binding.etPrimerApellido.error = "Ingresa tu primer apellido"
            binding.etPrimerApellido.requestFocus()
            return false
        }

        if (segundoApellido.isEmpty()) {
            binding.etSegundoApellido.error = "Ingresa tu segundo apellido"
            binding.etSegundoApellido.requestFocus()
            return false
        }

        if (telefono.isEmpty()) {
            binding.etTelefono.error = "Ingresa tu teléfono"
            binding.etTelefono.requestFocus()
            return false
        }

        if (telefono.length != 10 || !telefono.all { it.isDigit() }) {
            binding.etTelefono.error = "Debe tener 10 dígitos"
            binding.etTelefono.requestFocus()
            return false
        }

        if (fechaNacimiento.isEmpty()) {
            binding.etFechaNacimiento.error = "Selecciona tu fecha de nacimiento"
            binding.etFechaNacimiento.requestFocus()
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}