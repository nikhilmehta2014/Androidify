package com.nikhil.androidify.codelabs.databinding.util

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter


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