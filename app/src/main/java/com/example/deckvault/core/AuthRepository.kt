package com.example.deckvault.core

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await

class AuthRepository : Authentication {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun requestLogin(
        email: String,
        password: String
    ): ResponseService<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            ResponseService.Success(true)
        } catch (e: FirebaseAuthInvalidUserException) {
            ResponseService.Error("No existe una cuenta registrada con este correo")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            ResponseService.Error("Correo o contraseña incorrectos")
        } catch (e: Exception) {
            ResponseService.Error(e.message ?: "Error al iniciar sesión")
        }
    }

    override suspend fun requestSignUp(
        email: String,
        password: String
    ): ResponseService<Boolean> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            ResponseService.Success(true)
        } catch (e: FirebaseAuthUserCollisionException) {
            ResponseService.Error("Este correo ya está registrado, intenta con otro")
        } catch (e: FirebaseAuthWeakPasswordException) {
            ResponseService.Error("La contraseña es muy débil")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            ResponseService.Error("El correo no tiene un formato válido")
        } catch (e: Exception) {
            ResponseService.Error(e.message ?: "Error al registrar usuario")
        }
    }
}