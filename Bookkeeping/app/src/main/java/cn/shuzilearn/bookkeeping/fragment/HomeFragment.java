package cn.shuzilearn.bookkeeping.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.adapter.BillModAdapter;
import cn.shuzilearn.bookkeeping.application.Myapplication;
import cn.shuzilearn.bookkeeping.dao.billDao;
import cn.shuzilearn.bookkeeping.decoration.SpaceItemDecoration;
import cn.shuzilearn.bookkeeping.pojo.bill;
import cn.shuzilearn.bookkeeping.utils.ToastUtil;

import java.util.*;


public class HomeFragment extends Fragment {


    private String mParam1;
    private String mParam2;
    private View view;
    private billDao billdao;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        billdao = Myapplication.getInstance().getDatabase().billdao();
        view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.showAllBill);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        BillLoading();

    }

    // 账单加载函数
    private void BillLoading() {
        List<bill> billList = billdao.getAllBillsOrderedTime();

        // 如果没有账单数据，显示提示
        if (billList == null || billList.size() == 0) {
            ToastUtil.showToast_s(getContext(), "你还没有账单，点击右上角进行添加！");
            return;
        }
//        ToastUnit.showToast_s(getContext(), "正在初始化");
        BillModAdapter adapter = new BillModAdapter(billList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        int spacing = 16; // 16dp 的间距
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacing));


    }


}