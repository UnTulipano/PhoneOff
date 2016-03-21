package com.iteam.phoneoff;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;
import com.iteam.phoneoff.utils.Utils;
import com.iteam.phoneoff.view.CircleBar;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SecondActivity extends BaseActivity implements OnChartValueSelectedListener {

    //无需调用findViewById和setOnclickListener等
    @ViewInject(id=R.id.txt_rate)
    TextView txt_rate;
    @ViewInject(id=R.id.txt_score)
    TextView txt_score;
    private PieChart mChart;
    private TextView tvX, tvY;
    private LineChart[] mCharts = new LineChart[1];
    private Typeface mTf;
    protected String[] mMonths = new String[]{Utils.getADay(-6), Utils.getADay(-5), Utils.getADay(-4), Utils.getADay(-3), Utils.getADay(-2), Utils.getADay(-1), Utils.getADay(0)};
    @Override
    protected void onResume() {
        super.onResume();
        //使用频率
        txt_rate.setText(Utils.rateEvaluation(this));
//        toast(this,Utils.getScore(this)+"");

        /**
         * 打分初始化
         */
        CircleBar circleBar = (CircleBar)findViewById(R.id.circleBar);
        circleBar.setColor(227, 23, 13);
        circleBar.setTxt("");
        circleBar.setMaxstepnumber(100);
        circleBar.update(100 - (int) Utils.getScore(this), 1000);
        txt_score.setText(Utils.scoreEvaluation(this));
        /**
         * 折线图初始化
         */
        mCharts[0] = (LineChart) findViewById(R.id.chart11);


        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        LineData data = getData(7, 200);

        for (int i = 0; i < mCharts.length; i++)
            // add some transparency to the color with "& 0x90FFFFFF"
            setupChart(mCharts[i], data, mColors[i % mColors.length]);


       /* final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what == 1){
                    toast(getApplicationContext(), "一秒后执行");


                }
            }
        };

        final Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }

        });*/

        /**
         * 饼图初始化
         */
        mChart = (PieChart) findViewById(R.id.chart1);

        // change the color of the center-hole
        mChart.setHoleColor(Color.rgb(235, 235, 235));

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setValueTypeface(tf);
        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        mChart.setHoleRadius(60f);

        mChart.setDescription("");

        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);

        mChart.setDrawHoleEnabled(true);

        mChart.setRotationAngle(0);

        // draws the corresponding description value into the slice
        mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);

        // display percentage values
        mChart.setUsePercentValues(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(SecondActivity.this);
        // mChart.setTouchEnabled(false);

        mChart.setCenterText("使用时间分布");
        mChart.setCenterTextSize(20f);

        setData(4, 100);

        mChart.animateXY(1500, 1500);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);


    }

    /**
     * 折线图
     */
    private int[] mColors = new int[]{
            Color.rgb(255, 255, 255),
            Color.rgb(240, 240, 30),
            Color.rgb(89, 199, 250),
            Color.rgb(250, 104, 104)
    };

    private void setupChart(LineChart chart, LineData data, int color) {

        // if enabled, the chart will always start at zero on the y-axis
        chart.setStartAtZero(true);

        // disable the drawing of values into the chart
        chart.setDrawYValues(false);

        chart.setDrawBorder(false);

        // no description text
        chart.setDescription("");
        chart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid lines
        chart.setDrawVerticalGrid(false);
        // mChart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false);
        chart.setGridColor(Color.parseColor("#66f9cf") & 0x70FFFFFF);
        // 表格线的线宽
        chart.setGridWidth(14.25f);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setBackgroundColor(color);

        chart.setValueTypeface(mTf);

        // add data
        chart.setData(data);

        // get the legend (only possible after setting data)
        // 设置标示，就是那个一组y的value的
        Legend l = chart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(10f);
        l.setTextColor(Color.parseColor("#66f9cf"));
        l.setTypeface(mTf);

        YLabels y = chart.getYLabels();
        y.setTextColor(Color.parseColor("#66f9cf"));
        y.setTypeface(mTf);
        // y轴上的标签的显示的个数
        y.setLabelCount(8);
        y.setTextSize(15f);

        XLabels x = chart.getXLabels();
        x.setTextColor(Color.parseColor("#66f9cf"));
        x.setTypeface(mTf);
        x.setTextSize(15f);

        // animate calls invalidate()...
        chart.animateX(2500);
    }

    private LineData getData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(mMonths[i % 7]);
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            if( i == count-1) {
                long m = Utils.getTodaySumMinutes(this);
                yVals.add(new Entry(Utils.timeInMillisToMinutes(m), i));
            }else {
//                long m = Utils.getTodaySumMinutes(this);
//                yVals.add(new Entry(Utils.timeInMillisToMinutes(m), i));
                int m = Utils.timeInMillisToMinutes(Utils.getSomedaySumMinutes(this, i - 6, i - 5));
                yVals.add(new Entry(m, i));
            }
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "Minute");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.setLineWidth(2.75f);
        set1.setCircleSize(4f);
        set1.setColor(Color.parseColor("#66f9cf"));
        set1.setCircleColor(Color.parseColor("#66f9cf"));
        set1.setHighLightColor(Color.parseColor("#66f9cf"));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        return data;
    }

    /**
     * 饼图
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                if (mChart.isDrawYValuesEnabled())
                    mChart.setDrawYValues(false);
                else
                    mChart.setDrawYValues(true);
                mChart.invalidate();
//                removeLastEntry();
                break;
            }
            case R.id.actionTogglePercent: {
                if (mChart.isUsePercentValuesEnabled())
                    mChart.setUsePercentValues(false);
                else
                    mChart.setUsePercentValues(true);
                mChart.invalidate();
//                addEntry();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {
                if (mChart.isDrawXValuesEnabled())
                    mChart.setDrawXValues(false);
                else
                    mChart.setDrawXValues(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.animateX: {
                mChart.animateX(1800);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1800);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1800, 1800);
                break;
            }
        }
        return true;
    }

    private String[] mParties = new String[] {
            "凌晨", "早上", "下午", "晚上", "Party E", "Party F", "Party G"
    };


    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        StringBuffer sb = new StringBuffer();
        List list = Utils.getYesterdayDistribute(this, -1);
        for (int i = 0; i < count; i++) {
            //yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
            float f =Float.parseFloat(list.get(i).toString());
            if(f == 0){
//                f=3f;
//                f=(float)Math.random();
            }
            yVals1.add(new Entry(f, i));
            sb.append(f +  "   ");
        }
//        toast(this, sb.toString());

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet set1 = new PieDataSet(yVals1, "Election Results");
        set1.setSliceSpace(3f);
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData data = new PieData(xVals, set1);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
