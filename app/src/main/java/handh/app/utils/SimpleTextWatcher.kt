package handh.app.utils

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(private val onChanged: (String) -> (Unit)) : TextWatcher {
    override fun afterTextChanged(editable: Editable?) = onChanged(editable.toString())

    override fun beforeTextChanged(editable: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(editable: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}