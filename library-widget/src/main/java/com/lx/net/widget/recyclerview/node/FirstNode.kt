package com.lx.net.widget.recyclerview.node

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode

/***********************************************************************
 * <p>@description: 侧滑栏分类第一级node
 * <p>@author: pengl
 * <p>@created on: 2022/8/17 0017 18:29
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/17 0017 18:29
 **********************************************************************/
class FirstNode(
    var title: String,
    var data: MutableList<BaseNode>,
    var level: Int? = 0,
    var nodeId: Long? = 0,
    var parentId: Long? = 0,
    var itemData: Any? = null,
    var isSelected :Boolean = false,
) :
    BaseExpandNode() {

    override val childNode: MutableList<BaseNode> = data

}