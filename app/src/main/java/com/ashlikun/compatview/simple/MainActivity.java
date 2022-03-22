package com.ashlikun.compatview.simple;

import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ashlikun.compatview.ExpandTextView;
import com.ashlikun.compatview.ScaleImageView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ExpandTextView expandTv;
    Button actionExpand;
    ScaleImageView levelIv;
    boolean isChang = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.app = getApplication();
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        expandTv = findViewById(R.id.expandTv);
        levelIv = findViewById(R.id.levelIv);
        actionExpand = findViewById(R.id.actionExpand);
        int color = 0xffff6e00;
        TextDrawable appDrawable = new TextDrawable();
        appDrawable.setTextColor(color);
        appDrawable.setText("我是APP");
        appDrawable.setPadding(5, 2, 5, 2);
        appDrawable.setTextSize(11)
                .setCornerRadiusNew(3)
                .setColorNew(0xfffff7ee);

        textView.setText(SpannableUtils.getBuilder("红色")
                .append("。").setResourceId(R.mipmap.ic_launcher_round).changImageSize()
                .append("红aaaaaaaaaaaaa红aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .create());

        SpannableString ss = new SpannableString(
                "红色打电话斜体删除线绿色下划线图片:.1红aaaaaaaaaaaaa红aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

//        // 用颜色标记文本
//        ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 2,
//                // setSpan时需要指定的 flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括).
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        // 用超链接标记文本
//        ss.setSpan(new URLSpan("tel:10086"), 2, 5,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        // 用样式标记文本（斜体）
//        ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 5, 7,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        // 用删除线标记文本
//        ss.setSpan(new StrikethroughSpan(), 7, 10,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        // 用下划线标记文本
//        ss.setSpan(new UnderlineSpan(), 10, 16,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        // 用颜色标记
//        ss.setSpan(new ForegroundColorSpan(Color.GREEN), 10, 12,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 获取Drawable资源
//        Drawable d = getResources().getDrawable(R.mipmap.ic_launcher);
//        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//        // 创建ImageSpan
//        SpannableUtils.CentreImageSpan span = new SpannableUtils.CentreImageSpan(d);
//        // 用ImageSpan替换文本
//        ss.setSpan(span, 18, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
        textView.setText(ss);
        expandTv.setText("码都来自AppCompatTextViewAutoSizeHelper，修修补补就变成了下面的类。Google在构建StaticLayout时候反射了\n\n部分TextView方法，\n\n虽然都在说不推荐反射，\n\n1234567890了反射那肯定是有原因的，而且Google用了MethodCacheMap做了Method缓存降低了反射对性能的影响，反射时候用defaultValue来处理反射失败的情况。你可以尝试将反射部分的代码改成下面的代码来测试反射失败的情况",
                false, new ExpandTextView.Callback() {
                    @Override
                    public void onChang(boolean isExpanded) {
                        actionExpand.setText(isExpanded ? "收起" : "展开");
                    }

                    @Override
                    public void onLoss() {

                    }
                });
        actionExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                expandTv.setChanged();
                if (isChang) {
                    isChang = false;
                    levelIv.setRatio(1.44f);
                } else {
                    isChang = true;
                    levelIv.setRatio(2);
                }

            }
        });
    }
}
