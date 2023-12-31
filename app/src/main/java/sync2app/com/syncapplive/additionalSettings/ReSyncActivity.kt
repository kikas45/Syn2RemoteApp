package sync2app.com.syncapplive.additionalSettings


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.DownloadManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import kotlinx.coroutines.launch
import sync2app.com.syncapplive.R
import sync2app.com.syncapplive.additionalSettings.DownloadsArray.list.DownlodPagger
import sync2app.com.syncapplive.additionalSettings.scanutil.ZipWorker
import sync2app.com.syncapplive.additionalSettings.urlchecks.checkUrlExistence
import sync2app.com.syncapplive.additionalSettings.urlchecks.isUrlValid
import sync2app.com.syncapplive.additionalSettings.utils.Constants
import sync2app.com.syncapplive.databinding.ActivityReSyncBinding
import sync2app.com.syncapplive.databinding.CustomContinueDownloadLayoutBinding
import sync2app.com.syncapplive.databinding.ProgressDialogLayoutBinding
import java.io.File
import java.util.Calendar


class ReSyncActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReSyncBinding

    private lateinit var customProgressDialog: Dialog


    private val baseUrl222 =
        "https://firebasestorage.googleapis.com/v0/b/vector-news-b5fcf.appspot.com/o/testAPKs%2FMyZip.zip?alt=media&token=5f890c03-d2d5-4f97-95c7-e39c8dc49c57"


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


    private var urlIndex =
        "https://cp.cloudappserver.co.uk/app_base/public/CLO/DE_MO_2021000/Zip/App.zip"
    private var urlIndexCPI =
        "https://cp.cloudappserver.co.uk/app_base/public/CLO/DE_MO_2021000/Zip/App.zip"
    private var urlIndexAPI =
        "https://firebasestorage.googleapis.com/v0/b/vector-news-b5fcf.appspot.com/o/testAPKs%2FMyZip.zip?alt=media&token=5f890c03-d2d5-4f97-95c7-e39c8dc49c57"


    private var isValid = false

    private var getUrlBasedOnSpinnerText = ""
    private var API_Server = "API-Cloud App Server"
    private var CP_server = "CP-Cloud App Server"


    private var Selected_time = "Selected time"

    private var rootfolder = "Downloads"


    private var getTimeDefined = ""
    private var timeMinuetesDefined = "Sync interval timer"
    private var timeMinuetes22 = "2 Minutes"
    private var timeMinuetes55 = "5 Minutes"
    private var timeMinuetes10 = "10 Minutes"
    private var timeMinuetes15 = "15 Minutes"
    private var timeMinuetes30 = "30 Minutes"
    private var timeMinuetes60 = "60 Minutes"
    private var timeMinuetes120 = "120 Minutes"
    private var timeMinuetes180 = "180 Minutes"
    private var timeMinuetes240 = "240 Minutes"


    private var getToatlFilePath = ""
    private var unzipManual = ""


    private val sharedBiometric: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SHARED_BIOMETRIC,
            Context.MODE_PRIVATE
        )
    }


    private val sharedP: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.MY_DOWNLOADER_CLASS,
            Context.MODE_PRIVATE
        )
    }


    private val savedDownloadPath: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            Constants.SAVE_M_DOWNLOAD_PATH,
            Context.MODE_PRIVATE
        )
    }



    var manager: DownloadManager? = null

    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private val myHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    var preferences: SharedPreferences? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (preferences!!.getBoolean("darktheme", false)) {
            setTheme(R.style.DarkThemeSettings)
        }

        binding = ActivityReSyncBinding.inflate(layoutInflater)
        setContentView(binding.root)
        manager = applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?


        binding.apply {

            val getSavedCLOImPutFiled = sharedP.getString(Constants.getSavedCLOImPutFiled, "")
            val getSaveSubFolderInPutFiled = sharedP.getString(Constants.getSaveSubFolderInPutFiled, "")
            val getSavedEditTextInputSynUrlZip = sharedP.getString(Constants.getSavedEditTextInputSynUrlZip, "")

            if (!getSavedCLOImPutFiled.isNullOrEmpty()){
                editTextCLOpath.setText(getSavedCLOImPutFiled)
            }

            if (!getSaveSubFolderInPutFiled.isNullOrEmpty()){
                editTextSubPathFolder.setText(getSaveSubFolderInPutFiled)
            }


            if (!getSavedEditTextInputSynUrlZip.isNullOrEmpty()){
                editTextInputSynUrlZip.setText(getSavedEditTextInputSynUrlZip)

            }

            closeBs.setOnClickListener {
                onBackPressed()
            }


            initViewTooggle()


            textTestConnectionAPPer.setOnClickListener {
                hideKeyBoard(binding.editTextInputSynUrlZip)
                try {
                    testConnectionSetup()
                } catch (_: Exception) {
                }
            }

            textTitle.setOnClickListener {
                deleteMySy2nAppliveFolder()
            }



            textDownloadZipSyncOrApiSyncNow.setOnClickListener {

                hideKeyBoard(binding.editTextInputSynUrlZip)
                if (checkMultiplePermission()) {
                    doOperation()
                }

            }
        }
    }

    private fun saveValuesForMySy2nAppliveFiolder() {
        val editor = sharedBiometric.edit()
        editor.putString(Constants.DELETE_SYN2APP_FOLDER, "DELETE_SYN2APP_FOLDER" )
        editor.apply()
    }


    private fun showCustomProgressDialog(message: String) {
        try {
            customProgressDialog = Dialog(this)
            val binding = ProgressDialogLayoutBinding.inflate(LayoutInflater.from(this))
            customProgressDialog.setContentView(binding.root)
            customProgressDialog.setCancelable(true)
            customProgressDialog.setCanceledOnTouchOutside(false)
            customProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            binding.textLoading.text = message

            customProgressDialog.show()
        } catch (_: Exception) {
        }
    }


    private fun testConnectionSetup() {
        binding.apply {
            val getFolderClo = editTextCLOpath.text.toString().trim()
            val getFolderSubpath = editTextSubPathFolder.text.toString().trim()

            val editor = sharedP.edit()
            if (isNetworkAvailable()) {
                if (!imagSwtichEnableManualOrNot.isChecked && isNetworkAvailable()) {
                    when (getUrlBasedOnSpinnerText) {
                        CP_server -> {
                            if (getFolderClo.isNotEmpty() && getFolderSubpath.isNotEmpty()) {
                                httpNetworkTester(getFolderClo, getFolderSubpath)
                                editor.putString(Constants.getSavedCLOImPutFiled,getFolderClo)
                                editor.putString(Constants.getSaveSubFolderInPutFiled,getFolderSubpath)
                                editor.apply()

                            } else {
                                editTextCLOpath.error = "Input a valid path e.g CLO"
                                editTextSubPathFolder.error = "Input a valid path e.g DE_MO_2021000"
                                showToastMessage("Fields can not be empty")
                            }
                        }

                        API_Server -> {
                            showToastMessage("No Logic For API Server Yet")
                        }

                    }

                }
                //// enf of the main if
            } else {
                showToastMessage("No Internet Connection")
            }



            /// when the button is checked
            if (isNetworkAvailable()) {
                if (imagSwtichEnableManualOrNot.isChecked && isNetworkAvailable()) {
                    when (getUrlBasedOnSpinnerText) {
                        CP_server -> {

                            val editInputUrl = editTextInputSynUrlZip.text.toString().trim()
                            if (editInputUrl.isNotEmpty() && isUrlValid(editInputUrl)) {
                                httpNetSingleUrlTest(editInputUrl)
                                editor.putString(Constants.getSavedEditTextInputSynUrlZip,editInputUrl)
                                editor.apply()
                            } else {
                                showToastMessage("Invalid url format")
                                binding.editTextInputSynUrlZip.error = "Invalid url format"
                            }

                        }

                        API_Server -> {
                            showToastMessage("No Logic For API Server Yet")
                        }

                    }
                }
            } else {
                showToastMessage("No Internet Connection")
            }

        }
    }


    private fun httpNetworkTester(getFolderClo: String, getFolderSubpath: String) {
        handler.postDelayed(Runnable {
            showCustomProgressDialog("Testing connection")


            val editor = sharedP.edit()

            if (binding.imagSwtichEnableSyncFromAPI.isChecked) {
                val baseUrl =
                    "https://cp.cloudappserver.co.uk/app_base/public/$getFolderClo/$getFolderSubpath/Zip/App.zip"
                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            showPopsForMyConnectionTest(getFolderClo, getFolderSubpath, "Successful")
                        } else {
                            showPopsForMyConnectionTest(getFolderClo, getFolderSubpath, "Failed!")
                        }
                    } finally {
                        customProgressDialog.dismiss()

                    }
                }

            } else {


                val baseUrl =
                    "https://cp.cloudappserver.co.uk/app_base/public/$getFolderClo/$getFolderSubpath/Api/update.csv"

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            showPopsForMyConnectionTest(getFolderClo, getFolderSubpath, "Successful")
                        } else {
                            showPopsForMyConnectionTest(getFolderClo, getFolderSubpath, "Failed!")
                        }
                    } finally {
                        customProgressDialog.dismiss()
                    }
                }


            }


        }, 300)
    }


    private fun httpNetSingleUrlTest(baseUrl33: String) {
        handler.postDelayed(Runnable {
            showCustomProgressDialog("Testing connection")



            val lastString = baseUrl33.substringAfterLast("/")
            val fileNameWithoutExtension = lastString.substringBeforeLast(".")

            lifecycleScope.launch {
                try {
                    val result = checkUrlExistence(baseUrl33)
                    if (result) {
                        showPopsForMyConnectionTest("CLO", fileNameWithoutExtension, "Successful")
                    } else {

                        showPopsForMyConnectionTest("CLO", fileNameWithoutExtension, "Failed!")
                    }
                } finally {
                    customProgressDialog.dismiss()
                }
            }

        }, 300)
    }


    private fun startZipExtractionWork(zipFilePath: String, destinationPath: String) {
        showToastMessage("Zip extraction started")
        val workRequest: WorkRequest =
            OneTimeWorkRequestBuilder<ZipWorker>()
                .setInputData(
                    workDataOf(
                        "zipFilePath" to zipFilePath,
                        "destinationPath" to destinationPath
                    )
                )
                .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }


    private fun funUnZipFile() {


        val getFolderClo = savedDownloadPath.getString("getFolderClo", "")
        val getFolderSubpath = savedDownloadPath.getString("getFolderSubpath", "")
        val Zip = savedDownloadPath.getString("Zip", "")
        val fileNamy = savedDownloadPath.getString("fileNamy", "")

        val finalFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip"
        val finalFolderPathDesired = "/$getFolderClo/$getFolderSubpath/Offline_app"



        val directoryPathString = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/" + finalFolderPath


        val destinationFolder = File(Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/" + finalFolderPathDesired)

        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs()
        }


        val myFile = File(directoryPathString, fileNamy)
        if (myFile.exists()) {
            startZipExtractionWork(myFile.toString(), destinationFolder.toString())
        }


    }


    private val runnable: Runnable = object : Runnable {
        override fun run() {
            getDownloadStatus()
            myHandler!!.postDelayed(this, 500)
        }
    }


    @SuppressLint("SetTextI18n")
    fun getDownloadStatus() {
        try {

            val download_ref = sharedP.getLong(Constants.downloadKey, -15)
            val query = DownloadManager.Query()
            query.setFilterById(download_ref)
            val c =
                (applicationContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager).query(
                    query
                )
            if (c!!.moveToFirst()) {
                @SuppressLint("Range") val bytes_downloaded =
                    c.getInt(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        .toLong()
                @SuppressLint("Range") val bytes_total =
                    c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)).toLong()
                val dl_progress =
                    (bytes_downloaded.toDouble() / bytes_total.toDouble() * 100f).toInt()
              ///  binding.progressBarPref.setProgress(dl_progress)
              //  binding.downloadBytes.setText(bytesIntoHumanReadable(bytes_downloaded.toString().toLong()) + "/" + bytesIntoHumanReadable(bytes_total.toString().toLong()))



                if (c == null) {
                  //  binding.textView10.setText(statusMessage(c))
                } else {
                    c.moveToFirst()
                  //  binding.textView10.setText(statusMessage(c))
                }
            }
        } catch (ignored: java.lang.Exception) {
        }
    }


    @SuppressLint("Range", "SetTextI18n")
    private fun statusMessage(c: Cursor): String? {
        var msg: String
        when (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            DownloadManager.STATUS_PENDING -> {
                msg = "Pending.."

            }

            DownloadManager.STATUS_RUNNING -> {
                msg = "Downloading.."
                isValid = true
            }

            DownloadManager.STATUS_PAUSED -> {
                msg = "Resume"

            }

            DownloadManager.STATUS_SUCCESSFUL -> {
                msg = "Completed"

            }

            DownloadManager.STATUS_FAILED -> {
                msg = "Failed!, Retry.."

            }

            else -> {
                msg = "failed! , try again.. "

            }
        }
        return msg
    }


    private fun initViewTooggle() {
        binding.apply {

            // for server
            getUrlBasedOnSpinnerText = CP_server
            constraintLayout4.setOnClickListener {
                hideKeyBoard(binding.editTextInputSynUrlZip)

                serVerOptionDialog()

            }

            // for time intervals
            getTimeDefined = timeMinuetesDefined
            textIntervalsSelect.setOnClickListener {

                definedTimeIntervals()
            }


            // use the clock
            textView12.setOnClickListener {
                val currentTime = Calendar.getInstance()
                val hour = currentTime.get(Calendar.HOUR_OF_DAY)
                val minute = currentTime.get(Calendar.MINUTE)

                val timePickerDialog = TimePickerDialog(
                    this@ReSyncActivity,
                    TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                        // Display the selected time on textDisplaytime
                        // val selectedTime = "Selected time : $selectedHour:$selectedMinute"
                        val selectedTime = "$selectedHour:$selectedMinute"
                        textDisplaytime.text = selectedTime
                    },
                    hour,
                    minute,
                    true // set to true if you want 24-hour format, false for 12-hour format
                )

                timePickerDialog.show()
            }


            val imgLunchOnline = sharedBiometric.getString(Constants.imgAllowLunchFromOnline, "")
            imagSwtichEnablOrDisbalePassowrd.isChecked =
                imgLunchOnline.equals(Constants.imgAllowLunchFromOnline)


            if (imgLunchOnline.equals(Constants.imgAllowLunchFromOnline)) {
                textLunchOnline.setText("Launch offline")
            } else {
                textLunchOnline.setText("Launch online")
            }

            imagSwtichEnablOrDisbalePassowrd.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                val editor = sharedBiometric.edit()
                hideKeyBoard(binding.editTextInputSynUrlZip)
                if (compoundButton.isChecked) {
                    editor.putString(Constants.imgAllowLunchFromOnline, "imgAllowLunchFromOnline")
                    editor.apply()
                    textLunchOnline.setText("Launch offline")

                } else {

                    editor.remove("imgAllowLunchFromOnline")
                    editor.apply()
                    textLunchOnline.setText("Launch online")

                }
            }


            /// use first Sync  or Do not use First Sync

            // enable satrt file for first synct

            val startFileFirstSync = sharedBiometric.getString(Constants.imgStartFileFirstSync, "")
            imgStartFileFirstSync.isChecked =
                startFileFirstSync.equals(Constants.imgStartFileFirstSync)


            if (startFileFirstSync.equals(Constants.imgStartFileFirstSync)) {
                textUseStartFile.setText("Use start file for first sync")
            } else {
                textUseStartFile.setText("Don't use start file for first sync")
            }


            imgStartFileFirstSync.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                val editor = sharedBiometric.edit()
                if (compoundButton.isChecked) {
                    editor.putString(
                        Constants.imgStartFileFirstSync, Constants.imgStartFileFirstSync
                    )
                    editor.apply()
                    textUseStartFile.setText("Use start file for first sync")

                } else {

                    editor.remove(Constants.imgStartFileFirstSync)
                    editor.apply()
                    textUseStartFile.setText("Don't use start file for first sync")

                }
            }


            // enable config

            val imgLCongigFile =
                sharedBiometric.getString(Constants.imagSwtichEnableConfigFileOnline, "")
            imagSwtichEnableConfigFileOnline.isChecked =
                imgLCongigFile.equals(Constants.imagSwtichEnableConfigFileOnline)


            if (imgLCongigFile.equals(Constants.imagSwtichEnableConfigFileOnline)) {
                textConfigfileOnline.setText("Config File Offline")
            } else {
                textConfigfileOnline.setText("Config File Online")
            }


            imagSwtichEnableConfigFileOnline.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                val editor = sharedBiometric.edit()
                if (compoundButton.isChecked) {
                    editor.putString(
                        Constants.imagSwtichEnableConfigFileOnline,
                        "imagSwtichEnableConfigFileOnline"
                    )
                    editor.apply()
                    textConfigfileOnline.setText("Config File Offline")
                } else {

                    editor.remove("imagSwtichEnableConfigFileOnline")
                    editor.apply()

                    textConfigfileOnline.setText("Config File Online")
                }
            }


            // enable Sync on File Change

            val imgEnableFileOnSyncChange =
                sharedBiometric.getString(Constants.imagSwtichEnableSyncOnFilecahnge, "")
            imagSwtichEnableSyncOnFilecahnge.isChecked =
                imgEnableFileOnSyncChange.equals(Constants.imagSwtichEnableSyncOnFilecahnge)


            if (imgEnableFileOnSyncChange.equals(Constants.imagSwtichEnableSyncOnFilecahnge)) {
                textSyncOnFileChangeIntervals.setText("Download on Intervals")
            } else {
                textSyncOnFileChangeIntervals.setText("Download on change")
            }


            imagSwtichEnableSyncOnFilecahnge.setOnCheckedChangeListener { compoundButton, isValued ->
                val editor = sharedBiometric.edit()
                if (compoundButton.isChecked) {
                    editor.putString(
                        Constants.imagSwtichEnableSyncOnFilecahnge,
                        "imagSwtichEnableSyncOnFilecahnge"
                    )
                    editor.apply()
                    textSyncOnFileChangeIntervals.setText("Download on Intervals")
                } else {

                    editor.remove("imagSwtichEnableSyncOnFilecahnge")
                    editor.apply()

                    textSyncOnFileChangeIntervals.setText("Download on change")
                }
            }


            // enable Toggle Mode

            val imgEnableToggleMode =
                sharedBiometric.getString(Constants.imagSwtichEnablEnableToggleOrNot, "")
            imagSwtichEnablEnableToggleOrNot.isChecked =
                imgEnableToggleMode.equals(Constants.imagSwtichEnablEnableToggleOrNot)

            if (imgEnableToggleMode.equals(Constants.imagSwtichEnablEnableToggleOrNot)) {
                textToogleMode.setText("Disable Test Toggle Mode")
            } else {
                textToogleMode.setText("Enable Test Toggle Mode")
            }

            imagSwtichEnablEnableToggleOrNot.setOnCheckedChangeListener { compoundButton, isValued ->
                val editor = sharedBiometric.edit()
                if (compoundButton.isChecked) {
                    editor.putString(
                        Constants.imagSwtichEnablEnableToggleOrNot,
                        "imagSwtichEnablEnableToggleOrNot"
                    )
                    editor.apply()
                    textToogleMode.setText("Disable Test Toggle Mode")
                } else {

                    editor.remove("imagSwtichEnablEnableToggleOrNot")
                    editor.apply()

                    textToogleMode.setText("Enable Test Toggle Mode")
                }
            }






            funManulOrNotInteView()

            //// logic for Select Partner Url

            val imagPartnerurl = sharedBiometric.getString(Constants.imagSwtichPartnerUrl, "")
            imagSwtichPartnerUrl.isChecked = imagPartnerurl.equals(Constants.imagSwtichPartnerUrl)


            if (imagPartnerurl.equals(Constants.imagSwtichPartnerUrl)) {
                textPartnerUrlLunch.setText("Select Partner Url")
            } else {
                textPartnerUrlLunch.setText("Custom Domain")
            }

            imagSwtichPartnerUrl.setOnCheckedChangeListener { compoundButton, isValued ->
                hideKeyBoard(binding.editTextInputSynUrlZip)
                val editor = sharedBiometric.edit()
                if (compoundButton.isChecked) {
                    editor.putString(Constants.imagSwtichPartnerUrl, "imagSwtichPartnerUrl")
                    editor.apply()
                    textPartnerUrlLunch.setText("Select Partner Url")

                } else {

                    editor.remove("imagSwtichPartnerUrl")
                    editor.apply()
                    textPartnerUrlLunch.setText("Custom Domain")

                }
            }


            // enable satrt file for first synct
            /*    val textSynfromApiZip222 =
                    sharedBiometric.getString(Constants.imagSwtichEnableSyncFromAPI, "")

                if (textSynfromApiZip222.equals(Constants.imagSwtichEnableSyncFromAPI)) {
                    textSynfromApiZip.setText("Use ZIP Sync")
                    textDownloadZipSyncOrApiSyncNow.setText("Connect ZIP Sync")
                } else {
                    textSynfromApiZip.setText("Use API Sync")
                    textDownloadZipSyncOrApiSyncNow.setText("Connect ZIP Sync")
                }
    */

            imagSwtichEnableSyncFromAPI.isChecked = true

            val editor = sharedBiometric.edit()
            editor.putString(
                Constants.imagSwtichEnableSyncFromAPI,
                Constants.imagSwtichEnableSyncFromAPI
            )
            editor.apply()

            imagSwtichEnableSyncFromAPI.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                val editor = sharedBiometric.edit()
                hideKeyBoard(binding.editTextInputSynUrlZip)
                if (compoundButton.isChecked) {
                    editor.putString(
                        Constants.imagSwtichEnableSyncFromAPI,
                        Constants.imagSwtichEnableSyncFromAPI
                    )
                    editor.apply()
                    textSynfromApiZip.setText("Use ZIP Sync")
                    editTextInputSynUrlZip.setHint("Input url  ZIP Sync")
                    textDownloadZipSyncOrApiSyncNow.setText("Connect ZIP Sync")
                } else {

                    editor.remove(Constants.imagSwtichEnableSyncFromAPI)
                    editor.apply()

                    textSynfromApiZip.setText("Use API Sync")
                    textDownloadZipSyncOrApiSyncNow.setText("Connect API Sync")
                    editTextInputSynUrlZip.setHint("Input url  API Sync")


                }
            }


            // enable satrt file for first synct

        }


    }

    @SuppressLint("SetTextI18n")
    private fun definedTimeIntervals() {

        val serverOptions = arrayOf(
            timeMinuetesDefined,
            timeMinuetes22,
            timeMinuetes55,
            timeMinuetes10,
            timeMinuetes15,
            timeMinuetes30,
            timeMinuetes60,
            timeMinuetes120,
            timeMinuetes180,
            timeMinuetes240
        )

        val builder = AlertDialog.Builder(this@ReSyncActivity)
        // builder.setTitle("Choose Server")
        builder.setItems(serverOptions) { dialog, which ->
            when (which) {
                0 -> {
                    // Handle CP-Cloud App Server selection
                    binding.textIntervalsSelect.text = timeMinuetesDefined
                    binding.textDisplaytime.text = "00:55 Seconds"
                    getTimeDefined = timeMinuetesDefined
                }

                1 -> {
                    binding.textIntervalsSelect.text = timeMinuetes22
                    binding.textDisplaytime.text = "2 Minutes"
                    getTimeDefined = timeMinuetes22
                }

                2 -> {
                    binding.textIntervalsSelect.text = timeMinuetes55
                    binding.textDisplaytime.text = "5 Minutes"
                    getTimeDefined = timeMinuetes55
                }

                3 -> {
                    binding.textIntervalsSelect.text = timeMinuetes10
                    binding.textDisplaytime.text = "10 Minutes"
                    getTimeDefined = timeMinuetes10
                }

                4 -> {
                    binding.textIntervalsSelect.text = timeMinuetes15
                    binding.textDisplaytime.text = "15 Minutes"
                    getTimeDefined = timeMinuetes15
                }

                5 -> {
                    binding.textIntervalsSelect.text = timeMinuetes30
                    binding.textDisplaytime.text = "30 Minutes"
                    getTimeDefined = timeMinuetes30
                }

                6 -> {
                    binding.textIntervalsSelect.text = timeMinuetes60
                    binding.textDisplaytime.text = "60 Minutes"
                    getTimeDefined = timeMinuetes60
                }

                7 -> {
                    binding.textIntervalsSelect.text = timeMinuetes120
                    binding.textDisplaytime.text = "2 Hours"
                    getTimeDefined = timeMinuetes120
                }

                8 -> {
                    binding.textIntervalsSelect.text = timeMinuetes180
                    binding.textDisplaytime.text = "3 hours"
                    getTimeDefined = timeMinuetes180
                }

                9 -> {
                    binding.textIntervalsSelect.text = timeMinuetes240
                    binding.textDisplaytime.text = "4 hours"
                    getTimeDefined = timeMinuetes240
                }
            }
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    private fun serVerOptionDialog() {
        val serverOptions = arrayOf(CP_server, API_Server)

        val builder = AlertDialog.Builder(this@ReSyncActivity)
        // builder.setTitle("Choose Server")
        builder.setItems(serverOptions) { dialog, which ->
            when (which) {
                0 -> {
                    // Handle CP-Cloud App Server selection
                    binding.texturlsViews.text = CP_server
                    getUrlBasedOnSpinnerText = CP_server
                }

                1 -> {
                    binding.texturlsViews.text = API_Server
                    getUrlBasedOnSpinnerText = API_Server
                }
            }
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    private fun funManulOrNotInteView() {
        binding.apply {
            // logic for use manual or not
            val imagUsemanualOrnotuseManual =
                sharedBiometric.getString(Constants.imagSwtichEnableManualOrNot, "")
            imagSwtichEnableManualOrNot.isChecked =
                imagUsemanualOrnotuseManual.equals(Constants.imagSwtichEnableManualOrNot)


            if (imagUsemanualOrnotuseManual.equals(Constants.imagSwtichEnableManualOrNot)) {
                textUseManual.setText("Use manual")

                editTextInputSynUrlZip.visibility = View.VISIBLE
                // for clos
                editTextCLOpath.visibility = View.GONE
                editTextSubPathFolder.visibility = View.GONE


            } else {
                textUseManual.setText("Do not use manual")

                editTextInputSynUrlZip.visibility = View.GONE
                // for clos
                editTextCLOpath.visibility = View.VISIBLE
                editTextSubPathFolder.visibility = View.VISIBLE


            }

            imagSwtichEnableManualOrNot.setOnCheckedChangeListener { compoundButton, isValued -> // we are putting the values into SHARED PREFERENCE
                val editor = sharedBiometric.edit()
                hideKeyBoard(binding.editTextInputSynUrlZip)
                if (compoundButton.isChecked) {
                    editor.putString(
                        Constants.imagSwtichEnableManualOrNot,
                        "imagSwtichEnableManualOrNot"
                    )
                    editor.apply()
                    textUseManual.setText("Use manual")

                    editTextInputSynUrlZip.visibility = View.VISIBLE
                    // for clos
                    editTextCLOpath.visibility = View.GONE
                    editTextSubPathFolder.visibility = View.GONE


                } else {

                    editor.remove("imagSwtichEnableManualOrNot")
                    editor.apply()
                    textUseManual.setText("Do not use manual")

                    editTextInputSynUrlZip.visibility = View.GONE
                    // for clos
                    editTextCLOpath.visibility = View.VISIBLE
                    editTextSubPathFolder.visibility = View.VISIBLE

                }
            }

        }
    }


    private fun hideKeyBoard(editText: EditText) {
        try {
            editText.clearFocus()
            val imm =
                applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        } catch (ignored: java.lang.Exception) {
        }
    }


    private fun testAndDownLoadZipConnection() {
        binding.apply {
            val getFolderClo = editTextCLOpath.text.toString().trim()
            val getFolderSubpath = editTextSubPathFolder.text.toString().trim()
            val editor = sharedP.edit()

            if (isNetworkAvailable()) {
                if (!imagSwtichEnableManualOrNot.isChecked && isNetworkAvailable()) {
                    when (getUrlBasedOnSpinnerText) {
                        CP_server -> {
                            if (getFolderClo.isNotEmpty() && getFolderSubpath.isNotEmpty()) {
                                httpNetworkDownloadsMultiplePaths(getFolderClo, getFolderSubpath)
                                editor.putString(Constants.getSavedCLOImPutFiled,getFolderClo)
                                editor.putString(Constants.getSaveSubFolderInPutFiled,getFolderSubpath)
                                editor.apply()

                            } else {
                                editTextCLOpath.error = "Input a valid path e.g CLO"
                                editTextSubPathFolder.error = "Input a valid path e.g DE_MO_2021000"
                                showToastMessage("Fields can not be empty")
                            }
                        }

                        API_Server -> {
                            showToastMessage("No Logic For API Server Yet")
                        }

                    }

                }
                //// enf of the main if
            } else {
                showToastMessage("No Internet Connection")
            }


            /// when the button is checked
            if (isNetworkAvailable()) {
                if (imagSwtichEnableManualOrNot.isChecked && isNetworkAvailable()) {
                    when (getUrlBasedOnSpinnerText) {
                        CP_server -> {

                            val editInputUrl = editTextInputSynUrlZip.text.toString().trim()
                            if (editInputUrl.isNotEmpty() && isUrlValid(editInputUrl)) {
                                httpNetSingleDwonload(editInputUrl)
                                editor.putString(Constants.getSavedEditTextInputSynUrlZip,editInputUrl)
                                editor.apply()

                            } else {
                                showToastMessage("Invalid url format")
                                binding.editTextInputSynUrlZip.error = "Invalid url format"
                            }

                        }

                        API_Server -> {
                            showToastMessage("No Logic For API Server Yet")
                        }

                    }
                }
            } else {
                showToastMessage("No Internet Connection")
            }

        }
    }


    private fun httpNetworkDownloadsMultiplePaths(getFolderClo: String, getFolderSubpath: String) {
        handler.postDelayed(Runnable {
            showCustomProgressDialog("Please wait")


            if (binding.imagSwtichEnableSyncFromAPI.isChecked) {

                val baseUrl =
                    "https://cp.cloudappserver.co.uk/app_base/public/$getFolderClo/$getFolderSubpath/Zip/App.zip"

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            // showPopContinueToDownloadingForMultiples(baseUrl,getFolderClo, getFolderSubpath, "Zip", "App.zip" )
                            startMyDownlaodsMutiplesPath(
                                baseUrl,
                                getFolderClo,
                                getFolderSubpath,
                                "Zip",
                                "App.zip"
                            )
                        } else {
                            // showPopConnectionSucessful(baseUrl, "Failed!")
                            showPopsForMyConnectionTest(getFolderClo, getFolderSubpath, "Failed!")
                        }
                    } finally {
                        customProgressDialog.dismiss()
                    }
                }


            } else {

                val baseUrl =
                    "https://cp.cloudappserver.co.uk/app_base/public/$getFolderClo/$getFolderSubpath/Api/update.csv"

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            ///  showPopContinueToDownloadingForMultiples(baseUrl,getFolderClo, getFolderSubpath, "Api", "update.csv" )
                            startMyDownlaodsMutiplesPath(
                                baseUrl,
                                getFolderClo,
                                getFolderSubpath,
                                "Api",
                                "update.csv"
                            )
                        } else {
                            //  showPopConnectionSucessful(baseUrl, "Failed!")
                            showPopsForMyConnectionTest(getFolderClo, getFolderSubpath, "Failed!")
                        }
                    } finally {
                        customProgressDialog.dismiss()
                    }
                }

            }


        }, 300)
    }


    private fun httpNetSingleDwonload(baseUrl: String) {
        handler.postDelayed(Runnable {
            showCustomProgressDialog("Please wait")
            val lastString = baseUrl.substringAfterLast("/")
            val fileNameWithoutExtension = lastString.substringBeforeLast(".")


            if (binding.imagSwtichEnableSyncFromAPI.isChecked) {

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            //  showPopContinueToDownloading(baseUrl, "Zip", "App.zip" )
                            startDownloadSingles(baseUrl, "Man_1", "App.zip")
                        } else {
                            // showPopConnectionSucessful(baseUrl, "Failed!")
                           // showPopsForMyConnectionTest("CLO", fileNameWithoutExtension, "Failed!")
                            showPopsForMyConnectionTest("CLO", "Man_1", "Failed!")
                        }
                    } finally {
                        customProgressDialog.dismiss()
                    }
                }

            } else {

                lifecycleScope.launch {
                    try {
                        val result = checkUrlExistence(baseUrl)
                        if (result) {
                            //  showPopContinueToDownloading(baseUrl, "API", "App.zip" )
                            startDownloadSingles(baseUrl, "Man_2", "App.zip")
                        } else {

                           // showPopsForMyConnectionTest("CLO", fileNameWithoutExtension, "Failed!")
                            showPopsForMyConnectionTest("CLO", "Man_2", "Failed!")
                        }
                    } finally {
                        customProgressDialog.dismiss()
                    }
                }
            }


        }, 300)
    }


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    private fun showPopsForMyConnectionTest(
        getFolderClo: String,
        getFolderSubpath: String,
        message: String,
    ) {

        val binding: CustomContinueDownloadLayoutBinding =
            CustomContinueDownloadLayoutBinding.inflate(layoutInflater)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(binding.root)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCancelable(true)


        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        binding.apply {


            val patterTexT = "/$getFolderClo/$getFolderSubpath"

          //  textYourUrlTest.text = "Your Directory is :\n$rootfolder$patterTexT"

            val userPath = "User\n-$getFolderClo-license-$getFolderSubpath"
            textYourUrlTest.text = userPath

            textSucessful.text = message

            textContinuPassword2.setOnClickListener {
                alertDialog.dismiss()

            }


        }


        alertDialog.show()


    }


    private fun startMyDownlaodsMutiplesPath(
        baseUrl: String,
        getFolderClo: String,
        getFolderSubpath: String,
        Zip: String,
        fileName: String,
    ) {

        val patterThreePath = "/$getFolderClo/$getFolderSubpath/$Zip"

        getToatlFilePath = patterThreePath + "/$fileName"

        val Extracted = "Offline_app"
        unzipManual = "/$getFolderClo/$getFolderSubpath/$Extracted"
        Log.d("startMyDownlaodsMutiplesPath", ": $unzipManual")


        download(baseUrl, getFolderClo, getFolderSubpath, Zip, fileName, Extracted)
        // isValid = true
        getDownloadStatus();

        val intent = Intent(applicationContext, DownlodPagger::class.java)
        intent.putExtra("baseUrl", baseUrl)
        intent.putExtra("getFolderClo", getFolderClo)
        intent.putExtra("getFolderSubpath", getFolderSubpath)
        intent.putExtra("Zip", Zip)
        intent.putExtra("fileName", fileName)
        intent.putExtra("Extracted", Extracted)

        intent.putExtra("getToatlFilePath", getToatlFilePath)
        intent.putExtra("unzipManual", unzipManual)
        intent.putExtra("patterThreePath", patterThreePath)
        startActivity(intent)

        /// similar but used on under second cancel downoad in danwload pager
        val editor = sharedP.edit()
        editor.putString("getFolderClo", getFolderClo)
        editor.putString("getFolderSubpath", getFolderSubpath)
        editor.putString("Zip", Zip)
        editor.putString("fileName", fileName)
        editor.putString("Extracted", Extracted)
        editor.apply()


    }


    private fun startDownloadSingles(baseUrl: String, Zip: String, fileNamy: String) {

        val MANUAL = "MANUAL"

        val manualPath = "/$MANUAL/$Zip"

        val patterThreePath = "/$MANUAL/$Zip"

        getToatlFilePath = manualPath + "/$fileNamy"

        val Extracted = "Manual_offline_app"
        unzipManual = "/$MANUAL/$Extracted"

      //  download(baseUrl, manualPath, fileNamy, Zip )
        download(baseUrl, MANUAL, "DEMO", Zip, fileNamy, Extracted)
        //   isValid = true
        getDownloadStatus();

        val intent = Intent(applicationContext, DownlodPagger::class.java)
        intent.putExtra("baseUrl", baseUrl)
        intent.putExtra("getFolderClo", MANUAL)
        intent.putExtra("getFolderSubpath", "DEMO_MANUAL")
        intent.putExtra("Zip", Zip)
        intent.putExtra("fileName", fileNamy)
        intent.putExtra("Extracted", Extracted)

        intent.putExtra("getToatlFilePath", getToatlFilePath)
        intent.putExtra("unzipManual", unzipManual)
        intent.putExtra("patterThreePath", patterThreePath)
        startActivity(intent)

        /// similar but used on under second cancel downoad in danwload pager
        val editor = sharedP.edit()
        editor.putString("getFolderClo", MANUAL)
        editor.putString("getFolderSubpath", "DEMO_MANUAL")
        editor.putString("Zip", Zip)
        editor.putString("fileName", fileNamy)
        editor.putString("Extracted", Extracted)
        editor.apply()



    }


    private fun showToastMessage(messages: String) {

        try {
            Toast.makeText(applicationContext, messages, Toast.LENGTH_SHORT).show()
        } catch (_: Exception) {
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        getDownloadStatus()

        try {
            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnable)
            }
        } catch (ignored: java.lang.Exception) {
        }
        if (myHandler != null) {
            myHandler!!.postDelayed(runnable, 500)
        }
    }


    override fun onPause() {
        super.onPause()
        try {
            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnable)
            }
        } catch (ignored: java.lang.Exception) {
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnable)
            }
        } catch (ignored: java.lang.Exception) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (myHandler != null) {
                myHandler!!.removeCallbacks(runnable)
            }
        } catch (ignored: java.lang.Exception) {
        }
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


    private fun bytesIntoHumanReadable(bytes: Long): String? {
        val kilobyte: Long = 1024
        val megabyte = kilobyte * 1024
        val gigabyte = megabyte * 1024
        val terabyte = gigabyte * 1024
        return if (bytes >= 0 && bytes < kilobyte) {
            "$bytes B"
        } else if (bytes >= kilobyte && bytes < megabyte) {
            (bytes / kilobyte).toString() + " KB"
        } else if (bytes >= megabyte && bytes < gigabyte) {
            (bytes / megabyte).toString() + " MB"
        } else if (bytes >= gigabyte && bytes < terabyte) {
            (bytes / gigabyte).toString() + " GB"
        } else if (bytes >= terabyte) {
            (bytes / terabyte).toString() + " TB"
        } else {
            bytes.toString() + ""
        }
    }


    private fun doOperation() {
        val getsyn2aapFolderValue = sharedBiometric.getString(Constants.DELETE_SYN2APP_FOLDER, "")
        if (getsyn2aapFolderValue != "DELETE_SYN2APP_FOLDER"){

            deleteMySy2nAppliveFolder()
            saveValuesForMySy2nAppliveFiolder()
        }

        handler.postDelayed(Runnable {
            testAndDownLoadZipConnection()
        }, 300)
    }




