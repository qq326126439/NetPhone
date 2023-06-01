package com.lx.net.common.toast;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lx.net.common.toast.config.IToast;


/**
 * author :
 * github :
 * time   : 2018/11/03
 * desc   : 系统 Toast
 */
public class SystemToast implements IToast {
    /** 吐司消息 View */
//    private TextView mMessageView;

    /**
     * 系统吐司控件
     */
    private Toast  mToast;

    /**
     * SystemToast 需要换一种初始化方式 系统的Toast在依赖的Activity销毁之后会导致mNextView为空 从而报错RuntimeException("setView must have been called");
     * 使用makeText 会重新创建mNextView
     */
    public SystemToast(Application application) {
        mToast = Toast.makeText(application, "", Toast.LENGTH_SHORT);
    }

    @Override
    public View getView() {
        return mToast.getView();
    }

    @Override
    public void setDuration(int duration) {
        mToast.setDuration(duration);
    }

    @Override
    public int getDuration() {
        return mToast.getDuration();
    }

    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        mToast.setGravity(gravity, xOffset, yOffset);
    }

    @Override
    public int getGravity() {
        return mToast.getGravity();
    }

    @Override
    public int getXOffset() {
        return mToast.getXOffset();
    }

    @Override
    public int getYOffset() {
        return mToast.getYOffset();
    }

    @Override
    public void setMargin(float horizontalMargin, float verticalMargin) {
        mToast.setMargin(horizontalMargin, verticalMargin);
    }

    @Override
    public float getHorizontalMargin() {
        return mToast.getHorizontalMargin();
    }

    @Override
    public float getVerticalMargin() {
        return mToast.getVerticalMargin();
    }

    @Override
    public TextView findMessageView(View view) {
        if (view instanceof TextView) {
            return (TextView) view;
        } else if (view.findViewById(android.R.id.message) instanceof TextView) {
            return ((TextView) view.findViewById(android.R.id.message));
        } else if (view instanceof ViewGroup) {
            TextView textView = findTextView((ViewGroup) view);
            if (textView != null) {
                return textView;
            }
        }
        // 如果设置的布局没有包含一个 TextView 则抛出异常，必须要包含一个 TextView 作为 MessageView
        throw new IllegalArgumentException("The layout must contain a TextView");
    }

    @Override
    public TextView findTextView(@NonNull ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if ((view instanceof TextView)) {
                return (TextView) view;
            } else if (view instanceof ViewGroup) {
                TextView textView = findTextView((ViewGroup) view);
                if (textView != null) {
                    return textView;
                }
            }
        }
        return null;
    }

    @Override
    public void show() {
        if (getView() != null){
            mToast.show();
        }
    }

    @Override
    public void cancel() {
        mToast.cancel();
    }

    @Override
    public void setText(int id) {
        mToast.setText(id);
    }

    @Override
    public void setText(CharSequence text) {
        mToast.setText(text);
    }

    @Override
    public void setView(View view) {
        mToast.setView(view);
    }
}