package com.nikhil.androidify.codelabs.databinding

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nikhil.androidify.R

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

        setContentView(R.layout.activity_basic_sample)

        // TODO: Explicitly setting initial values is a bad pattern. We'll fix that.
        updateName()
        updateLikes()
    }

    /**
     * This method is triggered by the `android:onclick` attribute in the layout. It puts business
     * logic in the activity, which is not ideal. We should do something about that.
     */
    fun onLike(view: View) {
        viewModel.onLike()
        updateLikes()
    }

    /**
     * So much findViewById! We'll fix that with Data Binding.
     */
    private fun updateName() {
        findViewById<TextView>(R.id.plain_name).text = viewModel.name
        findViewById<TextView>(R.id.plain_lastname).text = viewModel.lastName
    }

    /**
     * This method has many problems:
     * - It's calling findViewById multiple times
     * - It has untestable logic
     * - It's updating two views when called even if they're not changing
     */
    private fun updateLikes() {
        findViewById<TextView>(R.id.likes).text = viewModel.likes.toString()
        findViewById<ProgressBar>(R.id.progressBar).progress =
            (viewModel.likes * 100 / 5).coerceAtMost(100)
        val image = findViewById<ImageView>(R.id.imageView)

        val color = getAssociatedColor(viewModel.popularity, this)

        ImageViewCompat.setImageTintList(image, ColorStateList.valueOf(color))

        image.setImageDrawable(getDrawablePopularity(viewModel.popularity, this))
    }

    private fun getAssociatedColor(popularity: Popularity, context: Context): Int {
        return when (popularity) {
            Popularity.NORMAL -> context.theme.obtainStyledAttributes(
                intArrayOf(android.R.attr.colorForeground)
            ).getColor(0, 0x000000)
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
}