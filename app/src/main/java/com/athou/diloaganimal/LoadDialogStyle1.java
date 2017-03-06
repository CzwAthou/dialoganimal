/*
 * Copyright (c) 2016  athou（cai353974361@163.com）.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.athou.diloaganimal;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

/**
 * 加载dialog样式二
 */
public class LoadDialogStyle1 extends Dialog {
    private ImageView iv_route;
    private int[] imgRes = new int[]{R.drawable.loading_style01_01, R.drawable.loading_style01_02,
            R.drawable.loading_style01_03, R.drawable.loading_style01_04, R.drawable.loading_style01_05,
            R.drawable.loading_style01_06, R.drawable.loading_style01_07, R.drawable.loading_style01_08,
            R.drawable.loading_style01_09};
    private int index = 0;

    public LoadDialogStyle1(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View contentView = View.inflate(context, R.layout.layout_loading_dialog_style01, null);
        iv_route = (ImageView) contentView.findViewById(R.id.iv_route);
        setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    @Override
    public void show() {
        refreshImageView();
        super.show();
    }

    private void refreshImageView() {
        iv_route.setImageResource(imgRes[(index++) % imgRes.length]);
        set = new AnimatorSet();
        ObjectAnimator ra = ObjectAnimator.ofFloat(null, "rotationY", 0, 180).setDuration(800);
        ObjectAnimator aa = ObjectAnimator.ofFloat(null, "alpha", 1.0f, 0.3f).setDuration(800);
        aa.setInterpolator(new AccelerateInterpolator());
        set.playTogether(ra, aa);
        set.addListener(listener);
        set.setTarget(iv_route);
        set.start();
    }

    Animator.AnimatorListener listener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator arg0) {
        }

        @Override
        public void onAnimationEnd(Animator arg0) {
            refreshImageView();
        }

        @Override
        public void onAnimationRepeat(Animator arg0) {
        }

        @Override
        public void onAnimationCancel(Animator arg0) {
        }
    };

    private AnimatorSet set = null;
}
