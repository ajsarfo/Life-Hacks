package com.sarftec.lifehacks.view.dialog

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sarftec.lifehacks.databinding.DialogLoadingBinding

class LoadingDialog(
    activity: AppCompatActivity,
    parent: ViewGroup
) : AlertDialog(parent.context){

    private val layoutBinding = DialogLoadingBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )

    init {
        val displayRect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(displayRect)
        layoutBinding.root.apply {
            minimumWidth = (displayRect.width() * 1f).toInt()
            minimumHeight = (displayRect.height() * 1f).toInt()
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setView(layoutBinding.root)
        setCancelable(false)
    }
}