package com.yarchike.occupation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var idea = ""
        val sharedPreferences = getSharedPreferences(DATE_FILE, Context.MODE_PRIVATE)
        var listIgnore = ArrayList<Int>()
        if (DateUtil.nowDateAfterDate(sharedPreferences)) {
            DateUtil.writeDateToSharePrefrens(sharedPreferences)
            listIgnore = readAndWriteIgnore(true)
        } else {
            listIgnore = readAndWriteIgnore(false)
        }

        val list = readBase()
        val h2 = Handler() {
            when (it.what.toString()) {
                "1" -> {
                    imageView1.setImageResource(R.mipmap.ic_launcher_round);
                }
                "2" -> {
                    imageView2.setImageResource(R.mipmap.ic_launcher_round);
                }
                "3" -> {
                    imageView3.setImageResource(R.mipmap.ic_launcher_round);
                }
                "4" -> {
                    imageView4.setImageResource(R.mipmap.ic_launcher_round);
                }
                "5" -> {
                    imageView5.setImageResource(R.mipmap.ic_launcher_round);
                }
                /*"6" -> {
                    imageView6.setImageResource(R.mipmap.ic_launcher_round);
                }*/
                else -> {
                    textIdea.text = idea
                }


            }

            true
        }

        val h = Handler() {
            textTimeOut.visibility = View.VISIBLE

            textTimeOut.text = "До следушего нажатия: " + it.what.toString()
            //Toast.makeText(this@MainActivity, it.what.toString(), Toast.LENGTH_SHORT).show()
            if (it.what == 0) {
                buttonIdea.visibility = View.VISIBLE
                textTimeOut.visibility = View.INVISIBLE
            }

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
                        readAndWriteIgnore(true, listIgnore)
                        idea = list[randomInteger]
                        break
                    }
                }
            }
            /*imageView1.setImageResource(R.mipmap.ic_launcher_round);
            Thread.sleep(1000)
            imageView2.setImageResource(R.mipmap.ic_launcher_round);
            Thread.sleep(1000)
            imageView3.setImageResource(R.mipmap.ic_launcher_round);
            Thread.sleep(1000)
            imageView4.setImageResource(R.mipmap.ic_launcher_round);
            Thread.sleep(1000)
            imageView5.setImageResource(R.mipmap.ic_launcher_round);*/

            Thread {
                try {
                    h2.sendEmptyMessage(1)
                    Thread.sleep(1000)
                    h2.sendEmptyMessage(2)
                    Thread.sleep(1000)
                    h2.sendEmptyMessage(3)
                    Thread.sleep(1000)
                    h2.sendEmptyMessage(4)
                    Thread.sleep(1000)
                    h2.sendEmptyMessage(5)
                    Thread.sleep(1000)
                    h2.sendEmptyMessage(5)
                    Thread.sleep(1000)
                    h2.sendEmptyMessage(6)
                    h2.sendEmptyMessage(10)


                    h.sendEmptyMessage(3)
                    Thread.sleep(1000)
                    h.sendEmptyMessage(2)
                    Thread.sleep(1000)
                    h.sendEmptyMessage(1)
                    Thread.sleep(1000)
                    h.sendEmptyMessage(0)
                    buttonIdea.setClickable(true)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }.start()

            buttonIdea.isClickable = false
            buttonIdea.visibility = View.INVISIBLE


            /* Thread {
                 try {

                 } catch (e: InterruptedException) {
                     e.printStackTrace()
                 }
             }.start()*/

        }

    }


    fun readAndWriteIgnore(
        write: Boolean,
        listIgnore: ArrayList<Int> = ArrayList<Int>(),
    ): ArrayList<Int> {
        if (write) {
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
                /* Toast.makeText(
                     this@MainActivity,
                     "Ошибка записи в файл",
                     Toast.LENGTH_LONG
                 )
                     .show()*/
            }
        }


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