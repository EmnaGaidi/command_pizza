package com.gl4.commandpizza

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    lateinit var nom : TextInputLayout
    lateinit var prenom : TextInputLayout
    lateinit var adresse : TextInputLayout
    lateinit var radioGroup : RadioGroup
    lateinit var fromage : CheckBox
    lateinit var champignon : CheckBox
    lateinit var thon : CheckBox
    lateinit var size : String
    var ingredients : String = "Les ingrédients sont : "
    lateinit var sms_button : Button
    lateinit var email_button : Button
    lateinit var finalText : String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nom = findViewById(R.id.nom)
        prenom = findViewById(R.id.prenom)
        adresse = findViewById(R.id.adresse)
        radioGroup = findViewById(R.id.radioGroup)
        fromage = findViewById(R.id.fromage)
        champignon = findViewById(R.id.champignon)
        thon = findViewById(R.id.thon)
        sms_button = findViewById(R.id.sms_button)
        email_button = findViewById(R.id.email_button)


        if (fromage.isChecked){
            ingredients = ingredients +" fromage /"
        }
        if(champignon.isChecked){
            ingredients = ingredients + " champignon /"
        }
        if(thon.isChecked){
            ingredients = ingredients +" thon /"
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // checkedId contient l'ID du RadioButton sélectionné
            val selectedRadioButton: RadioButton = findViewById(checkedId)
            size = selectedRadioButton.text.toString()
            val nomText = nom.editText?.text.toString()
            val prenomText = prenom.editText?.text.toString()
            val adresseText = adresse.editText?.text.toString()
            finalText = "Monsieur/Madame "+nomText+" "+prenomText+" habite à "+adresseText+" à commander Pizza "+size+" "+ingredients

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Demandez la permission si elle n'est pas déjà accordée
            // arrayOf : C'est un tableau contenant les autorisations que vous demandez. Dans ce cas, il s'agit de l'autorisation de lecture du stockage externe.
            // 1 : C'est un code de demande qui vous permettra de gérer la réponse de l'utilisateur lorsque la permission est demandée. Vous utiliserez ce code de demande pour identifier la réponse lorsque l'utilisateur prend une décision.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                1
            )
        }
        sms_button.setOnClickListener{
            var phone = "00000"
            SendSMS(phone,finalText)
            showToast("SMS envoyé :"+finalText)

        }
        email_button.setOnClickListener {
            val subject = "commande pizza"
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:emnagaidii@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, finalText)
            }

            startActivity(intent)
        }
    }

    fun SendSMS(number: String,message:String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(number, null, message, null, null)
    }
    private fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}