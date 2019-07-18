package com.pawel.myapplication

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private lateinit var svBarcode: SurfaceView
    private lateinit var tvBarcode: TextView

    private lateinit var detector: BarcodeDetector
    private lateinit var cameraSource: CameraSource


    private lateinit var barcodeRegistry : Map<String, String>


    //  S E T U P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        barcodeRegistry = mapOf("11274524319022" to "1", "47852967365628" to "2","98357673456313" to "3","76579296745464" to "4",
            "56279524312558" to "5", "47839688278570" to "6" ,"35274786746754" to "7")

        svBarcode = findViewById(R.id.sv_barcode)
        tvBarcode = findViewById(R.id.tv_barcode)

        detector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ITF).build()
        detector.setProcessor(object : Detector.Processor<Barcode>{
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                val barcodes = detections?.detectedItems
                if(barcodes!!.size() > 0){
                    tvBarcode.post{
                        tvBarcode.text = barcodes.valueAt(0).displayValue
                        processBarcode(barcodes.valueAt(0))
                    }
                }
            }
        })

        cameraSource = CameraSource.Builder(this, detector).setRequestedPreviewSize(1024,768)
            .setRequestedFps(25f).setAutoFocusEnabled(true).build()

        svBarcode.holder.addCallback(object : SurfaceHolder.Callback2{
            override fun surfaceRedrawNeeded(holder: SurfaceHolder?) {
            }

            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                cameraSource.stop()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                if(ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                    cameraSource.start(holder)
                else ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.CAMERA), 123)
            }
        })
}

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 123){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                cameraSource.start(svBarcode.holder)
            else Toast.makeText(this, "Scanner wont work without permission", Toast.LENGTH_SHORT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detector.release()
        cameraSource.stop()
        cameraSource.release()
    }




    // L O G I K

   fun processBarcode(barcode : Barcode) {
       if(barcodeRegistry.containsKey(barcode.displayValue)) {
           val dummyCode = barcodeRegistry.get(barcode.displayValue)
           sendColorcode(dummyCode!!)

       }
   }

    fun sendColorcode(colorCode : String){
        // log for testing:
        Log.d("readed colorCode", colorCode)

        // put Kotlin code here or create new Java class and call it here
        //
    }
}
//