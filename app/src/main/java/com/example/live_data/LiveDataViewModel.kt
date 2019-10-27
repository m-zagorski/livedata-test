package com.example.live_data

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class LiveDataViewModel : ViewModel() {
    enum class Choice {
        NONE, FIRST, SECOND, THIRD
    }

    private val _progressVisibilityLiveData = MutableLiveData<Boolean>()
    private val _errorLiveData = MutableLiveData<GlobalError>()

    val progressVisibilityLiveData: LiveData<Boolean> = _progressVisibilityLiveData.apply { value = false }
    val errorLiveData: LiveData<GlobalError> = _errorLiveData

    val observableData = ObservableField(LiveDataData())

    fun actionContinue() {
        _progressVisibilityLiveData.postValue(true)
        if (observableData.get()?.isValid() == false) _errorLiveData.postValue(GlobalError.AllFieldsRequired)
        else sendToApi()
    }

    fun dupaAction(data: LiveDataData) {
        Log.e("KurwoTy", "---- $data")
    }

    private fun sendToApi() {
        // TODO there would be still an rx java call to single, when we would handle error in the same way we are doing
        // it in actionContinue
    }

    data class LiveDataData(val firstName: ObservableField<String> = ObservableField(""),
                            val testName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" },
                            val secondName: ObservableField<String> = ObservableField(""),
                            val choice: ObservableField<Choice> = ObservableField(Choice.NONE)) {
        fun changeChoice(newChoice: Choice) {
            choice.set(newChoice)
        }

        fun isValid(): Boolean = !firstName.get().isNullOrEmpty() && !secondName.get().isNullOrEmpty() && choice.get() != Choice.NONE

        fun toRequest(): Request = Request(firstName.get()!!, secondName.get()!!, choice.get()!!)
    }
}

data class Request(val fn: String,
                   val sn: String,
                   val choice: LiveDataViewModel.Choice)

sealed class GlobalError {
    object AllFieldsRequired : GlobalError()
}

fun <T, R> LiveData<T>.map(action: (T) -> R): LiveData<R> = Transformations.map(this, action)