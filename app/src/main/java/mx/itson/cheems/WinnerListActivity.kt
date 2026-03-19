package mx.itson.cheems

import adapters.WinnerAdapter
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.itson.cheems.entities.Winner

class WinnerListActivity : AppCompatActivity() {

    var listWinners: ListView? = null
    var context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_winner_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listWinners = findViewById(R.id.list_winners)
        loadWinners()

    }

    fun loadWinners(){
        val winners = Winner().getAll(this)
        listWinners?.adapter = WinnerAdapter(context, winners)
    }

}