package com.yarchike.occupation

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = readBase()
        var listIgnore = readIgnore()
        val h = Handler(){
            buttonIdea.setVisibility(View.VISIBLE)
            true
        }

        buttonIdea.setOnClickListener() {
            if (list.size <= listIgnore.size) {
                Toast.makeText(this@MainActivity, "Идеи закончились", Toast.LENGTH_LONG).show()
            } else {
                var randomInteger = (0..list.size - 1).shuffled().first().toInt()
                for (i in list) {
                    if (randomInteger in listIgnore) {
                        randomInteger = (0..list.size - 1).shuffled().first().toInt()
                    } else {
                        listIgnore.add(randomInteger)
                        try {
                            val file = File(cacheDir, FILE_NAME)
                            val writer = BufferedWriter(FileWriter(file))
                            var str = ""
                            for (i in listIgnore) {
                                str += i.toString() + ";"
                            }
                            str = str.substring(0, str.length - 1)
                            writer.write(str)
                            writer.close()
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@MainActivity,
                                "Ошибка записи в файл",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        textIdea.text = list[randomInteger]
                        break
                    }
                }
            }
            buttonIdea.setClickable(false)
            buttonIdea.setVisibility(View.INVISIBLE)
            //buttonIdea.setVisibility(View.VISIBLE)
            Thread {
                try {
                    Thread.sleep(3000)
                    buttonIdea.setClickable(true)
                    h.sendEmptyMessage(0)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }.start()

        }


    }

    fun readIgnore(): ArrayList<Int> {
        var arraylist = ArrayList<Int>()
        try {
            val file = File(cacheDir, FILE_NAME)
            val reader = BufferedReader(FileReader(file))
            val line = reader.readLine()
            val array = line.split(";")
            for (i in array) {
                arraylist.add(i.toInt())
            }
        } catch (e: Exception) {

        }
        return arraylist


    }

    fun readBase(): List<String> {
        val str = getString(R.string.base_idea)
        val mass = str.split(";").toList()

        return mass
    }

}