package com.lx.net.widget.recyclerview.provider

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.lx.net.widget.recyclerview.node.SecondNode
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.net.common.utils.DensityUtil
import com.lx.net.common.utils.HseAlcImpl
import com.lx.net.widget.R

/***********************************************************************
 * <p>@description:侧滑栏分类第二级title
 * <p>@author: pengl
 * <p>@created on: 2022/8/17 0017 18:31
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/17 0017 18:31
 **********************************************************************/
class SecondNodeProvider : BaseNodeProvider() {

    override val itemViewType = 1

    override val layoutId = R.layout.item_second_node

    override fun convert(helper: BaseViewHolder, item: BaseNode) {

        if (item is SecondNode) {
            val tvTitle = helper.getView<TextView>(R.id.tv_title)
            helper.setText(R.id.tv_title, item.title)
            item.level?.let {
                //fixLeft左边距
                var layoutParams = tvTitle.layoutParams as LinearLayout.LayoutParams
                layoutParams.leftMargin =
                    DensityUtil.dp2px(HseAlcImpl.getApplication().resources, 50f + (it-1) * 18).toInt()
                tvTitle.layoutParams = layoutParams
            }
//            helper.setTextColor(
//                R.id.tv_title,
//                if (item.isSelected) context.resources.getColor(R.color.main_blue)
//                else context.resources.getColor(R.color.text_color_2)
//            )
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
//        Log.e("parry", "data = $data")
//        if (data is SecondNode) {
//            data.isSelected = !data.isSelected
//            getAdapter()?.notifyItemChanged(position)
//        }
    }
}