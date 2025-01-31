package br.com.fiap.easycoachapp.main.di

import br.com.fiap.easycoachapp.data.coach.useCases.GetCurrentCoach
import br.com.fiap.easycoachapp.data.login.useCases.CheckUserIsAuthenticated
import br.com.fiap.easycoachapp.data.login.useCases.DoLogin
import br.com.fiap.easycoachapp.data.session.useCases.DeleteSession
import br.com.fiap.easycoachapp.data.signUp.useCases.DoSignUp
import br.com.fiap.easycoachapp.viewModel.login.LoginContract
import br.com.fiap.easycoachapp.viewModel.login.LoginViewModel
import br.com.fiap.easycoachapp.viewModel.schedule.ScheduleContract
import br.com.fiap.easycoachapp.viewModel.schedule.ScheduleViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

object ViewModelModules {
    val modules = module {
        viewModel { (contract: ScheduleContract) ->
            ScheduleViewModel(
                contract,
                get<GetCurrentCoach>(),
                get<DeleteSession>()
            )}

        viewModel { (contract: LoginContract) ->
            LoginViewModel(
                contract,
                get<CheckUserIsAuthenticated>(),
                get<DoLogin>(),
                get<DoSignUp>()
            )
        }
    }
}