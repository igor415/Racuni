package hr.tvz.android.racuni.shared

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast



fun Context?.toast(text: String){
    Toast.makeText(this,text,Toast.LENGTH_LONG).show()
}

inline fun <reified T: Any> Context.getIntent() = Intent(this,T::class.java)

inline fun <reified T: Any> Context.startActivity() = startActivity(getIntent<T>())

val Context.inflater: LayoutInflater
get() = LayoutInflater.from(this)

fun String.vratiStringSaDvijeDecimale(): String{
    val it_split = this.split(".")
    if(it_split[1].length>1){
        val decimale = it_split[1].substring(0,2)
        return "${it_split[0]}.${decimale} kn"
    }else{
        return "$this kn"
    }

}

