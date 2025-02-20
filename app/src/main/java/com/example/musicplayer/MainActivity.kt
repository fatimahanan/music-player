package com.example.musicplayer

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.musicplayerservice.MusicServiceAIDL


class MainActivity : AppCompatActivity() {
    private var musicService : MusicServiceAIDL? = null
    private var serviceBound=false
    //anonymous class to implement service connection interface
    private var serviceConnection= object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            musicService=MusicServiceAIDL.Stub.asInterface(service)   //using generated stub class to bind service
            serviceBound=true
            val fragment=MusicPlayerFragment.newInstance()
            fragment.setMusicService(musicService)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            Log.d("MainActivity", "Service Connected!")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService=null
            serviceBound=false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(false);
        supportActionBar?.hide()

        val intent = Intent("com.example.musicplayerservice.MusicPlayerService")
        intent.setPackage("com.example.musicplayerservice")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(serviceBound){
            unbindService(serviceConnection)
            serviceBound=false
        }
    }

}