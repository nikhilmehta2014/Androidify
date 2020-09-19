package com.nikhil.androidify.codelabs.databinding.basicsample.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.nikhil.androidify.R
import com.nikhil.androidify.codelabs.databinding.basicsample.Popularity

const val MAX_LIKES_TO_FILL_PROGRESS_BAR = 5
/**
 * In Kotlin, static methods can be created by adding functions to the top level of a Kotlin file or as extension functions on the class.
 */


/**
 * With the Data Binding Library, almost all UI calls are done in static methods called Binding Adapters -> [BindingAdapter].
 *
 * The library provides a huge amount of Binding Adapters. Here's an example for the android:text attribute:
 * <code>
 *     @BindingAdapter("android:text")
 *     public static void setText(TextView view, CharSequence text) {
 *     // Some checks removed for clarity
 *     view.setText(text);
 *     }
 * </code>
 *
 * There's no magic in Data Binding.
 * Everything is resolved at compilation time and it's accessible for you to read in the generated code.
 */

/**
 * This binding adapter:
 * 1. applies to the app:hideIfZero attribute.
 * 2. can be applied to every View (since the first parameter is a View; you can restrict to certain classes by changing this type)
 * 3. takes an Integer that should be what the layout expression returns.
 * 4. makes the View GONE if the number is zero. VISIBLE otherwise.
 */
@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}

/**
 *  Sets the value of the progress bar so that 5 likes will fill it up.
 *
 *  Showcases Binding Adapters with multiple attributes.
 *  Note that this adapter is called whenever any of the attribute changes.
 *
 *  This Binding Adapter is not used if any of the attributes are missing.
 *  This happens at compile time.
 *  The method takes 3 parameters now (the view it applies to plus the number of attributes defined in the annotation).
 *
 *  The requireAll parameter defines when the binding adapter is used:
 *  1. When true, all elements must be present in the XML definition.
 *  2. When false, the missing attributes will be null, false if booleans, or 0 if primitives.
 */
@BindingAdapter(value = ["app:progressScaled", "android:max"], requireAll = true)
fun setProgress(progressBar: ProgressBar, likes: Int, max: Int) {
    progressBar.progress = (likes * max / MAX_LIKES_TO_FILL_PROGRESS_BAR).coerceAtMost(max)
}

/**
 * A Binding Adapter that is called whenever the value of the attribute `app:popularityIcon` changes.
 * Receives a popularity level that determines the icon and tint color to use.
 */
@BindingAdapter("app:popularityIcon")
fun popularityIcon(view: ImageView, popularity: Popularity) {
    val color = getAssociatedColor(popularity, view.context)
    ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(color))
    view.setImageDrawable(getDrawablePopularity(popularity, view.context))
}

/**
 * A Binding Adapter that is called whenever the value of the attribute `android:progressTint` changes.
 * Depending on the value it determines the color of the progress bar.
 */
@SuppressLint("ObsoleteSdkInt")
@BindingAdapter("app:progressTint")
fun tintPopularity(view: ProgressBar, popularity: Popularity) {
    val color = getAssociatedColor(popularity, view.context)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        view.progressTintList = ColorStateList.valueOf(color)
    }
}

private fun getAssociatedColor(popularity: Popularity, context: Context): Int {
    return when (popularity) {
        Popularity.NORMAL -> context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorForeground)).getColor(0, 0x000000)
        Popularity.POPULAR -> ContextCompat.getColor(context, R.color.popular)
        Popularity.STAR -> ContextCompat.getColor(context, R.color.star)
    }
}

private fun getDrawablePopularity(popularity: Popularity, context: Context): Drawable? {
    return when (popularity) {
        Popularity.NORMAL -> {
            ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
        }
        Popularity.POPULAR -> {
            ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
        }
        Popularity.STAR -> {
            ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
        }
    }
}