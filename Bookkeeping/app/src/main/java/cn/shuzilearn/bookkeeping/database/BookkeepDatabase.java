package cn.shuzilearn.bookkeeping.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import cn.shuzilearn.bookkeeping.dao.noteDao;
import cn.shuzilearn.bookkeeping.pojo.bill;
import cn.shuzilearn.bookkeeping.pojo.note;
import cn.shuzilearn.bookkeeping.pojo.user;
import cn.shuzilearn.bookkeeping.dao.userDao;
import cn.shuzilearn.bookkeeping.dao.billDao;

@Database(entities = {user.class,bill.class, note.class}, version = 2, exportSchema = false)
public abstract class BookkeepDatabase extends RoomDatabase {
    public abstract userDao userdao();
    public abstract billDao billdao();
    public abstract noteDao notedao();
}