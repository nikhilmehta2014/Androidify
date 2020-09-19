package com.nikhil.androidify.codelabs.databinding.util

import android.view.View
import androidx.databinding.BindingAdapter

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