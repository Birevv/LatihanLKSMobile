package id.sch.smkn1bantul.latihanlks2.utils

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.Locale


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun isEmailValid(email: String): Boolean {
    return if (email.contains('@')) {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    } else {
        false
    }
}

fun isPasswordValid(password: String): Boolean {
    return password.length >= 8 && password.contains(Regex("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)"))
}

fun isPhoneValid(phone: String): Boolean {
    return phone.startsWith('0') && phone.length in 8..13
}

fun ImageView.loadImageWithUri(uri: Uri?, centerCrop: Boolean) {
    if (centerCrop) Glide.with(this).load(uri).centerCrop().into(this)
    else Glide.with(this).load(uri).into(this)
}

fun getRupiah(price: Int): String {
    val locale = Locale("id", "ID")
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)

    return currencyFormatter.format(price)
}