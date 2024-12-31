package cn.shuzilearn.bookkeeping.adapter;

import android.app.AppComponentFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.decoration.SpaceItemDecoration;
import cn.shuzilearn.bookkeeping.pojo.bill;
import cn.shuzilearn.bookkeeping.utils.ToastUtil;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.*;

public class BillModAdapter extends RecyclerView.Adapter<BillModAdapter.ViewHolder> {
    private List<bill> billList;
    private Map<String, List<bill>> keyORvalue;
    private String[] s1;

    // 构造函数传入数据

    public BillModAdapter(List<bill> billList) {
        this.billList = billList;
        initdata();

    }

    @NonNull
    @Override
    public @NotNull BillModAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
//        View view = View.inflate(viewGroup.getContext(),R.layout.billshow_module,null);
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.billshow_module, viewGroup, false);
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.billshow_module, viewGroup, false);


        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BillModAdapter.ViewHolder viewHolder, int i) {
        String key = s1[i];
        viewHolder.time.setText(key);

        List<bill> bills = keyORvalue.get(key);
        BillDetailsAdapter billDetailsAdapter = new BillDetailsAdapter(bills);
        viewHolder.recyclerView.setAdapter(billDetailsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(viewHolder.recyclerView.getContext());
        viewHolder.recyclerView.setLayoutManager(linearLayoutManager);

        int spacing = 4; // 16dp 的间距
        viewHolder.recyclerView.addItemDecoration(new SpaceItemDecoration(spacing));



    }

    @Override
    public int getItemCount() {
        return s1.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time = null;
        RecyclerView recyclerView = null;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.dateshow);
            recyclerView = itemView.findViewById(R.id.shownote_mod);
        }
    }


    private void initdata() {
        // 定义日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // 使用 TreeMap 按日期降序排序
        keyORvalue = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    Date date1 = dateFormat.parse(o1);
                    Date date2 = dateFormat.parse(o2);
                    return date2.compareTo(date1); // 按日期降序
                } catch (ParseException e) {
                    e.printStackTrace();
                    return o1.compareTo(o2); // 如果解析失败，按默认字符串排序
                }
            }
        });

        // 填充数据到 TreeMap
        for (bill bill : billList) {
            if (keyORvalue.get(bill.getTime()) == null) {
                ArrayList<bill> temp = new ArrayList<>();
                temp.add(bill);
                keyORvalue.put(bill.getTime(), temp);
            } else {
                keyORvalue.get(bill.getTime()).add(bill);
            }
        }

        // 提取排序后的键集合
        Set<String> strings = keyORvalue.keySet();
        s1 = strings.toArray(new String[0]); // 将排序后的键集合直接转为数组
    }



}
