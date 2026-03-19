package adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.itson.cheems.entities.Winner
import mx.itson.cheems.R

class WinnerAdapter (
    context : Context,
    winners :List<Winner>
) : BaseAdapter() {

    var context : Context = context
    var winnerList : List<Winner> = winners


    override fun getCount(): Int {
        return winnerList.size
    }

    override fun getItem(position: Int): Any {
       return winnerList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val element = LayoutInflater.from(context).inflate(R.layout.winner_item, null)

        try{
            val winner = getItem(position) as Winner

            val txtName : TextView = element.findViewById(R.id.winner_name)
            txtName.text = winner.name

            val txtNickName : TextView = element.findViewById(R.id.winner_nickname)
            txtNickName.text = winner.nickname

        } catch (ex: Exception){
            Log.e("Error showing winners", ex.message.toString())
        }

        return element
    }



}