/*

    private fun deleteMySy2nAppliveFolder() {
        val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/DE_MO_2021000/Api/"

        val directory = File(directoryPath)

        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()

            if (files != null && files.isNotEmpty()) {
                // Assuming you want to delete the first file in the directory
                val myFile = files[0]

                val myFileName = myFile.name

                showToastMessage(myFileName)

                if (myFile.exists()) {
                    myFile.delete()
                    showToastMessage("File $myFileName deleted successfully")
                } else {
                    showToastMessage("File $myFileName does not exist")
                }
            } else {
                showToastMessage("No files in the directory")
            }
        } else {
            showToastMessage("Directory does not exist or is not a directory")
        }
    }
*/


/*
    val directoryPathPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/"

    val myFile = File(directoryPathPath)

    if (myFile.exists()) {
        myFile.deleteRecursively()
        //    showToastMessage("File deleted successfully")
    } else {
        //   showToastMessage("File does not exist")
    }
*/



    private fun deleteMySy2nAppliveFolder() {



/*            val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/CLO/DE_MO_2021000/Zip/App.zip"

            val  nameOfpackage = this.packageName

            val fileUri = FileProvider.getUriForFile(
                applicationContext,
                "$nameOfpackage.fileprovider",
                File(directoryPath)
            )

            DeleteRecursive(fileUri)

            */


        }


    private fun DeleteRecursive(fileOrDirectory: Uri) {


    }


    private fun deleteMySyn2AppLiveFolder(folder: File) {
        if (folder.exists()) {
            val files = folder.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isDirectory) {
                        deleteMySyn2AppLiveFolder(file)
                    } else {
                        file.delete()
                    }
                }
            }
            folder.delete()
            showToastMessage("Folder deleted successfully")
        } else {
            showToastMessage("Folder does not exist")
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


    private fun download(url: String, getFolderClo:String,  getFolderSubpath:String, Zip: String, fileNamy: String, Extracted:String) {
        val finalFolderPath = "/$getFolderClo/$getFolderSubpath/$Zip"
        val Syn2AppLive = "Syn2AppLive"


        val editior = savedDownloadPath.edit()
        editior.putString("getFolderClo", getFolderClo)
        editior.putString("getFolderSubpath", getFolderSubpath)
        editior.putString("Zip", Zip)
        editior.putString("fileNamy", fileNamy)
        editior.putString("Extracted", Extracted)
        editior.apply()


        val directoryPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Syn2AppLive/" + finalFolderPath

        val myFile = File(directoryPath, fileNamy)

        if (myFile.exists()) {
            myFile.deleteRecursively()
            showToastMessage("File deleted successfully")
        } else {
            showToastMessage("File does not exist")
        }


        val managerDownload = getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        val folder = File(Environment.DIRECTORY_DOWNLOADS.toString() + "/$Syn2AppLive/$finalFolderPath")

        if (!folder.exists()) {
            folder.mkdirs()
        }


        // Continue with the download logic
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(fileNamy)
        // request.setDescription("Downloading...")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS, "/$Syn2AppLive/$finalFolderPath/$fileNamy"
        )
        val downloadReferenceMain = managerDownload.enqueue(request)

        val sharedPreferences = getSharedPreferences(Constants.MY_DOWNLOADER_CLASS, Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putLong(Constants.downloadKey, downloadReferenceMain)
        editor.apply()
    }




}