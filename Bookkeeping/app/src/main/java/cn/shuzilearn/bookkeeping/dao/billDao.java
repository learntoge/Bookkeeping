package cn.shuzilearn.bookkeeping.dao;

import androidx.room.*;
import cn.shuzilearn.bookkeeping.pojo.bill;

import java.util.List;

@Dao
public interface billDao {

    @Insert
    void insert(bill bill);

    @Update
    void update(bill bill);

    @Delete
    void delete(bill bill);

    @Query("SELECT * FROM bill")
    List<bill> getAllBills();

    @Query("SELECT * FROM bill ORDER BY time DESC ")
    List<bill> getAllBillsOrderedTime();

    @Query("SELECT * FROM bill WHERE classify = :classify")
    List<bill> getBillsByClassify(String classify);

    @Query("SELECT * FROM bill WHERE classify = :id")
    bill getBillsById(int id);

    @Query("DELETE FROM bill")
    void deleteAll();


}
