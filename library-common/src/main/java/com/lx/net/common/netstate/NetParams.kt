package com.lx.net.common.netstate

import com.lx.net.common.netstate.NetType
import java.lang.reflect.Method

/***********************************************************************
 * <p>@description:
 * <p>@author: pengl
 * <p>@created on: 2022/7/28 0028 15:52
 *
 * <p>@version: 1.0.0
 * <p>@modify time:2022/7/28 0028 15:52
 **********************************************************************/
data class NetParams(val paramType: Class<*>, val netType: NetType, val method: Method)
