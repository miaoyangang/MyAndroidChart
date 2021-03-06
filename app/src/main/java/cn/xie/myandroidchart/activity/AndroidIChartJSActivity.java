package cn.xie.myandroidchart.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.xie.myandroidchart.R;
import cn.xie.myandroidchart.domain.Contact;
import cn.xie.myandroidchart.domain.Pie3DChart;
import cn.xie.myandroidchart.utils.Util;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.github.mikephil.charting.charts.PieChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 利用ichart.1.2.1.min.js来进行画图，而android代码只实现数据的封装，然后HTML通过js调用android方法来获取数据
 */
public class AndroidIChartJSActivity extends BaseActivity {
    private WebView barChart3D,pieChart3D;
    private static final String TAG = "AndroidIChartJSActivity";
    private Pie3DChart pie3DChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_ichart_js);
        initNavBar(true,"IChartJS之3D直方图");
        initView();
    }
    @SuppressLint("JavascriptInterface")
    private void initView() {
        barChart3D = findViewById(R.id.weView_barChart3D);
        barChart3D.getSettings().setJavaScriptEnabled(true); // 允许使用javascript脚本语言
        barChart3D.getSettings().setBuiltInZoomControls(true); // 设置可以缩放
        // 设置javaScript可用于操作AndroidIChartJSActivity类
        barChart3D.addJavascriptInterface(this,TAG);
        barChart3D.loadUrl("file:///android_asset/3dBarChart.html");
        //设置自适应屏幕，两者合用
        barChart3D.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        barChart3D.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小


        initPie3DChart();
        pieChart3D = findViewById(R.id.weView_pieChart3D);
        pieChart3D.getSettings().setJavaScriptEnabled(true); // 允许使用javascript脚本语言
        pieChart3D.getSettings().setBuiltInZoomControls(true); // 设置可以缩放
        // 设置javaScript可用于操作AndroidIChartJSActivity类，用于JS调用本类方法
        pieChart3D.addJavascriptInterface(this,TAG);
        //设置javaScript可用于操作Pie3DChart类，用于JS调用Pie3DChart类方法
        pieChart3D.addJavascriptInterface(pie3DChart,"Pie3DChart");
        pieChart3D.loadUrl("file:///android_asset/3dPieChart.html");
        pieChart3D.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        pieChart3D.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
    }

    private void initPie3DChart() {
        pie3DChart = new Pie3DChart();
        pie3DChart.setTitle("2012年7月份中国浏览器市场占有率Top6");
        pie3DChart.setPieWidth(800);
        pie3DChart.setPieHeight(600);
        pie3DChart.setPieRadius(250);
        pie3DChart.setPieZHigh(40);
        pie3DChart.setAlign("right");
        pie3DChart.setFootnote("Data from StatCounter");
        pie3DChart.setvAlign("bottom");
        pie3DChart.setRow(1);
    }

    /**
     * 实现将list转换成json格式字符串，由JS调用此方法
     * @return json格式的字符串
     */
    @JavascriptInterface
    public String getContacts() {
        List<Contact> contacts = Util.getContacts();
        String json = null;
        try {
            JSONArray array = new JSONArray();
            for (Contact contact : contacts) {

                JSONObject item = new JSONObject();
                item.put("name", contact.getName());
                item.put("value", contact.getValue());
                item.put("color", contact.getColor());
                array.put(item);
            }
            json = array.toString();
            Log.i(TAG, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    /**
     * 实现将list转换成json格式字符串，由JS调用此方法
     * @return json格式的字符串
     */
    @JavascriptInterface
    public String get3DPieCharContacts() {
        List<Contact> contacts = Util.getContacts();
        String json = null;
        try {
            JSONArray array = new JSONArray();
            for (Contact contact : contacts) {

                JSONObject item = new JSONObject();
                item.put("name", contact.getName());
                item.put("value", contact.getValue());
                item.put("color", contact.getColor());
                array.put(item);
            }
            json = array.toString();
            Log.i(TAG, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}
