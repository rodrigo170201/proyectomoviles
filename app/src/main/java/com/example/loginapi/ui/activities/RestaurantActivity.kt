// RestaurantsActivity.kt
package com.example.loginapi.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.databinding.ActivityRestaurantBinding
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.repositories.RetrofitRepository
import com.example.loginapi.ui.adapters.RestaurantAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchRestaurants()
    }

    private fun fetchRestaurants() {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(APIProyecto::class.java)
        service.getRestaurants().enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    val restaurants = response.body() ?: emptyList()
                    setupRecyclerView(restaurants)
                } else {
                    Log.e("RestaurantsActivity", "Failed to fetch restaurants")
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                Log.e("RestaurantsActivity", "Error: ${t.message}", t)
            }
        })
    }

    private fun setupRecyclerView(restaurants: List<Restaurant>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = RestaurantAdapter(restaurants)

    }
}
