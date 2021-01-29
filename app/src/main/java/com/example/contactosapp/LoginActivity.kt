package com.example.contactosapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
class LoginActivity : AppCompatActivity() {

    lateinit var buttonLogin : Button
    lateinit var editTextTextEmailAddress : EditText
    lateinit var editTextTextPassword : EditText
    lateinit var checkBoxRecordarme :CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
//Lectura de valores de archivos de preferencias en caso que existan
        editTextTextEmailAddress.setText ( sharedPref.getString(LOGIN_KEY,"") )
        editTextTextPassword.setText ( sharedPref.getString(PASSWORD_KEY,"") )
        checkBoxRecordarme.isChecked = sharedPref.getBoolean(CHECK_KEY,false)

        buttonLogin.setOnClickListener {
            if (!ValidarDatos())
                return@setOnClickListener

            if(checkBoxRecordarme.isChecked){
                sharedPref
                        .edit()
                        .putString(LOGIN_KEY,editTextTextEmailAddress.text.toString())
                        .putString(PASSWORD_KEY,editTextTextPassword.text.toString())
                        .putBoolean(CHECK_KEY,true)
                        .apply()
            }
            else{
                val editor = sharedPref.edit()
                editor.putString(LOGIN_KEY,"")
                editor.putString(PASSWORD_KEY,"")
                editor.putBoolean(CHECK_KEY,false)
                editor.apply() // o commit()
            }
        }

    }
    fun ValidarDatos(): Boolean {
        fun CharSequence?.isValidEmail() =
                !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
        if (editTextTextEmailAddress.text.isNullOrEmpty()) {
            editTextTextEmailAddress.setError(getString(R.string.editTextTextEmailAddress_hint))
            editTextTextEmailAddress.requestFocus()
            return false
        }
        if (!editTextTextEmailAddress.text.isValidEmail()) {
            editTextTextEmailAddress.setError(getString(R.string.email_NoValido))
            editTextTextEmailAddress.requestFocus()
            return false
        }
        if (editTextTextPassword.text.isNullOrEmpty()) {
            editTextTextPassword.setError(getString(R.string.editTextPassword_hint))
            editTextTextPassword.requestFocus()
            return false
        }
        if (editTextTextPassword.text.length < MIN_PASSWORD_LENGTH) {
            editTextTextPassword.setError(getString(R.string.password_longitudNoValida))
            editTextTextPassword.requestFocus()
            return false
        }
        return true
    }
}