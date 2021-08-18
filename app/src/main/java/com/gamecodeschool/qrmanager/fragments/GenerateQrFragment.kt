package com.gamecodeschool.qrmanager.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.gamecodeschool.qrmanager.qrgenerator.QrGenerator
import com.gamecodeschool.qrmanager.R
import kotlinx.android.synthetic.main.fragment_generateqr.*

class GenerateQrFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generateqr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textToEncode.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val text = s.toString()
                if (text.length > 0) (qrImgView as ImageView).setImageBitmap(
                    QrGenerator.encodeText(text)
                ) else (qrImgView as ImageView).setImageResource(0)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

}