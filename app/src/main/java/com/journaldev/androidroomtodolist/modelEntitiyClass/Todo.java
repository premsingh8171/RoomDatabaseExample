package com.journaldev.androidroomtodolist.modelEntitiyClass;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.journaldev.androidroomtodolist.database.MyDatabase;

import java.io.Serializable;

@Entity(tableName = MyDatabase.TABLE_NAME_TODO)
public class Todo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int todo_id;

    public String name;

    public String description;

    public String category;

    @Ignore
    public String priority;

}
