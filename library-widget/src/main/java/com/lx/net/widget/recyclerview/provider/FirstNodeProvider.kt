package com.lx.net.widget.recyclerview.provider

import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import com.lx.net.widget.recyclerview.node.FirstNode
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lx.net.common.utils.DensityUtil
import com.lx.net.common.utils.HseAlcImpl
import com.lx.net.widget.R

/***********************************************************************
 * <p>@description: 侧滑栏分类第一级title
 * <p>@author: pengl
 * <p>@created on: 2022/8/17 0017 18:31
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/17 0017 18:31
 **********************************************************************/
class FirstNodeProvider : BaseNodeProvider() {

    override val itemViewType = 0

    override val layoutId = R.layout.item_first_node

    override fun convert(helper: BaseViewHolder, item: BaseNode) {

        if (item is FirstNode) {
            helper.setText(R.id.tv_title, item.title)

            val imageView = helper.getView<ImageView>(R.id.iv_expand)

            item.level?.let {
                //fixLeft左边距
                var layoutParams = imageView.layoutParams as LinearLayout.LayoutParams
                layoutParams.leftMargin =
                    (DensityUtil.dp2px(
                        HseAlcImpl.getApplication().resources,
                        if(it>0) (28f + (it-1) * 18) else 20f
                    )).toInt()
                imageView.layoutParams = layoutParams
            }

            if (item.isExpanded) {
                imageView.setImageResource(R.drawable.iv_expand_gray)
            } else {
                ViewCompat.animate(imageView).setDuration(200)
                    .setInterpolator(DecelerateInterpolator())
                    .rotation(270f)
                    .start()
            }
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        super.onClick(helper, view, data, position)

        if ((data as FirstNode).isExpanded) {
            getAdapter()!!.collapse(position)
        } else {
            getAdapter()!!.expandAndCollapseOther(position)
        }
        getAdapter()?.notifyDataSetChanged()
    }
}