package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_roll.setOnClickListener {
            rollDice();
        }
        rollDice();
    }

    private fun rollDice() {
        val dice = Dice(6)
        val resultRoll = dice.roll()
        when(resultRoll) {
            1 -> surface_dice.setImageResource(R.drawable.dice_1)
            2 -> surface_dice.setImageResource(R.drawable.dice_2)
            3 -> surface_dice.setImageResource(R.drawable.dice_3)
            4 -> surface_dice.setImageResource(R.drawable.dice_4)
            5 -> surface_dice.setImageResource(R.drawable.dice_5)
            6 -> surface_dice.setImageResource(R.drawable.dice_6)
        }
        surface_dice.contentDescription = resultRoll.toString()
    }
}

class Dice(val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random();
    }
}