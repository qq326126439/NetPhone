package com.lx.net.widget.recyclerview.node

import com.chad.library.adapter.base.entity.node.BaseNode

/***********************************************************************
 * <p>@description:侧滑栏分类第二级node
 * <p>@author: pengl
 * <p>@created on: 2022/8/15 0015 17:22
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/15 0015 17:22
 **********************************************************************/
class SecondNode(
    val title: String = "",
    var isSelected: Boolean = false,
    var level: Int? = 0,
    var nodeId: Long? = 0,
    var parentId: Long? = 0,
    var itemData: Any? = null
) : BaseNode() {
    override val childNode: MutableList<BaseNode>? = null
}