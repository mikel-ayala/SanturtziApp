package com.g2.santurtziapp.fragments.juegos

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.g2.santurtziapp.Constantes
import com.g2.santurtziapp.R
import com.g2.santurtziapp.TouchListener
import com.g2.santurtziapp.databinding.FragmentJuego1Binding
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min
import kotlin.math.roundToInt


class Juego1Fragment : Fragment() {

    lateinit var binding: FragmentJuego1Binding
    var pieces: ArrayList<Constantes.PuzzlePiece?>?=null
    lateinit var prueba: ConstraintLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJuego1Binding.inflate(layoutInflater)

        prueba =binding.root
        return prueba
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = view.findViewById<RelativeLayout>(R.id.layoutf)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val assetName = "portua2argazkia.JPG"

        // ejecutar el código relacionado con la imagen después de que se haya diseñado la vista
        // para tener calculadas todas las dimensiones

        val handler = Handler()
        handler.postDelayed({
            imageView.post {
                setPicFromAsset(assetName, imageView)
                pieces = splitImage(view)
                val touchListener = TouchListener(this)
                // shuffle pieces order
                Collections.shuffle(pieces)
                for (piece in pieces!!) {
                    piece!!.setOnTouchListener(touchListener)
                    layout.addView(piece)
                    // randomize position, on the bottom of the screen
                    val lParams = piece.layoutParams as RelativeLayout.LayoutParams
                    lParams.leftMargin = Random().nextInt(layout.width - piece.pieceWidth)
                    lParams.topMargin = layout.height - piece.pieceHeight
                    piece.layoutParams = lParams
                }
            }
        }, 500)
    }

    private fun setPicFromAsset(assetName: String, imageView: ImageView) {
        // Obtener las dimensiones de la vista
        val targetW = imageView.width
        val targetH = imageView.height
        val am = activity?.assets
        try {
            val `is` = am?.open("img/$assetName")
            // Obtenemos las dimensiones del bitmap
            val bmOptions = BitmapFactory.Options()
            bmOptions.inJustDecodeBounds = true
            BitmapFactory.decodeStream(`is`, Rect(-1, -1, -1, -1), bmOptions)
            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight

            // Determinar cuánto reducir la imagen
            val scaleFactor = min(photoW / targetW, photoH / targetH)
            `is`!!.reset()

            // Decodifique el archivo de imagen en un tamaño de Bitmap para llenar la vista
            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = scaleFactor
            bmOptions.inPurgeable = true
            val bitmap = BitmapFactory.decodeStream(`is`, Rect(-1, -1, -1, -1), bmOptions)
            imageView.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun splitImage(view: View): ArrayList<Constantes.PuzzlePiece?> {
        //Asignamos el tamaño al puzzle
        val piecesNumber = 20
        val rows = 5
        val cols = 4
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val pieces = ArrayList<Constantes.PuzzlePiece?>(piecesNumber)

        // Obtenga el bitmap escalado de la imagen de origen
        val drawable = imageView.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val dimensions = getBitmapPositionInsideImageView(imageView)
        val scaledBitmapLeft = dimensions[0]
        val scaledBitmapTop = dimensions[1]
        val scaledBitmapWidth = dimensions[2]
        val scaledBitmapHeight = dimensions[3]
        val croppedImageWidth = scaledBitmapWidth - 2 * Math.abs(scaledBitmapLeft)
        val croppedImageHeight = scaledBitmapHeight - 2 * Math.abs(scaledBitmapTop)
        val scaledBitmap =
            Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true)
        val croppedBitmap = Bitmap.createBitmap(
            scaledBitmap,
            Math.abs(scaledBitmapLeft),
            Math.abs(scaledBitmapTop),
            croppedImageWidth,
            croppedImageHeight
        )

        // Calculamos el ancho y alto de las piezas
        val pieceWidth = croppedImageWidth / cols
        val pieceHeight = croppedImageHeight / rows

        // Cree cada pieza del bitmap y agréguela al array resultante
        var yCoord = 0
        for (row in 0 until rows) {
            var xCoord = 0
            for (col in 0 until cols) {
                // calcular la compensacion para cada pieza
                var offsetX = 0
                var offsetY = 0
                if (col > 0) {
                    offsetX = pieceWidth / 3
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3
                }

                // aplicar la compensacion a cada pieza
                val pieceBitmap = Bitmap.createBitmap(
                    croppedBitmap,
                    xCoord - offsetX,
                    yCoord - offsetY,
                    pieceWidth + offsetX,
                    pieceHeight + offsetY
                )
                val piece = Constantes.PuzzlePiece(requireContext())
                piece.setImageBitmap(pieceBitmap)
                piece.xCoord = xCoord - offsetX + imageView.left
                piece.yCoord = yCoord - offsetY + imageView.top
                piece.pieceWidth = pieceWidth + offsetX
                piece.pieceHeight = pieceHeight + offsetY

                // este Bitmap contendrá nuestra imagen final de la pieza del puzzle
                val puzzlePiece = Bitmap.createBitmap(
                    pieceWidth + offsetX,
                    pieceHeight + offsetY,
                    Bitmap.Config.ARGB_8888
                )

                // dibujar la trayectoria
                val bumpSize = pieceHeight / 4
                val canvas = Canvas(puzzlePiece)
                val path = Path()
                path.moveTo(offsetX.toFloat(), offsetY.toFloat())
                if (row == 0) {
                    // tamaño maximo de la pieza
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                } else {
                    // top bump
                    path.lineTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 3).toFloat(),
                        offsetY.toFloat()
                    )
                    path.cubicTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 6).toFloat(),
                        (offsetY - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 6 * 5).toFloat(),
                        (offsetY - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 3 * 2).toFloat(),
                        offsetY.toFloat()
                    )
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                }
                if (col == cols - 1) {
                    // lado derecho de la pieza
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // right bump
                    path.lineTo(
                        pieceBitmap.width.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3).toFloat()
                    )
                    path.cubicTo(
                        (pieceBitmap.width - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6).toFloat(),
                        (pieceBitmap.width - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6 * 5).toFloat(),
                        pieceBitmap.width.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3 * 2).toFloat()
                    )
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                }
                if (row == rows - 1) {
                    // lado alto de la pieza
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // bottom bump
                    path.lineTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 3 * 2).toFloat(),
                        pieceBitmap.height.toFloat()
                    )
                    path.cubicTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 6 * 5).toFloat(),
                        (pieceBitmap.height - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 6).toFloat(),
                        (pieceBitmap.height - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 3).toFloat(),
                        pieceBitmap.height.toFloat()
                    )
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                }
                if (col == 0) {
                    // lado izqiuerdo de la pieza
                    path.close()
                } else {
                    // left bump
                    path.lineTo(
                        offsetX.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3 * 2).toFloat()
                    )
                    path.cubicTo(
                        (offsetX - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6 * 5).toFloat(),
                        (offsetX - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6).toFloat(),
                        offsetX.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3).toFloat()
                    )
                    path.close()
                }

                // mascara de la pieza
                val paint = Paint()
                paint.color = -0x1000000
                paint.style = Paint.Style.FILL
                canvas.drawPath(path, paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                canvas.drawBitmap(pieceBitmap, 0f, 0f, paint)

                // dibujamos borde nblanco
                var border = Paint()
                border.color = -0x7f000001
                border.style = Paint.Style.STROKE
                border.strokeWidth = 8.0f
                canvas.drawPath(path, border)

                // dibujamos borde negro de la pieza
                border = Paint()
                border.color = -0x80000000
                border.style = Paint.Style.STROKE
                border.strokeWidth = 3.0f
                canvas.drawPath(path, border)

                // Seleccionar el Bitmap resultante de la pieza
                piece.setImageBitmap(puzzlePiece)
                pieces.add(piece)
                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }
        return pieces
    }

    private fun getBitmapPositionInsideImageView(imageView: ImageView?): IntArray {
        val ret = IntArray(4)
        if (imageView == null || imageView.drawable == null) return ret

        // Obtener la dimension de la imagen
        // Obtenga valores de matriz de imagen y colóquelos en un array
        val f = FloatArray(9)
        imageView.imageMatrix.getValues(f)

        // Extraiga los valores de escala usando las constantes (si se mantiene la relación de aspecto, scaleX == scaleY)
        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        //Obtenga el dibujable (también podría obtener el mapa de bits detrás del dibujable y getWidth/getHeight)
        val d = imageView.drawable
        val origW = d.intrinsicWidth
        val origH = d.intrinsicHeight

        // Calcular las dimensiones actuales
        val actW = (origW * scaleX).roundToInt()
        val actH = (origH * scaleY).roundToInt()
        ret[2] = actW
        ret[3] = actH

        // Obtener la posicion de la imagen
        // Suponemos que la imagen está centrada en ImageView
        val imgViewW = imageView.width
        val imgViewH = imageView.height
        val top = (imgViewH - actH) / 2
        val left = (imgViewW - actW) / 2
        ret[0] = left
        ret[1] = top
        return ret
    }

    fun checkGameOver()  {
        if (isGameOver) {
            //Meter aqui lo que cargara cuando se complete el juego
            MediaPlayer.create(requireContext(), R.raw.ondo).start()

            setFragmentResult("juego1", bundleOf("terminado" to "yes"))
        }
    }

    private val isGameOver: Boolean
        get() {
            for (piece in pieces!!) {
                if (piece!!.canMove) {
                    return false
                }
            }
            return true
        }
}