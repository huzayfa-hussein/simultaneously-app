package com.example.splittingproject.ui

import android.content.DialogInterface
import android.os.Bundle
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
        button_call.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            progressBar1.visibility = View.VISIBLE
            progressBar2.visibility = View.VISIBLE
            viewModel.fetchContent()
        }
        listenForObservers()
    }

    private fun listenForObservers() {
        viewModel.tenthCharLiveData.observe(this@ContentActivity) {
            textView_third.text = it
            progressBar2.visibility = View.GONE
        }
        viewModel.everyTenthCharLiveData.observe(this@ContentActivity) {
            textView_first.text = it
            progressBar.visibility = View.GONE
        }

        viewModel.wordCounterLiveData.observe(this@ContentActivity) {
            textView_second.text = it
            progressBar1.visibility = View.GONE
        }
        viewModel.failureLiveData.observe(this@ContentActivity) {
            showFailureDialog()
        }
    }

    private fun showFailureDialog() {
        val alertDialogLayout = MaterialAlertDialogBuilder(this@ContentActivity)
            .setTitle(resources.getString(R.string.facing_error_dialog_title))
            .setMessage(resources.getString(R.string.facing_error_dialog_message))
            .setPositiveButton(resources.getString(R.string.all_try_again)) { dialogInterface: DialogInterface, i: Int ->
                viewModel.grabData()
            }
            .setNegativeButton(resources.getString(R.string.all_cancel)) { d: DialogInterface, i: Int ->
                //nothing to do
            }.create()
        alertDialogLayout.show()
    }
}