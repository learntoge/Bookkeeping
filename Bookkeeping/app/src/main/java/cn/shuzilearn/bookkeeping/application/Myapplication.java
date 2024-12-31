package cn.shuzilearn.bookkeeping.application;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.room.Room;
import android.app.Application;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import cn.shuzilearn.bookkeeping.database.BookkeepDatabase;



public class Myapplication extends Application {
    private static Myapplication instance;
    private BookkeepDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                // 假设我们要添加一个名为 "new_column" 的新列
                database.execSQL("ALTER TABLE note ADD COLUMN new_column INTEGER DEFAULT 0");
            }
        };

        // 初始化Room数据库
        database = Room.databaseBuilder(getApplicationContext(), BookkeepDatabase.class, "bookkeep_database")
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries() // 只适用于轻量级的操作，实际开发中应避免在主线程执行数据库操作
                .build();
    }

    public static Myapplication getInstance() {
        return instance;
    }

    public BookkeepDatabase getDatabase() {
        return database;
    }
}
