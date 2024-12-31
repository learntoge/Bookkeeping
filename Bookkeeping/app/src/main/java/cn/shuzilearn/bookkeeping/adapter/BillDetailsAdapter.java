

package cn.shuzilearn.bookkeeping.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.shuzilearn.bookkeeping.R;
import cn.shuzilearn.bookkeeping.application.Myapplication;
import cn.shuzilearn.bookkeeping.dao.billDao;
import cn.shuzilearn.bookkeeping.page.BilleditorActivity;
import cn.shuzilearn.bookkeeping.pojo.bill;
import cn.shuzilearn.bookkeeping.utils.ToastUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BillDetailsAdapter extends RecyclerView.Adapter<BillDetailsAdapter.ViewHolder> {

    private final billDao billdao; // 数据库访问对象
    private List<bill> billList;  // 数据源

    public BillDetailsAdapter(List<bill> billList) {
        this.billList = billList;
        billdao = Myapplication.getInstance().getDatabase().billdao();
    }

    @NonNull
    @Override
    public @NotNull BillDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bill_littlemod, viewGroup, false);
        return new BillDetailsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull BillDetailsAdapter.ViewHolder viewHolder, int position) {
        bill thebill = billList.get(position);

        // 设置视图内容
        viewHolder.bill_littlemod.setTag(thebill);

        // 笔记修改功能
        viewHolder.bill_littlemod.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), BilleditorActivity.class);
                intent.putExtra("id1",thebill.getId());
                intent.putExtra("amount1",thebill.getAmount());
                intent.putExtra("classify1",thebill.getClassify());
                intent.putExtra("time1",thebill.getTime());
                intent.putExtra("remark1",thebill.getRemark()==null?"":thebill.getRemark());

                v.getContext().startActivity(intent);

            }
        });
        viewHolder.bill_money.setText(thebill.getAmount() + "元");
        viewHolder.bill_class.setText(thebill.getClassify());
        viewHolder.bill_beizhu.setText("备注：" + (thebill.getRemark() == null ? "无" : thebill.getRemark()));

        // 删除按钮的点击事件
        viewHolder.deletebtn.setOnClickListener(v -> {
            bill b = (bill) viewHolder.bill_littlemod.getTag();

            // 从数据库中删除
            billdao.delete(b);

            // 从数据源中移除
            billList.remove(position);

            // 通知适配器更新
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, billList.size());

            // 显示删除成功提示
            // ToastUnit.showToast_s(v.getContext(), "删除成功");
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public void updateData(List<bill> newBillList) {
        this.billList = newBillList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bill_money;
        TextView bill_class;
        TextView bill_beizhu;
        TextView deletebtn;
        LinearLayout bill_littlemod;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            bill_littlemod = itemView.findViewById(R.id.saveTag);
            bill_money = itemView.findViewById(R.id.bill_money);
            bill_class = itemView.findViewById(R.id.bill_class);
            bill_beizhu = itemView.findViewById(R.id.bill_beizhu);
            deletebtn = itemView.findViewById(R.id.deletebtn);
        }
    }
}


//package cn.shuzilearn.bookkeeping.adapter;
//
//import android.annotation.SuppressLint;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import cn.shuzilearn.bookkeeping.R;
//import cn.shuzilearn.bookkeeping.application.Myapplication;
//import cn.shuzilearn.bookkeeping.dao.billDao;
//import cn.shuzilearn.bookkeeping.pojo.bill;
//import cn.shuzilearn.bookkeeping.unit.ToastUnit;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//
//public class BillDetailsAdapter extends RecyclerView.Adapter<BillDetailsAdapter.ViewHolder>  {
//
//    private final billDao billdao;
//    private List<bill> billList;
//
//    public BillDetailsAdapter(List<bill> billList) {
//        this.billList = billList;
//        billdao = Myapplication.getInstance().getDatabase().billdao();
//    }
//
//    @NonNull
//    @Override
//    public @NotNull BillDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
////        View view = View.inflate(viewGroup.getContext(), R.layout.bill_littlemod, null);
////        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bill_littlemod, viewGroup, false);
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.bill_littlemod, viewGroup, false);
//
//
//        BillDetailsAdapter.ViewHolder viewHolder = new BillDetailsAdapter.ViewHolder(view);
//        return viewHolder;
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(@NonNull @NotNull BillDetailsAdapter.ViewHolder viewHolder, int i) {
//        bill thebill = billList.get(i);
//        viewHolder.bill_littlemod.setTag(thebill);
//        viewHolder.bill_money.setText(thebill.getAmount()+"元");
//        viewHolder.bill_class.setText(thebill.getClassify());
//        viewHolder.bill_beizhu.setText("备注："+(thebill.getRemark()==null?"无":thebill.getRemark()));
//        viewHolder.deletebtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                bill b = (bill) viewHolder.bill_littlemod.getTag();
//                billdao.delete(b);
//                ToastUnit.showToast_s(v.getContext(),"删除成功");
//            }
//        });
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return billList.size();
//    }
//
//
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView bill_money;
//        TextView bill_class;
//        TextView bill_beizhu;
//        TextView deletebtn;
//        LinearLayout bill_littlemod;
//
//        public ViewHolder(@NonNull @NotNull View itemView) {
//            super(itemView);
//            bill_littlemod = itemView.findViewById(R.id.saveTag);
//            bill_money = itemView.findViewById(R.id.bill_money);
//            bill_class = itemView.findViewById(R.id.bill_class);
//            bill_beizhu = itemView.findViewById(R.id.bill_beizhu);
//            deletebtn = itemView.findViewById(R.id.deletebtn);
//        }
//    }
//}
