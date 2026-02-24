package mx.itson.cheems

import android.R.attr.button
import android.content.Context
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

class MainActivity : AppCompatActivity(), View.OnClickListener{

   var gameOverCard = 0
    var goodCardsFlipped = 0
    var gameFinished = false

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
        start()

    }

    fun start() {
        goodCardsFlipped = 0
        gameFinished = false
        for (i in 1..9) {
            val btnCard = findViewById<View>(
                resources.getIdentifier("card$i", "id", this.packageName)

            ) as ImageButton
            btnCard.isEnabled = true
            btnCard.setOnClickListener (this)
            btnCard.setBackgroundResource(R.drawable.cheems_question)
        }

        gameOverCard =(1..9).random()

        Log.d("Valor de la carta perdedora", "La carta perdedora es ${gameOverCard.toString()}")

    }

    fun flip(card :Int){

        if (gameFinished) return

        val btnCard = findViewById<View>(
            resources.getIdentifier("card$card", "id", this.packageName)
        ) as ImageButton

        btnCard.isEnabled = false

        if(card == gameOverCard){

            gameFinished = true

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                // Si la versión del sistema operativo instalado en el teléfono es igual o mayor a Android 12 (API 31)
                val vibratorAdmin= applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator= vibratorAdmin.defaultVibrator
                vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE))
            }else{
                val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(1500)
            }
            Toast.makeText(this, "Perdiste :( Intenta otra vez",
                Toast.LENGTH_LONG).show()

            for(i in 1..9) {
                val btnCard = findViewById<View>(
                    resources.getIdentifier("card$i", "id", this.packageName)
                ) as ImageButton


                btnCard.isEnabled = false

                if(i ==card){
                    btnCard.setBackgroundResource(R.drawable.cheems_bad)
                } else {
                    btnCard.setBackgroundResource(R.drawable.cheems_ok)
                }
            }
        } else {

            btnCard.setBackgroundResource(R.drawable.cheems_ok)
            goodCardsFlipped++


            if (goodCardsFlipped == 8) {

                gameFinished = true

                Toast.makeText(this,
                    " ¡Ganaste :D !!",
                    Toast.LENGTH_LONG).show()

                val losingCard = findViewById<View>(
                    resources.getIdentifier("card$gameOverCard", "id", this.packageName)
                ) as ImageButton

                losingCard.setBackgroundResource(R.drawable.cheems_bad)
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
            R.id.restart -> { start()}
        }
    }


}