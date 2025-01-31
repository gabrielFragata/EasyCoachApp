package br.com.fiap.easycoachapp.domain.usecases.session

import br.com.fiap.easycoachapp.domain.entities.*
import br.com.fiap.easycoachapp.domain.helpers.DomainError

interface CreateSessionContract {
    fun execute(
        session: SessionEntity,
        onSuccessListener: () -> Unit,
        onFailureListener: (DomainError) -> Unit)
}