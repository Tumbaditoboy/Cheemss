package mx.itson.cheems

import android.R.attr.button
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.itson.cheems.entities.Winner

class MainActivity : AppCompatActivity(), View.OnClickListener{

   var gameOverCard = 0
    var goodCardsFlipped = 0
    var gameFinished = false

    var master = 0

    val REQUEST_WINNER = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val btnRestart = findViewById<Button>(R.id.restart)
        btnRestart.setOnClickListener(this)

     //   Winner().save(this, "Pedro Robles","pedrin" )
      //  Winner().getAll(this)
        start()

        val btnNewWinner = findViewById<View>(R.id.btn_new_winner) as Button
        btnNewWinner.setOnClickListener(this)

        val btnListWinner = findViewById<View>(R.id.btn_list_winner) as Button
        btnListWinner.setOnClickListener(this)

    }

    fun vib(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            // Si la versión del sistema operativo instalado en el teléfono es igual o mayor a Android 12 (API 31)
            val vibratorAdmin= applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator= vibratorAdmin.defaultVibrator
            vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE))
        }else{
            val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(1500)
        }
    }
    fun start() {
        goodCardsFlipped = 0
        gameFinished = false

        val btnWinner = findViewById<Button>(R.id.btn_new_winner)
        val textWinner = findViewById<View>(R.id.text_win)

        btnWinner.visibility = View.GONE
        textWinner.visibility = View.GONE

        for (i in 1..12) {
            val btnCard = findViewById<View>(
                resources.getIdentifier("card$i", "id", this.packageName)

            ) as ImageButton
            btnCard.isEnabled = true
            btnCard.setOnClickListener (this)
            btnCard.setBackgroundResource(R.drawable.cheems_question)
        }

        gameOverCard =(1..12).random()

        Log.d("Valor de la carta perdedora", "La carta perdedora es ${gameOverCard.toString()}")

        master =(1..12).random()

        Log.d("Valor de la carta master", "La carta master es ${master.toString()}")

     /*   gameOverCard = (1..12).random()

        do {
            master = (1..12).random()
        } while (master == gameOverCard) */



        Toast.makeText(this,
            getString(R.string.Welcome),
            Toast.LENGTH_LONG).show()



    }

    fun flip(card :Int){

        if (gameFinished) return

        val btnCard = findViewById<View>(
            resources.getIdentifier("card$card", "id", this.packageName)
        ) as ImageButton

        btnCard.isEnabled = false



        if(card == gameOverCard){

            gameFinished = true
            vib()

            Toast.makeText(this, getString(R.string.Lose),
                Toast.LENGTH_LONG).show()

            for(i in 1..12) {
                val btnCard = findViewById<View>(
                    resources.getIdentifier("card$i", "id", this.packageName)
                ) as ImageButton


                btnCard.isEnabled = false

                if(i ==card){
                    btnCard.setBackgroundResource(R.drawable.cheems_bad)
                } else if(i == master) {
                    btnCard.setBackgroundResource(R.drawable.cheems_master)
                } else {
                    btnCard.setBackgroundResource(R.drawable.cheems_ok)
                }
            }
        }   else if(card == master){
            gameFinished = true
            vib()

            Toast.makeText(this,
                getString(R.string.MasterWin),
                Toast.LENGTH_LONG).show()

            //
            findViewById<Button>(R.id.btn_new_winner).visibility = View.VISIBLE
            findViewById<View>(R.id.text_win).visibility = View.VISIBLE

            for(i in 1..12) {
                val btnCard = findViewById<View>(
                    resources.getIdentifier("card$i", "id", this.packageName)
                ) as ImageButton


                btnCard.isEnabled = false

                if(i ==card){
                    btnCard.setBackgroundResource(R.drawable.cheems_master)
                } else if (i == gameOverCard){
                    btnCard.setBackgroundResource(R.drawable.cheems_bad)
                } else {
                    btnCard.setBackgroundResource(R.drawable.cheems_ok)
                }
            }


        } else {

            btnCard.setBackgroundResource(R.drawable.cheems_ok)
            goodCardsFlipped++


            if (goodCardsFlipped == 11) {

                //
                findViewById<Button>(R.id.btn_new_winner).visibility = View.VISIBLE
                findViewById<View>(R.id.text_win).visibility = View.VISIBLE
                gameFinished = true
                vib()

                Toast.makeText(this,
                    getString(R.string.WIn),
                    Toast.LENGTH_LONG).show()



                val losingCard = findViewById<View>(
                    resources.getIdentifier("card$gameOverCard", "id", this.packageName)
                ) as ImageButton

                losingCard.setBackgroundResource(R.drawable.cheems_bad)
                losingCard.isEnabled = false

                val masterCard = findViewById<View>(
                    resources.getIdentifier("card$master", "id", this.packageName)
                ) as ImageButton

                masterCard.setBackgroundResource(R.drawable.cheems_master)
                losingCard.isEnabled = false
            }


        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.card1 -> { flip(card = 1)}
            R.id.card2 -> { flip(card = 2)}
            R.id.card3 -> { flip(card = 3)}
            R.id.card4 -> { flip(card = 4)}
            R.id.card5 -> { flip(card = 5)}
            R.id.card6 -> { flip(card = 6)}
            R.id.card7 -> { flip(card = 7)}
            R.id.card8 -> { flip(card = 8)}
            R.id.card9 -> { flip(card = 9)}
            R.id.card10 -> { flip(card = 10)}
            R.id.card11 -> { flip(card = 11)}
            R.id.card12 -> { flip(card = 12)}
            R.id.restart -> { start()}
            R.id.btn_new_winner -> {
                val intentWinnerForm = Intent(this, WinnerFormActivity::class.java)
                startActivityForResult(intentWinnerForm, REQUEST_WINNER)
            }
            R.id.btn_list_winner -> {
                val intentWinnerList = Intent(this, WinnerListActivity::class.java)
                startActivity(intentWinnerList)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_WINNER && resultCode == RESULT_OK){

            val btnWinner = findViewById<Button>(R.id.btn_new_winner)
            val textWinner = findViewById<View>(R.id.text_win)

            btnWinner.visibility = View.GONE
            textWinner.visibility = View.GONE
        }
    }


}