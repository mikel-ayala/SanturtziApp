package com.g2.santurtziapp

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.g2.santurtziapp.fragments.juegos.Juego1Fragment
import kotlin.math.pow
import kotlin.math.sqrt

class TouchListener(private val fragment: Juego1Fragment) : View.OnTouchListener {

    private var xDelta = 0f
    private var yDelta = 0f

    //
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {

        val x = motionEvent.rawX
        val y = motionEvent.rawY
        val tolerance = sqrt(view.width.toDouble().pow(2.0) + view.height.toDouble().pow(2.0)) / 10
        val piece = view as Constantes.PuzzlePiece

        if (!piece.canMove) {

            return true

        }//if (!piece.canMove)

        val lParams = view.getLayoutParams() as RelativeLayout.LayoutParams

        when (motionEvent.action and MotionEvent.ACTION_MASK) {

            MotionEvent.ACTION_DOWN -> {

                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin

                piece.bringToFront()

            }//action_down

            MotionEvent.ACTION_MOVE -> {

                lParams.leftMargin = (x - xDelta).toInt()
                lParams.topMargin = (y - yDelta).toInt()

                view.setLayoutParams(lParams)

            }//action_move

            MotionEvent.ACTION_UP -> {

                val xDiff = StrictMath.abs(piece.xCoord - lParams.leftMargin)
                val yDiff = StrictMath.abs(piece.yCoord - lParams.topMargin)

                if (xDiff <= tolerance && yDiff <= tolerance) {

                    lParams.leftMargin = piece.xCoord
                    lParams.topMargin = piece.yCoord

                    piece.layoutParams = lParams
                    piece.canMove = false

                    sendViewToBack(piece)

                    fragment.checkGameOver()

                }//if (xDiff <= tolerance && yDiff <= tolerance)

            }//action_up

        }//when (motionEvent.action and MotionEvent.ACTION_MASK)

        return true

    }//onTouch(view: View, motionEvent: MotionEvent): Boolean

    //
    fun sendViewToBack(child: View) {

        val parent = child.parent as ViewGroup

        parent.removeView(child)
        parent.addView(child, 0)

    }//sendViewToBack(child: View)

}//TouchListener(private val fragment: Juego1Fragment) : View.OnTouchListener