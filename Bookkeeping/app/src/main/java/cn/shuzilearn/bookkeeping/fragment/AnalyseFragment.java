package cn.shuzilearn.bookkeeping.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.application.Myapplication;
import cn.shuzilearn.bookkeeping.dao.billDao;
import cn.shuzilearn.bookkeeping.pojo.bill;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class AnalyseFragment extends Fragment {

    private billDao billdao;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_analyse, container, false);
        billdao = Myapplication.getInstance().getDatabase().billdao();
        xuanran();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void xuanran() {
        // 从数据库中获取数据
        List<bill> list = billdao.getAllBills();

        if (list.isEmpty()) {
            return; // 如果没有数据，直接返回
        }

        Map<String, Double> classifyAmountMap = list.stream()
                .collect(Collectors.groupingBy(
                        bill::getClassify, // 按 'classify' 分组
                        Collectors.summingDouble(bill::getAmount) // 按 'amount' 汇总
                ));

        // 准备饼图数据
        PieDataSet pieDataSet = new PieDataSet(new ArrayList<>(), "消费分类");
        for (Map.Entry<String, Double> entry : classifyAmountMap.entrySet()) {
            PieEntry pieEntry = new PieEntry(entry.getValue().floatValue(), entry.getKey());
            pieDataSet.addEntry(pieEntry);

        }

        // 设置百分比显示数据
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f%%", value);  // 这里使用 %.1f 来保留一位小数，% 是直接拼接的
            }
        });
        /**
         * setValueTextColor(int color)：设置此对象的值文本（绘制值标签的颜色）的颜色。DataSet
         * setValueTextColors(List<Integer> colors)：设置要用作值颜色的颜色列表。
         * setValueTextSize(float size)：设置此对象的值文本的大小（以 dp 为单位）。DataSet
         * setValueTypeface(Typeface tf)：设置此对象的所有值标签。TypefaceDataSet
         * setValueFormatter(ValueFormatter f)：为此对象设置自定义，更多内容请见此处。ValueFormatterDataSetValueFormatter
         * setDrawValues(boolean enabled)：启用/禁用此对象的绘图值（值文本）。
         *
         * */

        // 设置饼图标签的字体大小
        pieDataSet.setValueTextSize(18f);  // 设置数据项（value）的字体大小
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setSliceSpace(2f);  // 设置切片之间的间隙
        pieDataSet.setSelectionShift(5f);

        // 饼图颜色列表
        int[] colors = {
                Color.parseColor("#FF5733"),  // 红色
                Color.parseColor("#33FF57"),  // 绿色
                Color.parseColor("#3357FF"),  // 蓝色
                Color.parseColor("#FF33A8"),  // 粉色
                Color.parseColor("#FFD700"),  // 金色
                Color.parseColor("#800080"),  // 紫色
                Color.parseColor("#FF6347"),  // 番茄红
                Color.parseColor("#00CED1"),  // 深青色
                Color.parseColor("#FF1493"),  // 深粉色
                Color.parseColor("#FFD700"),  // 金黄色
                Color.parseColor("#8A2BE2"),  // 紫罗兰色
                Color.parseColor("#32CD32"),  // 石灰绿
                Color.parseColor("#FF8C00"),  // 暗橙色
                Color.parseColor("#20B2AA"),  // 海洋绿
                Color.parseColor("#DC143C")   // 猩红色
        };
        pieDataSet.setColors(colors);


        PieData pieData = new PieData(pieDataSet);

        // 配置饼图
        PieChart pieChart = view.findViewById(R.id.pieChart);

        pieChart.setCenterText("消费分析(占比)");
        pieChart.setCenterTextSize(20f);
        pieChart.setCenterTextColor(R.color.purple_200);
        pieChart.getLegend().setEnabled(false);
        pieChart.setEntryLabelTextSize(14f);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(20f);
        pieChart.getDescription().setEnabled(false);
//        pieChart.getDescription().setText("消费类型占比分析图");
//        pieChart.getDescription().setTextSize(20);


        pieChart.setData(pieData);

        pieChart.invalidate();  // 刷新饼图

        /**
         * 上面是饼图
         * ----------------分割线--------------
         * 下面是柱状图
         * */

        // 获取 BarChart 控件
        BarChart barChart = view.findViewById(R.id.barChart);

        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(classifyAmountMap.entrySet());
        sortedList.sort(Map.Entry.comparingByKey());  // 按键（分类）排序

        // 创建 BarEntry 数据集
        // 准备x轴标签的数据
        Set<String> strings = classifyAmountMap.keySet();
        String[] s = new String[strings.size()];
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < sortedList.size(); i++) {
            Map.Entry<String, Double> entry = sortedList.get(i);
            entries.add(new BarEntry(i, entry.getValue().floatValue()));  // 使用索引 i 作为横坐标
            s[i] = entry.getKey();
        }




        // 创建 BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "消费类型");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // 创建 BarData
        BarData barData = new BarData(dataSet);
        barData.setValueTextSize(16);  // 设置柱子上文本的大小
        barChart.setData(barData);


        // 设置 X 轴标签
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(s));  // 自定义 X 轴标签
        xAxis.setGranularity(1f);  // 设置 X 轴的最小间隔，防止标签重叠
        xAxis.setLabelRotationAngle(60f);  // 旋转标签，避免过长标签重叠
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // 设置标签显示位置
        xAxis.setTextSize(14f);

        // 设置 Y 轴配置
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);  // 设置 Y 轴从 0 开始
        barChart.getAxisRight().setEnabled(false);  // 禁用右侧 Y 轴

        // 设置柱状图描述
        barChart.getDescription().setEnabled(false);
//        barChart.getDescription().setText("各类型消费金额统计");
//        barChart.getDescription().setYOffset(20f);
//        barChart.getDescription().setTextSize(20f);
//        barChart.getDescription().setYOffset(-60f);  // 调整描述的垂直位置

        // 配置柱状图，使其适应屏幕
        barChart.setFitBars(true);

        // 图例设置
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);


        // 刷新图表
        barChart.invalidate();  // 刷新图表数据


    }
}
