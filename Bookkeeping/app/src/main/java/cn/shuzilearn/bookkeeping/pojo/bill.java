package cn.shuzilearn.bookkeeping.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



@Entity(tableName = "bill")
public class bill {
    @PrimaryKey(autoGenerate = true)
    private int id;// id
    private double amount; // 消费金额
    private String classify; // 分类
    private String  time; // 消费时间
    private String remark; // 备注信息

    @Ignore
    public bill(double amount, String classify, String time, String remark) {
        this.amount = amount;
        this.classify = classify;
        this.time = time;
        this.remark = remark;
    }

    public bill(double amount, String classify, String time) {
        this.amount = amount;
        this.classify = classify;
        this.time = time;
    }

    public bill() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "bill{" +
                "id=" + id +
                ", amount=" + amount +
                ", classify='" + classify + '\'' +
                ", time=" + time +
                ", remark='" + remark + '\'' +
                '}';
    }
}
