package com.example.resgatemeupet

import Endpoint
import Pets
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

    class RecyclerViewAdapter(private val pets: Array<Pets?>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardText : TextView
        val cardNumber : TextView
        val cardDescription : TextView
        val cardImage: ImageView

        init {
            cardText = itemView.findViewById(R.id.textTitle)
            cardNumber = itemView.findViewById(R.id.textNumber)
            cardDescription = itemView.findViewById(R.id.textDescription)
            cardImage = itemView.findViewById(R.id.cardImage)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardText.text = pets[position]?.title.toString()
        holder.cardNumber.text = "Número: " + pets[position]?.number.toString()
        holder.cardDescription.text = "Descrição: " + pets[position]?.description.toString()

        Picasso.get().load(pets[position]?.image.toString())
            .resize(100, 100) // resizes the image to these dimensions.
            .into(holder.cardImage);
    }

    override fun getItemCount(): Int {
        return pets.size
    }
}


