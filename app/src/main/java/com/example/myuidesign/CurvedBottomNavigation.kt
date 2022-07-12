package com.example.myuidesign

import android.animation.AnimatorSet
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.Clock
import java.time.Clock.offset

class CurvedBottomNavigation @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet?=null,
    defStyleAttr:Int=0,
):BottomNavigationView(context,attrs, defStyleAttr) {

    companion object {
        private const val TAG = "CurvedBottomNavigation"
        private const val PROPERTY_OFFSET = "OFFSET"
        private const val PROPERTY_CENTER_Y = "CENTER_Y"
       private  val PROPERTY_CENTER_X:Int =0
    }
    private val animatorSet=AnimatorSet()

    //offset of first control point
    private val FAB_RADIUS1:Float=resources.getDimension(com.google.android.material.R.dimen.design_fab_size_normal)
    private val FAB_RADIUS:Float=105f
    private val offset:Int=0
    private val TOP_CONTROL_X=FAB_RADIUS+FAB_RADIUS/2
    private val TOP_CONTROL_Y=FAB_RADIUS/6
    //offset of second control point
    private val BOTTOM_CONTROL_X=FAB_RADIUS+(FAB_RADIUS/2)
    private val BOTTOM_CONTROL_Y=FAB_RADIUS/4

    //width of the curve
    private val CURVE_OFFSET=FAB_RADIUS * 2 + (FAB_RADIUS/6)
    private val TAG="CurvedBottomView"

    //first berier curve
    private val firstCurveStart=PointF()
    private val firstCurveEnd=PointF()
    private val firstCurveControlPoint1=PointF()
    private val firstCurveControlPoint2=PointF()

    //first berier curve
    private val SecondCurveStart=PointF()
    private val SecondCurveEnd=PointF()
    private val SecondCurveControlPoint1=PointF()
    private val SecondCurveControlPoint2=PointF()

    //path of reparent the background including the curve
    private val path:Path=Path()

    private val paint:Paint=Paint().apply {
        style=Paint.Style.FILL_AND_STROKE
        color= Color.WHITE
    }

    init {
        setBackgroundColor(Color.TRANSPARENT)
        labelVisibilityMode = LABEL_VISIBILITY_UNLABELED

    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //first curve
        //set the starting point of the curve (P2)
        firstCurveStart.apply {
            // we want the curve to start at CURVE_OFFSET before the center of the view
            x=(w/2)-CURVE_OFFSET
            y=0f
        }
        //create first P3 point for curve  (P3)
        firstCurveEnd.apply {
            x=(w/2f)
            y=FAB_RADIUS + (FAB_RADIUS/4)
        }
        //set first control point  (C1)
        firstCurveControlPoint1.apply {
            x=firstCurveStart.x + TOP_CONTROL_X
            y=TOP_CONTROL_Y
        }
        //Set C2
        firstCurveControlPoint2.apply {
           x=firstCurveEnd.x-BOTTOM_CONTROL_X
           y=firstCurveEnd.y-BOTTOM_CONTROL_Y
        }

        //second curve start is the same P3

        SecondCurveStart.set(firstCurveEnd.x,firstCurveEnd.y)
        //Set end the curve (P4)
        SecondCurveEnd.apply {
            x=(w/2)+CURVE_OFFSET
            y=0f
        }
        // set first control point second corve in C4
        SecondCurveControlPoint1.apply {
            x=SecondCurveStart.x + BOTTOM_CONTROL_X
            y=SecondCurveStart.y  - BOTTOM_CONTROL_Y
        }
        //set second control point for second curve in C3
        SecondCurveControlPoint2.apply {
            x=SecondCurveEnd.x- TOP_CONTROL_X
            y=TOP_CONTROL_Y
        }

        //clear apy previous path
        path.reset()

        path.moveTo(0f,0f)

        path.lineTo(firstCurveStart.x,firstCurveStart.y)

        path.cubicTo(
            firstCurveControlPoint1.x,
            firstCurveControlPoint1.y,
            firstCurveControlPoint2.x,
            firstCurveControlPoint2.y,
            firstCurveEnd.x,
            firstCurveEnd.y
        )

        path.cubicTo(
            SecondCurveControlPoint1.x,
            SecondCurveControlPoint1.y,
            SecondCurveControlPoint2.x,
            SecondCurveControlPoint2.y,
            SecondCurveEnd.x,
            SecondCurveEnd.y
        )

        path.lineTo(w.toFloat(),0f)
        path.lineTo(w.toFloat(),h.toFloat())
        path.lineTo(0f,h.toFloat())
        path.close()
    }

    val animation=getBezierCovredAnimation(
        2000,
        120
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPath(path,paint)
    }


        //get bezier animation
    private fun getBezierCovredAnimation(
            slideAnimatiomDuration: Long,
            width: Int
        ):ValueAnimator{
        return ValueAnimator().apply {
            val propertyOffSet=PropertyValuesHolder.ofInt(PROPERTY_OFFSET,PROPERTY_CENTER_X)
            setValues(propertyOffSet)
            duration=slideAnimatiomDuration
            addUpdateListener { animator->
                val newOffset=getAnimatedValue(PROPERTY_OFFSET) as Int

                computeCurve(newOffset,width)
                invalidate()
            }

        }
    }

    private val layoutHeight = resources.getDimension(com.google.android.material.R.dimen.design_fab_size_normal)
    private val bottomNavOffsetY =
        layoutHeight - resources.getDimensionPixelSize(
            com.google.android.material.R.dimen.design_fab_size_normal
        )
    private var cellOffsetX: Int = 0
    private val topControlX = FAB_RADIUS + FAB_RADIUS / 2
    private val topControlY = bottomNavOffsetY + FAB_RADIUS / 6
    private val bottomControlX = FAB_RADIUS + (FAB_RADIUS / 2)
    private val bottomControlY = FAB_RADIUS / 4
    private val curveHalfWidth = FAB_RADIUS * 2 + FAB_RADIUS

    private val curveBottomOffset =
        resources.getDimensionPixelSize(com.google.android.material.R.dimen.design_fab_size_normal)

    private fun computeCurve(offsetX: Int, w: Int) {
        // store the current offset (useful when animating)
        this.cellOffsetX = offsetX
        // first curve
        firstCurveStart.apply {
            x = offsetX + (w / 2) - curveHalfWidth
            y = bottomNavOffsetY
        }
        firstCurveEnd.apply {
            x = offsetX + (w / 2f)
            y = layoutHeight - curveBottomOffset
        }
        firstCurveControlPoint1.apply {
            x = firstCurveStart.x + topControlX
            y = topControlY
        }
        firstCurveControlPoint2.apply {
            x = firstCurveEnd.x - bottomControlX
            y = firstCurveEnd.y - bottomControlY
        }

        // second curve
        SecondCurveStart.set(firstCurveEnd.x, firstCurveEnd.y)
        SecondCurveEnd.apply {
            x = offsetX + (w / 2) + curveHalfWidth
            y = bottomNavOffsetY
        }
        SecondCurveControlPoint1.apply {
            x = SecondCurveStart.x + bottomControlX
            y = SecondCurveStart.y - bottomControlY
        }
        SecondCurveControlPoint2.apply {
            x = SecondCurveEnd.x - topControlX
            y = topControlY
        }

        // generate the path
        path.reset()
        path.moveTo(0f, bottomNavOffsetY)
        // horizontal line from left to the start of first curve
        path.lineTo(firstCurveStart.x, firstCurveStart.y)
        // add the first curve
        path.cubicTo(
            firstCurveControlPoint1.x,
            firstCurveControlPoint1.y,
            firstCurveControlPoint2.x,
            firstCurveControlPoint2.y,
            firstCurveEnd.x,
            firstCurveEnd.y
        )
        // add the second curve
        path.cubicTo(
            SecondCurveControlPoint1.x,
            SecondCurveControlPoint1.y,
            SecondCurveControlPoint2.x,
            SecondCurveControlPoint2.y,
            SecondCurveEnd.x,
            SecondCurveEnd.y
        )
        // continue to draw the remaining portion of the bottom navigation
        path.lineTo(width.toFloat(), bottomNavOffsetY)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()
    }

}
