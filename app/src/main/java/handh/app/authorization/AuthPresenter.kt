package handh.app.authorization

import handh.app.weatherModule.WeatherModule
import io.reactivex.disposables.Disposable
import java.net.UnknownHostException

class AuthPresenter(private val view: View, private val weatherModule: WeatherModule) : Presenter {

    private var email = ""
    private var password = ""

    private var weatherRequest: Disposable? = null

    override fun onEmailChanged(email: String) {
        this.email = email
        isEmailValid(email)
    }

    override fun onPasswordChanged(password: String) {
        this.password = password
    }

    override fun onKeyboardImeAction() = tryToEnter()

    override fun onPasswordHintClick() = view.showPasswordHint()
    override fun onEnterClick() = tryToEnter()
    override fun onCreateClick() = view.navigateToRegistration()
    override fun onBackClick() = view.showConfirmCloseDialog { view.closeView() }

    private fun tryToEnter() {
        when {
            !isEmailValid(email) -> view.showEmailNotValidMessage()
            !isPasswordValid(password) -> view.showPasswordNotValidMessage()
            else -> enter(email, password)
        }
    }

    private fun isEmailValid(email: String) = email.matches("^(.+)@(.+)\$".toRegex())
    private fun isPasswordValid(password: String): Boolean {
        return password.toCharArray().let { array ->
            val min6chars = array.size >= 6
            var hasUpperLetter = false
            var hasLowerLetter = false
            var hasDigit = false

            array.forEach { char ->
                when {
                    char.isUpperCase() -> hasUpperLetter = true
                    char.isLowerCase() -> hasLowerLetter = true
                    char.isDigit() -> hasDigit = true
                }
            }

            min6chars && hasUpperLetter && hasLowerLetter && hasDigit
        }
    }


    private fun enter(email: String, password: String) {
        weatherRequest?.dispose()
        weatherRequest = weatherModule.requestWeatherSummary()
                .doOnSubscribe { view.showLoadingProgress() }
                .doAfterTerminate { view.hideLoadingProgress() }
                .subscribe(
                        { summary -> view.showSummary(summary) },
                        { throwable -> if (throwable is UnknownHostException) view.showNoInternetConnectionMessage() }
                )
    }
}