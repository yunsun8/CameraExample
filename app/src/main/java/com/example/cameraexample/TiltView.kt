package com.example.cameraexample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {

    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()

    private var cX: Float = 0f
    private var cY: Float = 0f

    private var xCoord: Float = 0f
    private var yCoord: Float = 0f

    init {
        // 녹색 페인트
        greenPaint.color = Color.GREEN

        // 검은색 테두리 페인트
        /*
            - FILL : 색을 채움. 획 관련된 설정을 무시
            - FILL_AND_STROKE : 획과 관련된 설정을 유지하면서 책을 채움
            - STROKE : 화 관련 설정을 유지하여 외관선만 그림
         */
        blackPaint.style = Paint.Style.STROKE
    }

    // 뷰의 크기가 변경될 때마다 호출
    /*
        - w : 변경된 가로 길이
        - h : 변경된 세로 길이
        - oldw : 변경 전 가로 길이
        - oldh : 변경 전 세로 길이
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w / 2f
        cY = h / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        // 바깥 원
        canvas?.drawCircle(cX, cY, 100f, blackPaint)

        // 녹색 원
        canvas?.drawCircle(xCoord + cX, yCoord + cY, 100f, greenPaint)

        // 가운데 십자가
        canvas?.drawLine(cX - 20, cY, cX + 20, cY, blackPaint)
        canvas?.drawLine( cX, cY - 20 , cX, cY + 20, blackPaint)
    }

    // MainActivity 에서 호출하여 View에 반영되도록 함수 작성
    // onSensorChanged 함수에서 호출됨
    fun onSensorEvent( event: SensorEvent) {
        // 화면을 가로로 돌렸으므로 X축과 Y축을 서로 바꿈
        xCoord = event.values[0] * 20
        yCoord = event.values[1] * 20
        invalidate()
    }

}