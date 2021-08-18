package com.gamecodeschool.qrmanager

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        findViewById<EditText>(R.id.ipAddress).setText(intent.getStringExtra("IpAddress"))
        findViewById<EditText>(R.id.portNumber).setText(intent.getStringExtra("Port"))

        val buttonAnim: Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim)
        findViewById<Button>(R.id.ok_btn).setOnClickListener {v ->
            v.startAnimation(buttonAnim)
            var answerIntent: Intent = Intent()
            answerIntent.putExtra("IpAddress", findViewById<EditText>(R.id.ipAddress).text.toString())
            answerIntent.putExtra("Port", findViewById<EditText>(R.id.portNumber).text.toString())
            setResult(RESULT_OK, answerIntent)
            finish()
        }

        findViewById<Button>(R.id.cancel_btn).setOnClickListener {v ->
            v.startAnimation(buttonAnim)
            var answerIntent: Intent = Intent()
            setResult(RESULT_CANCELED, answerIntent)
            finish()
        }

    }


}