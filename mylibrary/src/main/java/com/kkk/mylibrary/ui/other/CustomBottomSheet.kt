package com.kkk.mylibrary.ui.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kkk.mylibrary.R
import com.kkk.mylibrary.databinding.BottomSheetCustomOptionBinding
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.CustomBottomSheetDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.vo.OptionType

class CustomBottomSheet<T>(
    private val optionsList: ArrayList<OptionType<T>>,
    private val onClickItem: (Int) -> Unit
) :
    BaseBindingBottomSheet<BottomSheetCustomOptionBinding>() {
    override val isFullScreen: Boolean
        get() = false
    private val delegateAdapter = DelegateAdapter()
    private val itemList = mutableListOf<ItemDisplayer>()

    override fun bindView(inflater: LayoutInflater): BottomSheetCustomOptionBinding =
        BottomSheetCustomOptionBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppBottomSheetDialogTheme)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        itemList.clear()
        optionsList.mapIndexed { position, optionType ->
            itemList.add(CustomBottomSheetDisplayer(data = optionType, position, ::onClick))

        }
        binding.rvMoreOptions.apply {
            layoutManager = linearLayoutManager
            adapter = delegateAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL,
                )
            )
        }
        delegateAdapter.setData(itemList)


    }

    private fun onClick(position: Int) {
        onClickItem(position)
    }
}