package handh.app.app

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware

class AppLoader : Application(), KodeinAware {

    override val kodein = Kodein {

    }
}