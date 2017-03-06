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
public class LoadDialogStyle2 extends Dialog {
    private ImageView circleIv;
    private ImageView centerIv;

    public LoadDialogStyle2(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View contentView = View.inflate(context, R.layout.layout_loading_dialog_style02, null);
        circleIv = (ImageView) contentView.findViewById(R.id.dialog_loading_circle_iv);
        centerIv = (ImageView) contentView.findViewById(R.id.dialog_loading_center_iv);
        setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    @Override
    public void show() {
        start();
        super.show();
    }

    @Override
    public void dismiss() {
        if (circleAnima != null && circleAnima.isRunning()) {
            circleAnima.cancel();
        }
        if (centerAnima != null && centerAnima.isRunning()) {
            centerAnima.cancel();
        }
        super.dismiss();
    }

    ObjectAnimator circleAnima = null;
    ObjectAnimator centerAnima = null;

    private void start() {
        circleAnima = ObjectAnimator.ofFloat(circleIv, "rotation", 0.0F, 359.0F);
        circleAnima.setDuration(1000);
        circleAnima.setRepeatCount(ObjectAnimator.INFINITE);
        circleAnima.setRepeatMode(ObjectAnimator.RESTART);
        circleAnima.setInterpolator(new AccelerateInterpolator());
        circleAnima.start();

        centerAnima = ObjectAnimator.ofFloat(centerIv, "rotationY", 0.0F, 179.0F);
        centerAnima.setDuration(1000);
        centerAnima.setInterpolator(new AccelerateInterpolator());
        centerAnima.setRepeatCount(ObjectAnimator.INFINITE);
        centerAnima.setRepeatMode(ObjectAnimator.RESTART);
        centerAnima.start();
    }
}
