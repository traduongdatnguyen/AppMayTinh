package com.example.appmaytinh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
     fun numberAction(view: View) {
        if (view is Button) viewTV.append(view.text)
        else viewTV.text = "0"
    }
    fun clearAllnumber(view: View)
    {
        viewTV.text = ""
        resultTV.text=""
    }
    fun clearnumberlength(view:View){
        var length = viewTV.length()
        if (length > 0) viewTV.text = viewTV.text.subSequence(0, length - 1)
    }
    fun equasResult(view: View){
        resultTV.text = calculateResults()

    }

    private fun calculateResults():String{
        var chuyendoichuoi = chuyendoichuoi()

        var tinhnhanchia = tinhnhanchia(chuyendoichuoi)

        var tinhtonghieu = tinhtonghieu(tinhnhanchia)

        return tinhtonghieu.toString()
    }

    private fun tinhnhanchia(chuyendoichuoi: ArrayList<Any>): ArrayList<Any> {
        var newlist = chuyendoichuoi
            while (newlist.contains('*') || newlist.contains('/')){
                newlist = thuantoantinhnhanchia(newlist)
            }
        return newlist
    }

    private fun thuantoantinhnhanchia(newlist: ArrayList<Any>): ArrayList<Any> {
        var list = arrayListOf<Any>()
        var restartIndex = newlist.size

        for(i in newlist.indices){
            if (newlist[i] is Char && i != newlist.lastIndex && i < restartIndex ) {
                var operator = newlist[i]
                val prevDigit = newlist[i - 1] as Float
                val nextDigit = newlist[i + 1] as Float
                when (operator) {
                    '*' -> {
                        list.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        list.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        list.add(prevDigit)
                        list.add(operator)
                    }
                }
            }
            if(i > restartIndex)
                list.add(newlist[i])
        }

        return list
    }

    private fun tinhtonghieu(listString: ArrayList<Any>): Float {
        var result = listString[0] as Float

        for(i in listString.indices){
            if (listString[i] is Char && i != listString.lastIndex ){
                val operator = listString[i]
                val nextDigit = listString[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }

    private fun chuyendoichuoi(): ArrayList<Any> {
        var list = arrayListOf<Any>()
        var addstring = ""
        for(character in viewTV.text){
            //Trả về true khi nó là một số
            if (character.isDigit() || character == '.'){
                addstring += character
            }else{
                list.add(addstring.toFloat())
                addstring =""
                list.add(character)
            }

        }
        if (addstring != "") list.add(addstring.toFloat())
        return list
    }


}