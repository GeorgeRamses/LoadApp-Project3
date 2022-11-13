package com.udacity

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var fileNameTV: TextView
    lateinit var statusTV: TextView
    lateinit var okButton: Button
    lateinit var motionLayout: MotionLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        fileNameTV = findViewById(R.id.file_name_tv)
        motionLayout = findViewById(R.id.motion_layout)
        statusTV = findViewById(R.id.status_tv)
        okButton = findViewById(R.id.btn_ok)
fadeButton()
        object : CountDownTimer(3000, 100) {
            override fun onTick(time: Long) {
                motionLayout.progress += 0.1f
            }

            override fun onFinish() {

            }

        }.start()

        fileNameTV.text = intent.getStringExtra("fileName")
        val status = intent.getStringExtra("status")
        if (status == "Failed") statusTV.setTextColor(Color.RED) else statusTV.setTextColor(this.getColor(R.color.colorPrimaryDark))

        statusTV.text = status

        okButton.setOnClickListener {
            finish()
        }

    }

    private fun fadeButton(){
        val animator=ObjectAnimator.ofFloat(okButton,"alpha",1f,0.5f)
        animator.reverse()
        animator.repeatCount=10
        animator.duration=1000
        animator.start()
    }


}
