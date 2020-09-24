package com.yarchike.occupation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = readBase()
        var listIgnore = readIgnore()
        val h = Handler() {
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
                        write(listIgnore)
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
        btest.setOnClickListener() {
            /*val dateFormat = "MM/dd/yyyy HH:mm:ss"
            var sdf = SimpleDateFormat(dateFormat)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            var date = sdf.format(Date())*/
            var date = Calendar.getInstance(TimeZone.getTimeZone(" UTC+0"))
            date.add(Calendar.DAY_OF_MONTH, 1)
            date.set(Calendar.HOUR_OF_DAY, 0)
            date.set(Calendar.MINUTE, 0)
            date.set(Calendar.SECOND, 0)
            date.set(Calendar.MILLISECOND, 0)
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            var dateString = sdf.format(date.time)
            textIdea.text = dateString
            getSharedPreferences(DATE_FILE, Context.MODE_PRIVATE)
                .edit()
                .putString(DATE_INPUT, dateString)
                .apply()

        }

        button2.setOnClickListener() {
            val dateString = getSharedPreferences(DATE_FILE, Context.MODE_PRIVATE).getString(
                DATE_INPUT, ""
            )
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            val dateD = sdf.parse(dateString)
            var date = Calendar.getInstance();
            date.time = dateD
            val string1 = sdf.format(date.time)
            textView.text = dateString

            val nowDate = Calendar.getInstance(TimeZone.getTimeZone(" UTC+0"))
            val string2 = sdf.format(nowDate.time)
            textIdea.text =  string2
            if (date.after(nowDate)){
                textIdea.text = "НЕ Стереть" + string1 + "---" + string2
            }else{
                textIdea.text ="Стереть" + string1 + "---" + string2
            }
        }


    }
    

    private fun write(listIgnore: ArrayList<Int>) {
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