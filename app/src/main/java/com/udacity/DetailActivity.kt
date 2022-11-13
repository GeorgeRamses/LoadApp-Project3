package com.udacity

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var fileNameTV: TextView
    lateinit var statusTV: TextView
    lateinit var okButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        fileNameTV = findViewById(R.id.file_name_tv)
        statusTV = findViewById(R.id.status_tv)
        okButton = findViewById(R.id.btn_ok)

        fileNameTV.text = intent.getStringExtra("fileName")
        val status = intent.getStringExtra("status")
        if (status == "Failed") statusTV.setTextColor(Color.RED) else statusTV.setTextColor(this.getColor(R.color.colorPrimaryDark))

        statusTV.text = status

        okButton.setOnClickListener {
            finish()
        }

    }

}
