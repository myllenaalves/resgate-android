package com.example.resgatemeupet

import Endpoint
import Pets
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FeedActivity: AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null
    private lateinit var pets: Array<Pets?>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed)
        setSupportActionBar(findViewById(R.id.toolbar))
        getData();
    }

    override fun onStart() {
        super.onStart()
        getData();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.func2 -> {
            Firebase.auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun defineAll() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        layoutManager = LinearLayoutManager (this);
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerViewAdapter (pets)
        recyclerView.adapter = adapter
    }

    fun getData(): Unit {
        val retrofitClient = NetworkUtils
                .getRetrofitInstance("https://6086ce0da3b9c200173b6e8e.mockapi.io/api/v1/")

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getPets()

        callback.enqueue(object : Callback<List<Pets>> {
            override fun onFailure(call: Call<List<Pets>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Pets>>, response: Response<List<Pets>>) {
                pets = arrayOf();
                var auxPets: Array<Pets?>;
                response.body()?.forEach {
                    auxPets = pets.copyOf(pets.size + 1);
                    auxPets[auxPets.size-1] = it;
                    pets = auxPets;
                }
                defineAll();
            }
        })
    }


    fun createPost(view: View?) {
        val intent = Intent(this, AddPostActivity::class.java)
        startActivity(intent)
    }

}