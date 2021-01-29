package com.techstudio.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.techstudio.R
import com.techstudio.extensions.args
import com.techstudio.extensions.withArgs
import com.techstudio.model.MediaMetadata
import kotlinx.android.synthetic.main.dialog_image_preview.*

private const val EXTRA_MEDIA = "EXTRA_MEDIA"

class MediaPreviewDialog : DialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager, media: MediaMetadata) {
            MediaPreviewDialog()
                .withArgs(EXTRA_MEDIA to media)
                .show(fragmentManager, MediaPreviewDialog::class.java.name)
        }
    }

    private val media: MediaMetadata by args(EXTRA_MEDIA)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_image_preview, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
    }

    override fun getTheme(): Int = R.style.FullScreenDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            dismissAllowingStateLoss()
        }
        mediaView.loadImage(media.url, isCentered = true)
    }
}