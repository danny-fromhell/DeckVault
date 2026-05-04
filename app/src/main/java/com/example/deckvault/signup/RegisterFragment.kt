package com.example.deckvault.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.deckvault.R
import com.example.deckvault.core.FragmentCommunicator
import com.example.deckvault.core.ResponseService
import com.example.deckvault.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    private var communicator: FragmentCommunicator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = context as? FragmentCommunicator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeRegisterState()
    }

    private fun setupListeners() {

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            viewModel.requestSignUp(
                email = email,
                password = password,
                confirmPassword = password // temporal
            )
        }

        binding.etEmail.addTextChangedListener {
            binding.tilEmail.error = null
        }

        binding.etPassword.addTextChangedListener {
            binding.tilPassword.error = null
        }
    }

    private fun observeRegisterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { state ->
                    when (state) {

                        is ResponseService.Loading -> {
                            communicator?.manageLoader(true)
                        }

                        is ResponseService.Success -> {
                            communicator?.manageLoader(false)

                            Toast.makeText(
                                requireContext(),
                                "Registro exitoso",
                                Toast.LENGTH_SHORT
                            ).show()

                            findNavController().navigate(
                                R.id.action_registerFragment_to_personalInfoFragment
                            )

                            viewModel.resetState()
                        }

                        is ResponseService.Error -> {
                            communicator?.manageLoader(false)

                            Toast.makeText(
                                requireContext(),
                                state.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            viewModel.resetState()
                        }

                        null -> Unit
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        communicator?.manageLoader(false)
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        communicator = null
    }
}