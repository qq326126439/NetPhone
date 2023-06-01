package com.lx.net.common.utils

import java.util.regex.Pattern

object RegexUtil {

    fun matcherParentheses(content : String) : String{
        val pattern: Pattern = Pattern.compile("(?<=\\().+?(?=\\))")
        val matcher = pattern.matcher(content)
        if(matcher.find()){
            return matcher.group()
        }
        return ""
    }

}