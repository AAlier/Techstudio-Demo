package com.techstudio.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.techstudio.R
import com.techstudio.extensions.dip
import com.techstudio.extensions.setProgressListener

class ImageWithPlaceholderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    private var placeholder: Drawable? = null
    private val imageView: ImageView
    private val progressBar: ProgressBar

    private val glideTarget: GlideTarget

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ImageWithPlaceholderView)
        val drawable = ta.getDrawable(R.styleable.ImageWithPlaceholderView_placeholderDrawable)
        val tint = ta.getColor(R.styleable.ImageWithPlaceholderView_placeholderTint, 0)
        val clipImageView = ta.getBoolean(R.styleable.ImageWithPlaceholderView_clipImageView, false)
        ta.recycle()

        imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        imageView.id = ViewCompat.generateViewId()

        progressBar = ProgressBar(context)
        progressBar.id = ViewCompat.generateViewId()
        progressBar.isVisible = false
        addView(imageView, LayoutParams(MATCH_PARENT, MATCH_PARENT))

        addView(progressBar, LayoutParams(dip(24), dip(24)))

        val set = ConstraintSet()
        set.clone(this)
        set.connect(progressBar.id, START, PARENT_ID, START)
        set.connect(progressBar.id, END, PARENT_ID, END)
        set.connect(progressBar.id, TOP, PARENT_ID, TOP)
        set.connect(progressBar.id, BOTTOM, PARENT_ID, BOTTOM)
        set.applyTo(this)
        if (clipImageView) {
            imageView.background = background
            imageView.clipToOutline = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imageView.foreground = foreground
            }
        } else {
            clipChildren = true
        }
        glideTarget = GlideTarget(imageView)
        drawable?.let { setPlaceholder(it, tint) }
    }

    fun setPlaceholder(
        placeholderDrawable: Drawable,
        @ColorInt tint: Int = 0
    ) {
        if (tint != 0) DrawableCompat.wrap(placeholderDrawable).mutate().apply {
            DrawableCompat.setTint(this, tint)
        }

        placeholder = placeholderDrawable
        imageView.setImageDrawable(placeholderDrawable)
    }

    fun loadImage(
        url: String?,
        @ColorInt tint: Int = 0,
        isCentered: Boolean = false,
        loadWithProgress: Boolean = true
    ) {
        if (loadWithProgress) showLoading(true)
        Glide.with(imageView)
            .load(url)
            .placeholder(placeholder)
            .apply {
                if (isCentered) fitCenter() else centerCrop()
            }
            .setProgressListener(progressBar)
            .error(R.drawable.ic_camera)
            .into(glideTarget.withTint(tint))
    }

    fun showLoading(isLoading: Boolean) {
        progressBar.isVisible = isLoading
    }

    fun clear() {
        if (context !is FragmentActivity) return
        if (!(context as FragmentActivity).isDestroyed)
            Glide.with(this).clear(this)
    }
}

private class GlideTarget(imageView: ImageView) : CustomViewTarget<ImageView, Drawable>(imageView) {

    @ColorInt
    private var tint: Int = 0

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        if (tint != 0) {
            DrawableCompat.wrap(resource).mutate().apply {
                DrawableCompat.setTint(this, tint)
                view.setImageDrawable(this)
                view.setBackgroundResource(R.drawable.background_disabled_on_primary_rounded)
            }
        } else {
            view.setImageDrawable(resource)
        }
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        view.setImageDrawable(errorDrawable)
    }

    override fun onResourceCleared(placeholder: Drawable?) {
        view.setImageDrawable(placeholder)
    }

    fun withTint(@ColorInt tint: Int) = apply { this.tint = tint }
}
