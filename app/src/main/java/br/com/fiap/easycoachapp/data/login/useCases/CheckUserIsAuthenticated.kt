package br.com.fiap.easycoachapp.data.login.useCases

import br.com.fiap.easycoachapp.domain.usecases.login.CheckUserIsAuthenticatedContract
import com.google.firebase.auth.FirebaseAuth


class CheckUserIsAuthenticated (
    private val auth: FirebaseAuth
    ) : CheckUserIsAuthenticatedContract {
    override fun execute(): Boolean {
        return auth.currentUser != null
    }
}