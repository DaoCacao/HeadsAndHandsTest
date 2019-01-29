package handh.app.authorization

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import handh.app.R
import handh.app.utils.SimpleTextWatcher
import handh.app.weatherModule.AppWeatherModule
import handh.app.weatherModule.WeatherModule
import kotlinx.android.synthetic.main.activity_authorization.*

class AuthActivity : KodeinAppCompatActivity(), View {

    override fun provideOverridingModule() = Kodein.Module {
        bind<View>() with provider { this@AuthActivity }
        bind<Presenter>() with provider { AuthPresenter(instance(), instance()) }
        bind<WeatherModule>() with provider { AppWeatherModule() }
    }

    private val presenter: Presenter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { presenter.onBackClick() }

        editEmail.addTextChangedListener(SimpleTextWatcher { presenter.onEmailChanged(it) })

        editPassword.addTextChangedListener(SimpleTextWatcher { presenter.onPasswordChanged(it) })
        editPassword.setOnEditorActionListener { _, _, _ -> presenter.onKeyboardImeAction(); true }

        imageHint.setOnClickListener { presenter.onPasswordHintClick() }

        buttonEnter.setOnClickListener { presenter.onEnterClick() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_auth, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_create) presenter.onCreateClick()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = presenter.onBackClick()

    override fun showEmailNotValidMessage() = showSnackbar(R.string.wrong_email)
    override fun showPasswordNotValidMessage() = showSnackbar(R.string.wrong_password)
    override fun showNoInternetConnectionMessage() = showSnackbar(R.string.no_internet_connection)

    override fun showPasswordHint() {
        AlertDialog.Builder(this)
                .setMessage(R.string.password_validation_hint)
                .setPositiveButton(R.string.ok, null)
                .show()
    }
    override fun showSummary(summary: String) = showLongSnackbar(summary)

    override fun navigateToRegistration() = Toast.makeText(this, "Navigate to registration screen", Toast.LENGTH_SHORT).show() //TODO --> navigate to registration

    override fun showConfirmCloseDialog(onConfirm: () -> Unit) {
        AlertDialog.Builder(this)
                .setMessage(R.string.confirm_exit_title)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.exit, { _, _ -> onConfirm.invoke() })
                .show()
    }

    override fun showLoadingProgress() {
        progressLoading.visibility = android.view.View.VISIBLE
    }

    override fun hideLoadingProgress() {
        progressLoading.visibility = android.view.View.GONE
    }

    override fun closeView() = super.onBackPressed()

    private fun showSnackbar(@StringRes messageId: Int) = Snackbar.make(constraint, messageId, Snackbar.LENGTH_SHORT).show()
    private fun showLongSnackbar(message: String) = Snackbar.make(constraint, message, Snackbar.LENGTH_INDEFINITE).apply { setAction(R.string.ok, { dismiss() }) }.show()
}

