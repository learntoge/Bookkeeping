package cn.shuzilearn.bookkeeping.dao;

import androidx.room.*;
import cn.shuzilearn.bookkeeping.pojo.user;

@Dao
public interface userDao {

    @Insert
    void insert(user user);

    @Update
    void update(user user);

    @Delete
    void delete(user user);

    @Query("SELECT * FROM user WHERE phone = :phone")
    user findByPhone(String phone);

    @Query("SELECT * FROM user WHERE uid = :uid")
    user findById(int uid);

    @Query("DELETE FROM user")
    void deleteAll();
}
