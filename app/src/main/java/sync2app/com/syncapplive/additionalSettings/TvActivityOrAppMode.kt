package sync2app.com.syncapplive.additionalSettings

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.databinding.ActivityTvOrAppModeBinding
import java.io.File

class TvActivityOrAppMode : AppCompatActivity() {
    private lateinit var binding: ActivityTvOrAppModeBinding

    private val multiplePermissionId = 14
    private val multiplePermissionNameList = if (Build.VERSION.SDK_INT >= 33) {
        arrayListOf(
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.READ_MEDIA_IMAGES

        )
    } else {
        arrayListOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    }


    private var navigateTVMode = false;
    private var navigateAppMolde = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvOrAppModeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {


         ///   testFireBaseCarshReport()

            textAppMode.setOnClickListener {
                navigateAppMolde = true
                if (checkMultiplePermission()) {
                    doOperation()
                }

            }

            textTvMode.setOnClickListener {
                navigateTVMode = true
                if (checkMultiplePermission()) {
                    doOperation()
                }
            }


        }


    }

    private fun testFireBaseCarshReport() {
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)

        // Creates a button that mimics a crash when pressed

        binding.textView2.text = "Test Crash"
        binding.textView2.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(binding.textView2, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))





    }


    private fun doOperation() {
        val sharedBiometric: SharedPreferences = applicationContext.getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)
        val editor = sharedBiometric.edit()

        if (navigateAppMolde == true){
            startActivity(Intent(applicationContext, RequiredBioActivity::class.java))
            finish()
            editor.putString(Constants.MY_TV_OR_APP_MODE, Constants.App_Mode)
            editor.putString(Constants.FIRST_TIME_APP_START, Constants.FIRST_TIME_APP_START)
            editor.apply()
        }

        if (navigateTVMode == true){
            startActivity(Intent(applicationContext, ReSyncActivity::class.java))
            finish()
            editor.putString(Constants.MY_TV_OR_APP_MODE, Constants.TV_Mode)
            editor.putString(Constants.FIRST_TIME_APP_START, Constants.FIRST_TIME_APP_START)
            editor.apply()

        }


        createATestFolder()

    }

    private fun createATestFolder() {


        val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/TestOffline/1234"

        val path = File(directoryPath)

        if (!path.exists()) {
            path.mkdirs()
        }


    }


    private fun checkMultiplePermission(): Boolean {
        val listPermissionNeeded = arrayListOf<String>()
        for (permission in multiplePermissionNameList) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(permission)
            }
        }
        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionNeeded.toTypedArray(),
                multiplePermissionId
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == multiplePermissionId) {
            if (grantResults.isNotEmpty()) {
                var isGrant = true
                for (element in grantResults) {
                    if (element == PackageManager.PERMISSION_DENIED) {
                        isGrant = false
                    }
                }
                if (isGrant) {
                    // here all permission granted successfully
                    doOperation()
                } else {
                    var someDenied = false
                    for (permission in permissions) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                permission
                            )
                        ) {
                            if (ActivityCompat.checkSelfPermission(
                                    this,
                                    permission
                                ) == PackageManager.PERMISSION_DENIED
                            ) {
                                someDenied = true
                            }
                        }
                    }

                    if (someDenied) {
                        showPermissionDeniedDialog()

                    } else {
                        showPermissionDeniedDialog()
                    }


                }
            }
        }
    }


    private fun showPermissionDeniedDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setTitle("Permission Required")
        builder.setMessage("Please grant the required permissions in the app settings to proceed.")
        builder.setPositiveButton("Go to Settings") { dialog: DialogInterface?, which: Int ->
            openAppSettings()
            dialog?.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
            showToastMessage("Permission Denied!")
            //  activity.finish()
        }
        builder.show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", this.packageName, null)
        intent.data = uri
        startActivity(intent)
    }


    private fun showToastMessage(messages: String) {

        try {
            Toast.makeText(applicationContext, messages, Toast.LENGTH_SHORT).show()
        } catch (_: Exception) {
        }
    }





    override fun onResume() {
        super.onResume()

        val sharedBiometric: SharedPreferences = applicationContext.getSharedPreferences(Constants.SHARED_BIOMETRIC, MODE_PRIVATE)

        val first_time_app_start = sharedBiometric.getString(Constants.FIRST_TIME_APP_START, "")

        if (first_time_app_start.equals(Constants.FIRST_TIME_APP_START)){
            startActivity(Intent(applicationContext, RequiredBioActivity::class.java))
            finish()
        }

        //   loadOffkineWebviewPage()

    }









    /*    private fun loadOffkineWebviewPage() {
            binding.apply {
                val Syn2AppLive = "Syn2AppLive"
                val filename = "index.html"
                val unzipManual = "/CLO/DE_MO_2021000/Offline_app/"

                val folderToExtractTo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + Syn2AppLive + unzipManual

                val filePath = "file://$folderToExtractTo$filename"

                val destinationFolder = File(folderToExtractTo)
                if (destinationFolder.exists()) {
                    Log.d("PowellFilePath", "File Path: $filePath")

                    myWebview.webViewClient = WebViewClient()
                    myWebview.settings.javaScriptEnabled = true
                    myWebview.settings.setSupportZoom(true)
                    myWebview.settings.allowFileAccess = true
                    myWebview.settings.allowContentAccess = true
                    myWebview.settings.mediaPlaybackRequiresUserGesture  = true
                    myWebview.settings.setSupportMultipleWindows(true)
                    myWebview.loadUrl(filePath)
                } else {
                 //   Toast.makeText(applicationContext, "No index,html file found", Toast.LENGTH_SHORT).show()
                    Log.d("PowellFilePath", "No file Found..")

                }

            }
        }*/

}