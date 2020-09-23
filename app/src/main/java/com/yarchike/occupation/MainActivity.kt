package com.yarchike.occupation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = readBase()
        var listIgnore = ArrayList<Int>()

        buttonIdea.setOnClickListener() {
            var randomInteger = (1..list.size - 1).shuffled().first().toInt()
            for(i in list) {
                if (randomInteger in listIgnore) {
                    randomInteger = (1..list.size - 1).shuffled().first().toInt()
                } else {
                    listIgnore.add(randomInteger)
                    textIdea.text = list[randomInteger]
                    break
                }
                Log.d("my", randomInteger.toString())
            }
        }
        buttonTest.setOnClickListener(){
            val text = textIdea.text.toString()
            val fos = openFileOutput(FILE_NAME, MODE_PRIVATE)
            fos.write(text.toByteArray())
        }
    }



    fun readBase(): List<String> {
        val str = getString(R.string.base_idea)
        val mass = str.split(";").toList()

        return mass
    }

}