package com.example.desertclicker

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.example.desertclicker.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val KEY_REVENUE = "revenue_key"
        const val KEY_DESSERT_SOLD = "dessert_sold_key"
    }
    private var totalRevenue = 0
    private var dessertsSold = 0


    //store all the views here
    private lateinit var binding: ActivityMainBinding

    /**
     * creating a data class that holds dessert image, it's price and production amount
     */
    data class Dessert(val imageId:Int, val price:Int, val productionAmt:Int)


    //list of desserts in order
    private val allDessert = listOf(
        Dessert(R.drawable.cupcake,5,0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmallow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 16000),
        Dessert(R.drawable.oreo, 6000, 20000)
    )

    private var currentDessert = allDessert[0]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //using binding to get reference to views
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.dessertBtn.setOnClickListener{
            onDessertCLicked()
        }
        //restores revenue and number of desserts sold when device orientation is changed
        if (savedInstanceState != null) {
            totalRevenue = savedInstanceState.getInt(KEY_REVENUE, 0)
            dessertsSold = savedInstanceState.getInt(KEY_DESSERT_SOLD, 0)
            showCurrentDessert()
        }

        // Set the TextViews to the right values
        binding.revenue = totalRevenue
        binding.amountSold = dessertsSold

        // Make sure the correct dessert is showing
        binding.dessertBtn.setImageResource(currentDessert.imageId)
        Log.d(TAG, "onCreate Called")



    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d(TAG, "onSaveInstanceState Called")
        outState.putInt(KEY_REVENUE, totalRevenue)
        outState.putInt(KEY_DESSERT_SOLD, dessertsSold)
    }

    private fun onDessertCLicked() {
        //updating values after desserts being sold
        totalRevenue += currentDessert.price
        dessertsSold++

        //changes the current revenue into the UI
        binding.revenue = totalRevenue
        binding.amountSold = dessertsSold

        //showing next dessert
        showCurrentDessert()
    }

    /**
     * determines which desert to display
     */
    private fun showCurrentDessert() {
        var newDessert = allDessert[0]
        for (dessert in allDessert)
        {
            if(dessertsSold >= dessert.productionAmt)
            {
                newDessert = dessert
            }
            else break
        }
        //update the current dessert image if the new dessert is different
        if(newDessert != currentDessert)
        {
            currentDessert = newDessert
            binding.dessertBtn.setImageResource(newDessert.imageId)
        }
    }

    /**
     * adding the share menu
     */
    private fun onShare()
    {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_msg, dessertsSold, totalRevenue))
            .setType("text/plain").intent
        try{
            startActivity(shareIntent)
        }
        catch(ex: ActivityNotFoundException)
        {
            Toast.makeText(this,"Sharing Not Available",Toast.LENGTH_LONG).show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //calls onShare() method when share button is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }




}

