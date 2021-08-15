package ru.givemesomecoffee.hakaton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var confirmCheckButton: Button? = null
    private var hostFieldView: TextInputLayout? = null
    private var hostFieldInput: TextInputEditText? = null
    private var result: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hostFieldView = findViewById(R.id.host_field)
        hostFieldInput = findViewById(R.id.host_input_field)
        result = findViewById(R.id.textView)
        confirmCheckButton = findViewById(R.id.host_check_confirm_button)
        confirmCheckButton?.setOnClickListener {
            result?.text = null
            if (validateUserName()) checkHost(hostFieldView?.editText?.text.toString())
        }
        hostFieldInput!!.doOnTextChanged { text, start, before, count -> validateUserName() }
    }

    private fun validateUserName(): Boolean {
        var validated = false
        val name = hostFieldView?.editText?.text.toString().trim()
        when {
            name.isEmpty() -> showError("Поле не может быть пустым")
            name.contains(" ") -> showError("В адресе не может быть пробелов")
            !Patterns.WEB_URL.matcher(name).matches() -> showError("Такого хоста не существует")
            else -> validated = confirmValidation()
        }
        return validated
    }

    private fun confirmValidation(): Boolean {
        hostFieldView?.error = null
        return true
    }

    private fun showError(error: String) {
        hostFieldView?.error = error
    }

    private fun checkHost(host: String) {
        if (validateUserName()) {
            lifecycleScope.launch {
                result?.text = "kzkz.ru - html страница"
               val listResult = HostApi.retrofitService.getHostType()

            }
        }
    }
}