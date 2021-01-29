package com.techstudio.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.techstudio.R
import com.techstudio.extensions.setThrottleOnClickListener
import kotlinx.android.synthetic.main.widget_empty_view.view.*

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {
    var onRefreshListener: () -> Unit = {}

    init {
        View.inflate(context, R.layout.widget_empty_view, this)
        refreshButton.setThrottleOnClickListener {
            onRefreshListener.invoke()
        }
        isVisible = false
    }

    fun setMessage(message: String) {
        isVisible = true
        if (message.isEmpty()) {
            messageView.setText(R.string.general_error_message)
        } else {
            messageView.text = message
        }
    }
}