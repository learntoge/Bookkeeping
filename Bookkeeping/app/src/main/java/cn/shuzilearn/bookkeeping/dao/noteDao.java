package cn.shuzilearn.bookkeeping.dao;

import androidx.room.*;
import cn.shuzilearn.bookkeeping.pojo.note;

import java.util.List;

@Dao
public interface noteDao {
    @Insert
    long insert(note note); // 插入并返回新行的 ID

    @Update
    int update(note note); // 更新并返回受影响的行数

    @Query("DELETE FROM NOTE WHERE ID = :id")
    void deleteOfID(int id); // 删除指定 ID 的记录

    @Query("DELETE FROM NOTE")
    void deleteAll(); // 删除所有记录

    @Query("SELECT * FROM NOTE ORDER BY CREATETIME ASC")
    List<note> getAll(); // 获取所有记录

    @Query("SELECT * FROM NOTE WHERE ID = :id")
    note getByID(int id); // 根据 ID 获取记录
}
