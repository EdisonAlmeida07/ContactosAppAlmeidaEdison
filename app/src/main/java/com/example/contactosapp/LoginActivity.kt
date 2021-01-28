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
    // Inicializacion de variables

        buttonLogin = findViewById<Button>(R.id.buttonLogin)
        editTextTextEmailAddress = findViewById<EditText>(R.id.editTextTextEmailAddress)
        editTextTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        checkBoxRecordarme = findViewById<CheckBox>(R.id.checkBoxRecordarme)
        //Lectura de valores

        buttonLogin.setOnClickListener {
            if (!ValidarDatos())
                return@setOnClickListener

            if(checkBoxRecordarme.isChecked){
                val editor = sharedPref.edit()
                editor.putString(LOGIN_KEY,editTextTextEmailAddress.text.toString())
                editor.putString(PASSWORD_KEY,editTextTextPassword.text.toString())
                editor.commit()
            }
            else{
                val editor = sharedPref.edit()
                editor.putString(LOGIN_KEY,"")
                editor.putString(PASSWORD_KEY,"")
                editor.commit()
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