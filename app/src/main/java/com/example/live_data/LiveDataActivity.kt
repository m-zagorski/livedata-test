package com.example.live_data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.live_data.databinding.ActivityLiveDataBinding
import kotlinx.android.synthetic.main.activity_live_data.*

class LiveDataActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, LiveDataActivity::class.java)
    }

    private lateinit var binding: ActivityLiveDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this)[LiveDataViewModel::class.java]

        DataBindingUtil.setContentView<ActivityLiveDataBinding>(this, R.layout.activity_live_data)
                .apply {
                    this.viewModel = viewModel
//                this.data = viewModel.data
                    data = viewModel.observableData.get()
                    lifecycleOwner = this@LiveDataActivity
                }

//        viewModel.firstSelected
//            .observe(this, live_data_select_first::selected)
//        viewModel.secondSelected
//            .observe(this, Observer(live_data_select_second::selected))
//        viewModel.thirdSelected
//            .observe(this, Observer(live_data_select_third::selected))

//        viewModel.liveData(this) {
//            firstSelected
//                .observe(it, live_data_select_first::selected)
//            secondSelected
//                .observe(it, live_data_select_second::selected)
//            thirdSelected
//                .observe(it, live_data_select_third::selected)
//        }

//        viewModel.liveData(this) {
//            errorLiveData
//                    .observe(it, Observer { })
//        }
    }
}

internal fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

internal fun View.selected(selected: Boolean) {
    isSelected = selected
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, action: (T) -> Unit) =
        observe(owner, Observer(action))

fun <T> LiveData<T>.test(action: (T) -> Unit): (LifecycleOwner) -> Unit = {
    observe(it, action)
}

fun <T> LiveData<T>.consume(block: LiveData<T>.() -> Unit) {
    block()
}


fun <T : ViewModel> T.liveData(owner: LifecycleOwner, block: T.(LifecycleOwner) -> Unit) {
    block(owner)
}

// Binding adapters
@BindingAdapter("selected")
fun changeSelectedState(view: TextView, selected: Boolean) {
    view.isSelected = selected
}