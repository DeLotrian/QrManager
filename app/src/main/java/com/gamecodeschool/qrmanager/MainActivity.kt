package com.gamecodeschool.qrmanager


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gamecodeschool.qrmanager.fragments.GenerateQrFragment
import com.gamecodeschool.qrmanager.fragments.ScanQRFragment
import com.gamecodeschool.qrmanager.fragments.adapters.ViewPagerAdapter
import com.gamecodeschool.qrmanager.websocket.ServerData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_scanqr.*


class MainActivity : AppCompatActivity() {
    private val SETTINGS_CODE_ACTIVITY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpTabs()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Log.d("MenuSettings", "Settings Selected")
        val intent = Intent(this@MainActivity, SettingsActivity::class.java)
        if (ServerData.ipAddress!!.length > 0)
            intent.putExtra("IpAddress", ServerData.ipAddress)
        else intent.putExtra("IpAddress", "")
        if (ServerData.port!!.length > 0)
            intent.putExtra("Port", ServerData.port)
        else intent.putExtra("Port", "")
        startActivityForResult(intent, SETTINGS_CODE_ACTIVITY)
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SETTINGS_CODE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                ServerData.ipAddress = data?.getStringExtra("IpAddress")
                ServerData.port = data?.getStringExtra("Port")
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(ScanQRFragment(), "Scan")
        adapter.addFragment(GenerateQrFragment(), "Generate")

        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(PageChangeListener(this@MainActivity))
        tabs.setupWithViewPager(viewPager)

        appBarLayout.post(Runnable() {

            fun run() {
                var indicatorWidth = tabs.getWidth() / tabs.getTabCount()

                //Assign new width

                //Assign new width
                val indicatorParams = appBarLayout.getLayoutParams()
                indicatorParams.width = indicatorWidth
                appBarLayout.setLayoutParams(indicatorParams)
            }
        })
    }
}