package com.ticktackapps.encryption

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.drm.ProcessedData
import android.os.Bundle
import android.os.Process
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var isEncrypt : SwitchCompat
    private lateinit var key : EditText
    private lateinit var data : EditText
    private lateinit var processedData: TextView
    private lateinit var copyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        isEncrypt = findViewById(R.id.isencrypt)
        key = findViewById(R.id.enterkey)
        data = findViewById(R.id.enterdata)
        processedData = findViewById(R.id.getdata)
        copyButton = findViewById(R.id.copy)

        copyButton.setOnClickListener {

            val textToCopy = processedData.text.toString()
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("copy_label", textToCopy)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "Text copied!", Toast.LENGTH_SHORT).show()

        }

        key.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (key.text.toString() != ""){

                    processedData.setText(encrypt(key.text.toString(),data.text.reversed().toString(),isEncrypt.isChecked))

                }else{
                    processedData.setText("")
                }
            }

        })

        data.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                if (key.text.toString() != ""){

                    processedData.setText(encrypt(key.text.toString(),data.text.reversed().toString(),isEncrypt.isChecked))

                }else{
                    processedData.setText("")
                }


            }
        })

        isEncrypt.setOnClickListener {

            if (key.text.toString() != ""){

                processedData.setText(encrypt(key.text.toString(),data.text.reversed().toString(),isEncrypt.isChecked))

            }else{
                processedData.setText("")
            }

        }

    }






    fun encrypt(key:String, data:String, isEncrypt:Boolean):String{
        var indice = listOf(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
        var keylis = mutableListOf<Char>()
        for(i in key){
            keylis.add(i)
        }
        var final = ""
        for(i in 0..data.length-1){
            if(isEncrypt){
                final += indice.get((indice.indexOf(data[i])+indice.indexOf(keylis[i%keylis.size]))%indice.size).toString()
            }else{
                final += indice.get((indice.indexOf(data[i])-indice.indexOf(keylis[i%keylis.size])+27)%indice.size).toString()
            }
        }
        return final.reversed().toString()
    }

}