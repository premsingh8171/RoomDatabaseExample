package com.journaldev.androidroomtodolist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.journaldev.androidroomtodolist.modelEntitiyClass.Todo;
import com.journaldev.androidroomtodolist.dao.DaoAccess;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public static final String DB_NAME = "app_db";
    public static final String TABLE_NAME_TODO = "todo";

    public abstract DaoAccess daoAccess();

}
