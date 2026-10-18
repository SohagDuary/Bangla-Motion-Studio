
package com.banglamotionstudio.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    private val backgroundColor = Color.rgb(15, 15, 20)
    private val accentColor = Color.rgb(255, 190, 60)

    private var textColor = Color.WHITE
    private lateinit var preview: TextView
    private lateinit var captionInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            buildMainScreen()
        } catch (e: Exception) {
            Log.e("BanglaMotionStudio", "Startup failed", e)
            showError(e)
        }
    }

    private fun buildMainScreen() {
        window.statusBarColor = backgroundColor
        window.navigationBarColor = backgroundColor

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(20))
            setBackgroundColor(backgroundColor)
        }

        val scroll = ScrollView(this).apply {
            setFillViewport(true)
            addView(
                root,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        val title = TextView(this).apply {
            text = "BANGLA MOTION STUDIO"
            textSize = 23f
            setTextColor(accentColor)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        }
        root.addView(title, matchWidth())

        val subtitle = TextView(this).apply {
            text = "Typography Video Caption Editor"
            textSize = 14f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }
        root.addView(subtitle, matchWidth())

        addGap(root, 28)

        captionInput = EditText(this).apply {
            hint = "বাংলা বা English লেখা লিখুন..."
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            textSize = 20f
            setPadding(dp(12), dp(12), dp(12), dp(12))
            setBackgroundColor(Color.rgb(38, 38, 46))
            minLines = 2
            gravity = Gravity.TOP
        }
        root.addView(captionInput, matchWidth())

        addGap(root, 18)

        val previewTitle = TextView(this).apply {
            text = "LIVE PREVIEW"
            textSize = 14f
            setTextColor(accentColor)
            typeface = Typeface.DEFAULT_BOLD
        }
        root.addView(previewTitle, matchWidth())

        preview = TextView(this).apply {
            text = "আপনার লেখা এখানে দেখা যাবে"
            textSize = 30f
            setTextColor(textColor)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(dp(12), dp(35), dp(12), dp(35))
            setBackgroundColor(Color.BLACK)
        }

        val previewParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dp(190)
        ).apply {
            topMargin = dp(10)
        }
        root.addView(preview, previewParams)

        addGap(root, 16)

        val updateButton = makeButton("Update Preview")
        updateButton.setOnClickListener {
            val value = captionInput.text.toString()
            preview.text = if (value.isBlank()) {
                "আপনার লেখা এখানে দেখা যাবে"
            } else {
                value
            }
        }
        root.addView(updateButton, matchWidth())

        addGap(root, 10)

        val colorButton = makeButton("Change Text Color")
        colorButton.setOnClickListener {
            textColor = when (textColor) {
                Color.WHITE -> Color.YELLOW
                Color.YELLOW -> Color.CYAN
                else -> Color.WHITE
            }
            preview.setTextColor(textColor)
        }
        root.addView(colorButton, matchWidth())

        addGap(root, 10)

        val styleButton = makeButton("Toggle Bold Style")
        styleButton.setOnClickListener {
            preview.typeface = if (preview.typeface.isBold) {
                Typeface.create("sans-serif", Typeface.NORMAL)
            } else {
                Typeface.create("sans-serif-black", Typeface.BOLD)
            }
        }
        root.addView(styleButton, matchWidth())

        addGap(root, 20)

        val footer = TextView(this).apply {
            text = "Version 1.0 • Made for Bengali & English"
            setTextColor(Color.GRAY)
            textSize = 12f
            gravity = Gravity.CENTER
        }
        root.addView(footer, matchWidth())

        setContentView(scroll)
    }

    private fun showError(e: Exception) {
        try {
            val message = "অ্যাপ চালু করতে সমস্যা হয়েছে।\n\n" +
                "Error: ${e.javaClass.simpleName}\n" +
                "Details: ${e.message ?: "বিস্তারিত পাওয়া যায়নি"}"

            val errorView = TextView(this).apply {
                text = message
                textSize = 16f
                setTextColor(Color.WHITE)
                setBackgroundColor(Color.rgb(30, 20, 20))
                setPadding(dp(24), dp(24), dp(24), dp(24))
            }

            setContentView(errorView)
        } catch (displayError: Exception) {
            Log.e(
                "BanglaMotionStudio",
                "Could not display startup error",
                displayError
            )
        }
    }

    private fun makeButton(label: String): Button {
        return Button(this).apply {
            text = label
            textSize = 15f
            setTextColor(Color.BLACK)
            setBackgroundColor(accentColor)
            isAllCaps = false
        }
    }

    private fun matchWidth(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun addGap(parent: LinearLayout, height: Int) {
        parent.addView(
            android.view.View(this),
            LinearLayout.LayoutParams(1, dp(height))
        )
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }
}
