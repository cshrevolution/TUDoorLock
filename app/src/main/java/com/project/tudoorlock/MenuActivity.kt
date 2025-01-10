package com.project.tudoorlock

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import java.util.logging.Handler
import kotlin.concurrent.thread

class MenuActivity : AppCompatActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter

    private lateinit var devices: Set<BluetoothDevice> // 블루투스 디바이스 데이터 셋
    private var bluetoothDevice: BluetoothDevice? = null // 블루투스 디바이스
    private var bluetoothSocket: BluetoothSocket? = null //블루투스 소켓
    private var outputStream: OutputStream? = null //블루투스에 데이터를 출력하기 위한 출력 스트림
    private var inputStream: InputStream? = null //블루투스에 데이터를 입력하기 위한 입력 스트림
    private var workerThread: Thread? = null //문자열 수신에 사용되는 쓰레드
    private lateinit var readBuffer: ByteArray //수신된 문자열 저장 버퍼
    private var readBufferPosition = 0 //버퍼  내 문자 저장 위치
    var array: Array<String> = arrayOf("0") //수신된 문자열을 쪼개서 저장할 배열

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val preferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt("count", 1)
        editor.apply()

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter.isEnabled) {
            // --
        } else {
            intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }

        selectBluetoothDevice()

        val channel = NotificationChannel("Visitor", "Visitor", NotificationManager.IMPORTANCE_HIGH)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        Log.d("array", array[0])

        val unlockButton = findViewById<LinearLayout>(R.id.unlockButton)
        val lockButton = findViewById<LinearLayout>(R.id.lockButton)

        unlockButton.setOnClickListener {
            lockButton.isClickable = false
            unlockButton.visibility = View.GONE
            lockButton.visibility = View.VISIBLE

            if (preferences.getBoolean("switchStatus", false)) {
                startActivity(Intent(this, PasswordInputActivity::class.java))
                finish()
            }
            lockButton.isClickable = true
        }

        lockButton.setOnClickListener {
            unlockButton.isClickable = false


            unlockButton.visibility = View.VISIBLE
            lockButton.visibility = View.GONE

            unlockButton.isClickable = true
        }

        val btnSettings: ImageButton = findViewById(R.id.btn_settings)
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val btnNotification: ImageButton = findViewById(R.id.btn_notification)
        btnNotification.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        val cameraButton: Button = findViewById(R.id.camera_button)
        cameraButton.setOnClickListener {
            val intent = Intent(this, LiveCameraActivity::class.java)
            startActivity(intent)
        }
    }

    fun selectBluetoothDevice() {
        val devices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("블루투스 장치 선택")
        val deviceList = mutableListOf<String>()
        for (i in devices.indices) {
            deviceList.add(devices.elementAt(i).name)
        }
        Log.d("device", devices.toString())
        var charSequences: Array<CharSequence?> = deviceList.toTypedArray()
        deviceList.add("취소")
        dialog.setItems(charSequences) { _, which ->
            connectDevice(charSequences[which])
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun connectDevice(deviceName: CharSequence?) {
        //페어링 된 디바이스 모두 탐색
        val devices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
        if (devices != null) {
            for (tempDevice in devices) {
                Log.d("device", tempDevice.name)
                //사용자가 선택한 이름과 같은 디바이스로 설정하고 반복문 종료
                if (deviceName == tempDevice.name) {
                    bluetoothDevice = tempDevice
                    break
                }
            }
        }
        Toast.makeText(
            applicationContext,
            bluetoothDevice?.name + " 연결 완료!",
            Toast.LENGTH_SHORT
        ).show()
        //UUID생성
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")

        //Rfcomm 채널을 통해 블루투스 디바이스와 통신하는 소켓 생성
        try {
            bluetoothSocket = bluetoothDevice?.createRfcommSocketToServiceRecord(uuid)
            bluetoothSocket?.connect()

            outputStream = bluetoothSocket?.getOutputStream()
            inputStream = bluetoothSocket?.getInputStream()
            receiveData()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /*fun receiveData() {
        val handler = android.os.Handler(Looper.getMainLooper()) // 메인 쓰레드 루퍼 사용
        readBufferPosition = 0
        readBuffer = ByteArray(1024)

        workerThread = thread(true) { // 데몬 쓰레드로 변경
            while (!Thread.currentThread().isInterrupted) {
                try {
                    val byteAvailable = inputStream?.available()
                    if (byteAvailable != null && byteAvailable > 0) {
                        // 입력 스트림에서 바이트 단위로 읽어옴
                        val bytes = ByteArray(byteAvailable)
                        val bytesRead = inputStream!!.read(bytes) // 읽은 바이트 수 확인

                        // 입력 스트림 바이트를 한 바이트씩 읽어옴
                        for (i in 0 until bytesRead) { // bytesRead 사용
                            val tempByte = bytes[i]
                            // 개행문자를 기준으로 받음 (한줄)
                            if (tempByte == '\n'.code.toByte()) {
                                // readBuffer 배열을 encodedBytes로 복사
                                val encodedBytes = ByteArray(readBufferPosition)
                                System.arraycopy(
                                    readBuffer,
                                    0,
                                    encodedBytes,
                                    0,
                                    encodedBytes.size
                                )
                                // 인코딩 된 바이트 배열을 문자열로 변환
                                val text = String(encodedBytes, charset("UTF-8"))
                                readBufferPosition = 0

                                handler.post {
                                    array = text.split(",").toTypedArray()
                                    Log.d("array", array.toString())
                                    if (array.contains("Hello")) {
                                        val noti = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                                        noti.notify(1, buildNoti().build())
                                    }
                                }
                            } // 개행문자가 아닐 경우
                            else {
                                readBuffer[readBufferPosition++] = tempByte
                            }
                        }

                        Thread.sleep(1000) // 1초 sleep 추가
                    } else {
                        // 입력 스트림 확인 및 오류 처리
                        if (byteAvailable == null) {
                            Log.e("ReceiveData", "Input stream is null")
                            // 입력 스트림이 null인 경우 처리 방안 추가 (예: 재연결 시도)
                        } else {
                            Log.d("ReceiveData", "No data available in input stream")
                        }
                        Thread.sleep(100) // 데이터 없을 때 짧게 sleep
                    }
                } catch (e: IOException) {
                    // 예외 처리 로직 추가
                    Log.e("ReceiveData", "Error reading data: ${e.message}")
                    // 예외 발생 시 처리 방안 추가 (예: 재연결 시도, 오류 알림)
                }
            }
        }
    }*/

    fun receiveData() {
        val handler: android.os.Handler = android.os.Handler()
        //데이터 수신을 위한 버퍼 생성
        readBufferPosition = 0
        readBuffer = ByteArray(1024)

        //데이터 수신을 위한 쓰레드 생성
        workerThread = thread(false) {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    //데이터 수신 확인
                    val byteAvailable = inputStream?.available()
                    //데이터 수신 된 경우
                    if (byteAvailable != null) {
                        if (byteAvailable > 0) {
                            Log.d("byteAvailable", byteAvailable.toString())
                            //입력 스트림에서 바이트 단위로 읽어옴
                            val bytes = ByteArray(byteAvailable)
                            inputStream!!.read(bytes)
                            //입력 스트림 바이트를 한 바이트씩 읽어옴
                            for (i in 0 until byteAvailable) {
                                val tempByte = bytes[i]
                                //개행문자를 기준으로 받음 (한줄)
                                if (tempByte == '\n'.code.toByte()) {
                                    //readBuffer 배열을 encodeBytes로 복사
                                    val encodedBytes = ByteArray(readBufferPosition)
                                    System.arraycopy(
                                        readBuffer,
                                        0,
                                        encodedBytes,
                                        0,
                                        encodedBytes.size
                                    )
                                    //인코딩 된 바이트 배열을 문자열로 변환
                                    val text =
                                        String(encodedBytes, charset("UTF-8"))
                                    Log.d("text", text)
                                    readBufferPosition = 0

                                    handler.post(Runnable {
                                        array = text.split(",").toTypedArray()
                                        Log.d("array", array.toString())
                                        if (array.contains("Hello")) {
                                            val noti = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                                            noti.notify(1, buildNoti().build())
                                        }
                                    })


                                } // 개행문자가 아닐경우
                                else {
                                    readBuffer[readBufferPosition++] = tempByte
                                }

                            }
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            try {
                //1초 마다 받아옴
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }

        workerThread?.start()
    }

    private fun buildNoti(): NotificationCompat.Builder {
        val intent = Intent(this, NotificationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(this, "Visitor")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("알림")
            .setContentText("방문자가 있습니다!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        return builder
    }

}
