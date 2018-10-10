package divascion.marfiandhi.footballapps.utils

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView

/**
 * Created by Marfiandhi on 10/6/2018.
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun snackBarShow(view: View, message: String) {
    val snack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
    snack.view.setBackgroundColor(Color.BLACK)
    val snackText = snack.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
    snackText.setTextColor(Color.LTGRAY)
    snack.show()
}