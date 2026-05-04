package com.example.deckvault.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckvault.core.AuthRepository
import com.example.deckvault.core.ResponseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _registerState = MutableStateFlow<ResponseService<Boolean>?>(null)
    val registerState: StateFlow<ResponseService<Boolean>?> = _registerState

    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "El correo es obligatorio"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Correo inválido"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "La contraseña es obligatoria"
            password.length < 6 -> "La contraseña debe tener mínimo 6 caracteres"
            else -> null
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isBlank() -> "Confirma tu contraseña"
            password != confirmPassword -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    fun requestSignUp(
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val emailError = validateEmail(email)
        val passwordError = validatePassword(password)
        val confirmPasswordError = validateConfirmPassword(password, confirmPassword)

        if (emailError != null) {
            _registerState.value = ResponseService.Error(emailError)
            return
        }

        if (passwordError != null) {
            _registerState.value = ResponseService.Error(passwordError)
            return
        }

        if (confirmPasswordError != null) {
            _registerState.value = ResponseService.Error(confirmPasswordError)
            return
        }

        viewModelScope.launch {
            _registerState.value = ResponseService.Loading
            _registerState.value = repository.requestSignUp(email, password)
        }
    }

    fun resetState() {
        _registerState.value = null
    }
}