package com.fork.spoonfeed.presentation.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fork.spoonfeed.databinding.ItemCommentBinding

data class MyCommentResponseData(
    val id: Int,
    val sentence: String,
    val category: String,
    val writedData: String,
)

class MyCommentAdapter(
    private val postList: List<MyCommentResponseData>,
    private val clickListener: (MyCommentResponseData) -> Unit
) : ListAdapter<MyCommentResponseData, MyCommentAdapter.MyCommentViewHolder>(diffUtil) {

    inner class MyCommentViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: MyCommentResponseData) {
            binding.apply {
                tvItemCommentSentence.text = data.sentence
                tvItemCommentCategory.text = data.category
                tvItemCommentWritedData.text = data.writedData
                ivItemCommentEdit.setOnClickListener {
                    clickListener(data)
                }
            }
            setClickListenerItemPostEdit(binding)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyCommentViewHolder(binding)
    }

    override fun getItemCount() = postList.size

    override fun onBindViewHolder(holder: MyCommentAdapter.MyCommentViewHolder, position: Int) {
        holder.onBind(postList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyCommentResponseData>() {
            override fun areContentsTheSame(oldItem: MyCommentResponseData, newItem: MyCommentResponseData) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: MyCommentResponseData, newItem: MyCommentResponseData) =
                oldItem.id == newItem.id
        }
    }

    private fun setClickListenerItemPostEdit(binding: ItemCommentBinding) {
        with(binding) {
            ivItemCommentEdit.setOnClickListener {
                ctlItemPostEditDialog.visibility = android.view.View.VISIBLE
                //     activity.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
            tvCommentDialogEdit.setOnClickListener {
                ctlItemPostEditDialog.visibility = android.view.View.INVISIBLE
            }
            tvCommentDialogDelete.setOnClickListener {
                ctlItemPostEditDialog.visibility = android.view.View.INVISIBLE
            }
        }
    }
}
