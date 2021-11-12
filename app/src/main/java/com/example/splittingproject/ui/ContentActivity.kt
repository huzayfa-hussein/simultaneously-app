package com.example.splittingproject.ui

import android.content.DialogInterface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.splittingproject.R
import com.example.splittingproject.viewModel.ContentVM
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created By Huzayfa Elhussein on 11/10/2021
 */

class ContentActivity : AppCompatActivity() {

    val viewModel by viewModels<ContentVM>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTextViewsBehavior()
        button_call.setOnClickListener {
            showLoaders()
            viewModel.fetchContent()
        }
        listenForObservers()
    }

    private fun listenForObservers() {
        viewModel.tenthCharLiveData.observe(this@ContentActivity) {
            textView_word_count.text = it
            progressBar2.visibility = View.GONE
        }
        viewModel.everyTenthCharLiveData.observe(this@ContentActivity) {
            textView_tenth.text = it
            progressBar.visibility = View.GONE
        }

        viewModel.wordCounterLiveData.observe(this@ContentActivity) {
            textView_every_tenth.text = it
            progressBar1.visibility = View.GONE
        }
        viewModel.failureLiveData.observe(this@ContentActivity) {
            showFailureDialog()
        }
    }

    private fun showLoaders() {
        progressBar.visibility = View.VISIBLE
        progressBar1.visibility = View.VISIBLE
        progressBar2.visibility = View.VISIBLE
    }

    private fun setTextViewsBehavior() {
        textView_word_count.movementMethod = ScrollingMovementMethod()
        textView_every_tenth.movementMethod = ScrollingMovementMethod()
    }


    private fun showFailureDialog() {
        val alertDialogLayout = MaterialAlertDialogBuilder(this@ContentActivity)
            .setTitle(resources.getString(R.string.facing_error_dialog_title))
            .setMessage(resources.getString(R.string.facing_error_dialog_message))
            .setPositiveButton(resources.getString(R.string.all_try_again)) { dialogInterface: DialogInterface, i: Int ->
                viewModel.fetchContent()
            }
            .setNegativeButton(resources.getString(R.string.all_cancel)) { d: DialogInterface, i: Int ->
                //nothing to do
            }.create()
        alertDialogLayout.show()
    }
}