package handh.app.authorization

interface View {
    fun showEmailNotValidMessage()
    fun showPasswordNotValidMessage()
    fun showNoInternetConnectionMessage()
    fun showPasswordHint()
    fun showSummary(summary: String)

    fun navigateToRegistration()

    fun showConfirmCloseDialog(onConfirm: () -> Unit)

    fun showLoadingProgress()
    fun hideLoadingProgress()

    fun closeView()
}

interface Presenter {
    fun onEmailChanged(email: String)
    fun onPasswordChanged(password: String)

    fun onKeyboardImeAction()

    fun onPasswordHintClick()
    fun onEnterClick()
    fun onCreateClick()
    fun onBackClick()
}