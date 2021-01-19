package com.timac.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.timac.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityMainBinding

    var player1turn = true
    var roundCount = 0
    var player1Points = 0
    var player2Points = 0
    private val buttons: Array<Array<Button?>> = Array<Array<Button?>>(3) {
        arrayOfNulls<Button>(3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Getting references to all the buttons and set onclick listener
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "btn_$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons[i][j] = findViewById(resID)
                buttons[i][j]?.setOnClickListener(this)
            }
        }

        binding.btnReset.setOnClickListener { resetGame() }

    }

    override fun onClick(v: View?) {
        if ((v as Button).text.toString() != ""){
            return
        }
        if (player1turn){
            v.text = "X"
        } else {
            v.text = "O"
        }

        roundCount++

        if (checkForWin()){
            if (player1turn){
                player1Wins()
            } else {
                player2Wins()
            }
        } else if (roundCount == 9){
            draw()
        } else {
            player1turn = !player1turn
        }
    }

    private fun checkForWin() : Boolean{
        val field = Array(3) { arrayOfNulls<String>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                field[i][j] = buttons[i][j]?.text.toString()
            }
        }
            // Rows
        for (i in 0..2) {
            if (field[i][0].equals(field[i][1])
                and field[i][0].equals(field[i][2])
                and !field[i][0].equals("")
            ) {
                return true
            }
        }
            // Columns
        for (i in 0..2) {
            if (field[0][i].equals(field[1][i])
                and field[0][i].equals(field[2][i])
                and !field[0][1].equals("")
            ) {
                return true
            }
        }
            // Diagonal 1
        if (field[0][0].equals(field[1][1])
            and field[0][0].equals(field[2][2])
            and !field[0][0].equals("")
        ) {
            return true
        }
            //Diagonal 2
        if (field[0][2].equals(field[1][1])
            and field[0][2].equals(field[2][0])
            and !field[0][2].equals("")
        ) {
            return true
        }
        return false
    }

    private fun player1Wins() {
        player1Points++
        Toast.makeText(this, "Player 1 wins", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun player2Wins() {
        player2Points++
        Toast.makeText(this, "Player 2 wins", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun draw() {
        Toast.makeText(this, "Draw !", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    private fun updatePointsText() {
        binding.tvP1.text = "Player 1: $player1Points"
        binding.tvP2.text = "Player 2: $player2Points"
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]!!.text = ""
            }
        }
        roundCount = 0
        player1turn = true
    }

    private fun resetGame() {
        player1Points = 0
        player2Points = 0
        updatePointsText()
        resetBoard()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("roundCount", roundCount)
        outState.putInt("player1points",player1Points)
        outState.putInt("player12points",player2Points)
        outState.putBoolean("player1turn",player1turn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1turn = savedInstanceState.getBoolean("player1turn");
    }
}