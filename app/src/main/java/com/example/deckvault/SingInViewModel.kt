package com.example.deckvault

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckvault.core.AuthRepository
import com.example.deckvault.core.ResponseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _signInState = MutableStateFlow<ResponseService<Boolean>?>(null)
    val signInState: StateFlow<ResponseService<Boolean>?> = _signInState

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

    fun requestLogin(email: String, password: String) {
        val emailError = validateEmail(email)
        val passwordError = validatePassword(password)

        if (emailError != null) {
            _signInState.value = ResponseService.Error(emailError)
            return
        }

        if (passwordError != null) {
            _signInState.value = ResponseService.Error(passwordError)
            return
        }

        viewModelScope.launch {
            _signInState.value = ResponseService.Loading
            _signInState.value = repository.requestLogin(email, password)
        }
    }

    fun resetState() {
        _signInState.value = null
    }
}