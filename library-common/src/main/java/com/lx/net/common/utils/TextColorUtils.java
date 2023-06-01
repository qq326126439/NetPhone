package com.lx.net.common.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.lx.net.common.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextColorUtils {

    /**
     * @param context 上下文
     * @param text 设置文本
     * @param keyword 匹配文本
     * @param size 从文本的下标开始匹配
     * @return
     */
    public static SpannableString matcherSearchTitle(Context context, String text, String keyword, int size) {
        if (text == null){
            return null;
        }
        if (keyword == null){
            return new SpannableString(text);
        }
        String string = text.toLowerCase();
        String key = keyword.toLowerCase();
        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(string);
        SpannableString ss = new SpannableString(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (start >= size){
                ss.setSpan(new ForegroundColorSpan(0xfff000), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ss;
    }
}
