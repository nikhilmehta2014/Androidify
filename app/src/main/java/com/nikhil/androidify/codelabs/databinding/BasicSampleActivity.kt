package com.nikhil.androidify.codelabs.databinding

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.nikhil.androidify.R
import com.nikhil.androidify.databinding.ActivityBasicSampleBinding

/**
 * The Data Binding Library is an Android Jetpack library that allows you to bind UI components in your XML layouts to "data sources" in your app
 * using a "declarative format" rather than programmatically, reducing boilerplate code.
 *
 * This CodeLab showcases following examples:
 * 1. Static data binding
 * 2. Observable data binding
 * 3. Custom [srcCompat] Binding Method
 * 4. Provided [text] Binding Adapter
 * 5. Custom [progressTint] Binding Adapter
 */

/**
 * UI implementation found in the PlainOldActivity class has a number of problems:
 * 1. It calls [findViewById] multiple times. Not only is this slow, it's not safe because it's not checked at compile time.
 * If the ID you pass to findViewById() is wrong, the app will crash at run time.
 *
 * 2. It sets initial values in [onCreate]. It'd be much better to have good defaults that are set automatically.
 *
 * 3. It uses the [onClick] attribute in the Button element of the XML layout declaration, which is not safe either:
 * If the [onLike] method is not implemented in your activity (or is renamed), the app will crash at run time.
 *
 * 4. It has a lot of code. [Activity] and [Fragment] tend to grow very quickly, so it's a good idea to move as much code as possible out of them.
 * Also, code in activities and fragments is hard to test and maintain.
 *
 * With the "Data Binding Library" you can fix all of these problems by moving logic out of the activity into places where it's reusable and easier to test.
 */
class BasicSampleActivity : AppCompatActivity() {

    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(BasicSampleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * You'll need [binding] variable to set those layout variables you declared in the <data> block.
         *
         * Binding classes are generated automatically by the library.
         */
        val binding: ActivityBasicSampleBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_basic_sample)

        /**
         * [LiveData] is a lifecycle-aware observable so you need to specify what lifecycle owner to use.
         */
        binding.lifecycleOwner=this

        /**
         * Instead os using multiple [String]s in the XML, we can define a single [ViewModel] instance.
         * Then, instantiate it with [ViewModel] reference in the [View]
         */
        binding.viewModel=viewModel
    }
}