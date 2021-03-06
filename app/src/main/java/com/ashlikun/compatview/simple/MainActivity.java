package com.ashlikun.compatview.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.app = getApplication();
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        int color = 0xffff6e00;
        TextDrawable appDrawable = new TextDrawable()
                .setTextColor(color)
                .setText("我是APP")
                .setPadding(5, 2, 5, 2)
                .setTextSize(11)
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
    }
}
