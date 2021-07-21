package com.example.test

import android.Manifest
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {
    private val base64Utils = Base64Utils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main0)
        init()

        val query=intent?.getStringExtra(SearchManager.QUERY)
        if(query!=null){
            Log.d("zgq",query.toString())
        }
    }



    fun searchWeb(query: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val REQUEST_PERMISSION_CODE=1

    private fun init() {

        //悬浮按钮
        val button = findViewById<Button>(R.id.dblink)
        button.setOnClickListener {
            val page = base64Utils.getBase64("vidmate")
            val page_from = base64Utils.getBase64("floatbutton")
            var referer =
                base64Utils.getBase64("utm_source%3Dfloatbutton_pr%26utm_medium%3Dfile_page%26utm_campaign%3DVDM_growth")
            //open(page,page_from,referer)

            //viewFile(this)
            //test2()
            //searchWeb("中国")
            val uri=Uri.parse(Environment.getExternalStorageDirectory().absolutePath+"/Download/aa2.mp3")
            AudioManager.play(it.context,uri,"aa")
        }

        //分享banner
        val button2 = findViewById<Button>(R.id.dblink2)
        button2.setOnClickListener {
            val page = base64Utils.getBase64("vidmate")
            val page_from = base64Utils.getBase64("sharebanner")
            var referer =
                base64Utils.getBase64("utm_source%3Dsharebanner_pr%26utm_medium%3Dsharecontrol%26utm_campaign%3DVDM_growth")
            open(page, page_from, referer)
        }

        //文管按钮（旧）
        val button3 = findViewById<Button>(R.id.dblink3)
        button3.setOnClickListener {
            val page = base64Utils.getBase64("vidmate")
            val page_from = base64Utils.getBase64("filemanager")
            var referer =
                base64Utils.getBase64("utm_source%3Dfilemg_pr%26utm_medium%3Dfile_page%26utm_campaign%3DVDM_growth")

            open(page, page_from, referer)
        }

        //文管按钮
        val button4 = findViewById<Button>(R.id.dblink4)
        button4.setOnClickListener {
            val page = base64Utils.getBase64("filemanager")
            val page_from = base64Utils.getBase64("filemanager")
            var referer =
                base64Utils.getBase64("utm_source%3Dfilemg_pr%26utm_medium%3Dfile_page%26utm_campaign%3DVDM_growth")

            open(page, page_from, referer)

        }

        val btn_owl = findViewById<Button>(R.id.btn_owl)
        btn_owl.setOnClickListener {
            openOwl()
        }

        val btn_owl2 = findViewById<Button>(R.id.btn_owl2)
        btn_owl2.setOnClickListener {
            openOwl2()
        }

        val vm_test = findViewById<Button>(R.id.vm_test)
        vm_test.setOnClickListener {
            openVmTest()
        }

        val btn_open_thread=findViewById<Button>(R.id.btn_open_thread)
        btn_open_thread.setOnClickListener {
            ThreadTest.executeRunnable(this)
        }
        val btn_open_thread2=findViewById<Button>(R.id.btn_open_thread2)
        btn_open_thread2.setOnClickListener {
            ThreadTest.executeRunnable(this)
        }

        val btn_close_thread=findViewById<Button>(R.id.btn_close_thread)
        btn_close_thread.setOnClickListener {
            ThreadTest.stop()
        }
    }

    private fun open(page: String?, page_from: String?, referer: String?) {

        val uri = Uri.parse(
            "shareu://transfer/main?page="
                    + page + "&referer=" + referer + "&page_from=" + page_from
        )
        Log.d("zgq", uri.toString())
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    private fun openOwl() {
        val edt_owl = findViewById<EditText>(R.id.edt_owl)
        val host = edt_owl.text.toString()
        val url =
            "owlbrowser://virtual?params={\"channel\":\"warn\",\"action\":\"jump\",\"url\":\"${host}?from%3Dv%26appver%3D4.4706%23gaid%3Dfc0f434a-2d47-4a82-973b-245f1c7df2e2%26geo%3Dpk%26appver%3D4.4706\",\"activityid\":\"20210311\",\"mode\":\"globe\",\"vpn\":\"yes\"}"
        val uri = Uri.parse(url)
        Log.d("zgq", uri.toString())
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun openOwl2() {
        val edt_owl = findViewById<EditText>(R.id.edt_owl)
        val host = edt_owl.text.toString()
        val url =
            "owlbrowser://virtual?params={\"channel\":\"warn\",\"action\":\"jump\",\"url\":\"${host}?from%3Dv%26appver%3D4.4706%23gaid%3Dfc0f434a-2d47-4a82-973b-245f1c7df2e2%26geo%3Dpk%26appver%3D4.4706\",\"activityid\":\"20210311\",\"mode\":\"globe\",\"vpn\":\"no\"}"
        val uri = Uri.parse(url)
        Log.d("zgq", uri.toString())
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun openVmTest() {
        val edt_owl = findViewById<EditText>(R.id.edt_owl)
        val host = edt_owl.text.toString()
        val url =
            "owlbrowser://virtual?params={\"channel\":\"404page\",\"action\":\"jump\",\"url\":\"${host}?from%3Dv%26appver%3D4.4706%23gaid%3Dfc0f434a-2d47-4a82-973b-245f1c7df2e2%26geo%3Dpk%26appver%3D4.4706\",\"activityid\":\"20210420\",\"mode\":\"globe\",\"vpn\":\"yes\"}"
        val uri = Uri.parse(url)
        Log.d("zgq", uri.toString())
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    private fun open2() {
        val intent = Intent()
        intent.component = ComponentName(
            "com.shareu.share.transfer.debug",
            "com.shareu.transfer.main.MainActivity"
        )
        intent.action = Intent.ACTION_MAIN
        intent.putExtra("referrer", "floatbutton")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    private fun test() {
        val documentMimeType = arrayListOf<String>(
            "text/plain",
            "application/msword",
            "application/pdf",
            "application/vnd.ms-powerpoint",
            "application/vnd.ms-excel"
        )

        var selection = StringBuilder()
        var selectionArgs = documentMimeType.toArray()

        for ((index, value) in documentMimeType.withIndex()) {
            if (index > 0) {
                selection.append(" or ")
            }
            selection.append(MediaStore.Files.FileColumns.MIME_TYPE + " = ?")
        }

        val uri = MediaStore.Files.getContentUri("external")


    }


    private fun viewFile(context: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE)
            }else{
                val filePath =
                    Environment.getExternalStorageDirectory().absolutePath + "/Download/dd.jpg"
                val file = File(filePath)

                val intent = Intent(Intent.ACTION_VIEW)
                val mimeType = "image/jpeg"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val uri = try {
                        getUriByFileProvider(context, file)
                    } catch (e: Throwable) {
                        return
                    }
                    intent.data = uri
                    intent.setDataAndType(uri, mimeType)
                    intent.putExtra(Intent.EXTRA_STREAM, uri)
                } else {
                    val uri = Uri.fromFile(file)
                    intent.setDataAndType(uri, mimeType)
                }
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)

                try {
                    if (mimeType == "application/vnd.android.package-archive") {
                        context.startActivity(intent)
                    } else {
                        context.startActivity(Intent.createChooser(intent, "View File..."))
                    }
                } catch (e: Throwable) {
                    Toast.makeText(context, "Open File Error", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun getUriByFileProvider(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            BuildConfig.APPLICATION_ID + ".fileProvider",
            file
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {

        }
    }
}