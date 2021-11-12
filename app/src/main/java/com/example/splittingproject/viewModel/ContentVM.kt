package com.example.splittingproject.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.splittingproject.R
import com.example.splittingproject.remote.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 * Created By Huzayfa Elhussein on 11/10/2021
 */
class ContentVM(application: Application) : AndroidViewModel(application) {

    val mContext: Context by lazy {
        getApplication()
    }

    val tenthCharLiveData = MutableLiveData<String>()
    val everyTenthCharLiveData = MutableLiveData<String>()
    val wordCounterLiveData = MutableLiveData<String>()
    val failureLiveData = MutableLiveData<Unit>()
    var content = ""

    fun grabData() {
        viewModelScope.launch(Dispatchers.Default) {
            val wordCounterRequest = async { truecallerWordCounterRequest(content) }
            val tenthCharRequest = async { truecaller10thCharacterRequest() }
            everyTenthCharLiveData.postValue(tenthCharRequest.await())
            val everyTenthCharRequest =
                async { truecallerEvery10thCharacterRequest(text = content) }
            wordCounterLiveData.postValue(everyTenthCharRequest.await())
            tenthCharLiveData.postValue(wordCounterRequest.await())

        }
    }

    fun fetchContent() {
        val apiService = ApiUtils.getApiService(mContext)
        val call = apiService?.callTrueCallerWeb()
        call?.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                content = response.body()!!
                grabData()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                failureLiveData.postValue(Unit)
            }
        })
    }

    fun truecaller10thCharacterRequest(index: Int = 10): String {
        if (content.length < index) {
            failureLiveData.postValue(Unit)
            return ""
        }
        return content.toCharArray()[index].toString()
    }

    private fun truecallerWordCounterRequest(st: String): String {
        var text = ""
        val array = st.split("[\\s,\r\n]+".toRegex())
        if (array.isNullOrEmpty()) return text
        val occurrences = hashMapOf<String, Int>()
        array.forEach {
            val t = it.lowercase(Locale.getDefault())
            var count = occurrences[t]
            if (count == null) count = 0
            occurrences[t] = count + 1
        }

        occurrences.forEach { (t, u) ->
            text += "$t (" + String.format(
                mContext.resources.getString(R.string.occurrence_count_text),
                u
            ) + ")\n"
        }
        return text

    }

    fun truecallerEvery10thCharacterRequest(iterations: Int = 10, text: String = ""): String {
        if (text.isEmpty()) return ""
        var index = 0
        val array = arrayListOf<String>()
        while (index + iterations < text.toCharArray().size) {
            index += iterations
            array.add(text.toCharArray()[index] + "")
        }
        return array.toString()
    }

}