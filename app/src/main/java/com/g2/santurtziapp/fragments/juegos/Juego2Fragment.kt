package com.g2.santurtziapp.fragments.juegos

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.g2.santurtziapp.Constantes
import com.g2.santurtziapp.R
import kotlinx.android.synthetic.main.fragment_juego2.*

class Juego2Fragment : Fragment() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<Constantes.MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    private var contador = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_juego2, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed(Runnable {

            val images = mutableListOf(R.drawable.pareja11, R.drawable.pareja12, R.drawable.pareja21, R.drawable.pareja22, R.drawable.pareja31, R.drawable.pareja32, R.drawable.pareja41, R.drawable.pareja42, R.drawable.pareja51, R.drawable.pareja52)
            // Add each image twice so we can create pairs
            // Randomize the order of images
            images.shuffle()

            buttons = listOf(imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, imageButton8, imageButton9, imageButton10)


            cards = buttons.indices.map { index ->
                Constantes.MemoryCard(images[index])
            }


            buttons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    Log.i(Constantes.TAG, "button clicked!!")
                    // Update models
                    updateModels(index)
                    // Update the UI for the game
                    updateViews()
                }
            }
        }, 500)
    }
    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier  else R.drawable.logostz)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        // Error checking:
        if (card.isFaceUp) {
            return
        }
        // Three cases
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over => restore cards + flip over the selected card
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 selected cards previously
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            // exactly 1 card was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp

    }

    private fun restoreCards() {
        for (card in cards ) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        println(position1)
        if(comprobarpar(cards[position1].identifier) == cards[position2].identifier){
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            contador++
        }
        if (contador == 5){
            MediaPlayer.create(requireContext(), R.raw.ondo).start()
            setFragmentResult("juego2", bundleOf("terminado" to "yes"))
        }
    }
    private fun comprobarpar(idnt: Int): Int {
        var identificador:Int
        if(cards[0].identifier%2 == 0){
            if (idnt%2 == 0){
                identificador = idnt + 1
            }else{
                identificador = idnt - 1
            }
        }else{
            if (idnt%2 == 0){
                identificador = idnt - 1
            }else{
                identificador = idnt + 1
            }
        }
        return identificador
    }

}

