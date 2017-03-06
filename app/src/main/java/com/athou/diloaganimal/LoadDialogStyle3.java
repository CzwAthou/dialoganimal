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

import static com.athou.diloaganimal.R.id.iv_route;

/**
 * 加载dialog样式二
 */
public class LoadDialogStyle3 extends Dialog {
    private ThreePointLoadingView loadView;

    public LoadDialogStyle3(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View contentView = View.inflate(context, R.layout.layout_loading_dialog_style03, null);
        loadView = (ThreePointLoadingView) contentView.findViewById(R.id.dialog_loading_threepoint_view);
        setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    @Override
    public void show() {
        loadView.setVisibility(View.VISIBLE);
        loadView.startLoading();
        super.show();
    }

    @Override
    public void dismiss() {
        loadView.setVisibility(View.GONE);
        loadView.cancel();
        super.dismiss();
    }
}
