package mx.itson.cheems

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.itson.cheems.entities.Winner

class WinnerFormActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_winner_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSave = findViewById<View>(R.id.btn_save) as Button
        btnSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_save -> {
                val name = findViewById<EditText>(R.id.text_name).text.toString()
                val nickname = findViewById<EditText>(R.id.text_nickname).text.toString()

            //    Winner().save(this, name, nickname)

                try {

                    val result = Winner().save(this, name, nickname)

                    if(result){
                        Toast.makeText(
                            this,
                            getString(R.string.save_ok),
                            Toast.LENGTH_LONG
                        ).show()

                        setResult(RESULT_OK)
                        finish()

                    }else{
                        Toast.makeText(
                            this,
                            getString(R.string.save_err),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }catch (ex: Exception){

                    Toast.makeText(
                        this,
                        getString(R.string.save_err),
                        Toast.LENGTH_LONG
                    ).show()
                }


            }
        }
    }
}