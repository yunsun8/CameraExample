package com.example.cameraexample

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.cameraexample.R

// 자신을 SensorEventListener 인터페이스의 구현체로 등록
class MainActivity : AppCompatActivity(), SensorEventListener {

    // 지연된 초기화를 사용하여 sensorManager 객체를 얻는다.
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    // 수평을 시각적으로 보여주는 뷰
    private lateinit var tiltView: TiltView

    override fun onCreate(savedInstanceState: Bundle?) {

        // 화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 화면이 가로모드로 고정되게 하기
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        super.onCreate(savedInstanceState)

        // 커스텀 뷰 사용하기
        tiltView = TiltView(this)
        setContentView(tiltView)

    }


    override fun onResume() {
        super.onResume()
        /* ### registerListener로 사용할 센서 등록 ###
         첫 번째 인자 : 센서값을 받을 SensorEventListener
         두 번째 인자 : 센서 종류 지정 getDefaultSensor
         세 번째 인자 : 센서 값을 얼마나 자주 받을지 지정
           - SENSOR_DELAY_FASTAST : 가능한 가장 자주 센서값을 얻습니다.
           - SENSOR_DELAY_GAME : 게임에 적합한 정도로 센서값을 얻습니다.
           - SENSOR_DELAY_NORMAL : 화면방향이 전환될 때 적합한 정도로 센서값을 얻습니다.
           - SENSOR_DELAY_UI : 사용자 인터페이스를 표시하기에 적합한 정도로 센서값을 얻습니다.
        */
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }


    override fun onPause() {
        super.onPause()
        // unregisterListener로 센서 사용을 해제한다.
        sensorManager.unregisterListener(this)
    }


    // 센서 값이 변경 되면 호출
    override fun onSensorChanged(event: SensorEvent?) {
        /*
           SensorEvent.values[0] : x 축 값 - 위로 기울이면 -10 ~ 0, 아래로 기울이면 0 ~ 10
           SensorEvent.values[1] : y 축 값 - 왼쪽으로 기울이면 -10 ~ 0, 오른쪽으로 기울이면 0 ~ 10
           SensorEvent.values[2] : z 축 값 - 미사용
        */
        event?.let {
            Log.d("MainActivity"
                ,"onChanged: x: ${event.values[0]}, y: ${event.values[1]}, z: ${event.values[2]}" )

            tiltView.onSensorEvent(event)
        }
    }

    // 센서 정밀도가 변경되면 호출
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}