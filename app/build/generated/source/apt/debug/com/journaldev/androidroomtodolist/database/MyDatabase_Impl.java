package com.journaldev.androidroomtodolist.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import com.journaldev.androidroomtodolist.dao.DaoAccess;
import com.journaldev.androidroomtodolist.dao.DaoAccess_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;

public class MyDatabase_Impl extends MyDatabase {
  private volatile DaoAccess _daoAccess;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `todo` (`todo_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `description` TEXT, `category` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"fb5d2a61b5db4a077552c16dc9f875f9\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `todo`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTodo = new HashMap<String, TableInfo.Column>(4);
        _columnsTodo.put("todo_id", new TableInfo.Column("todo_id", "INTEGER", true, 1));
        _columnsTodo.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsTodo.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsTodo.put("category", new TableInfo.Column("category", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTodo = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTodo = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTodo = new TableInfo("todo", _columnsTodo, _foreignKeysTodo, _indicesTodo);
        final TableInfo _existingTodo = TableInfo.read(_db, "todo");
        if (! _infoTodo.equals(_existingTodo)) {
          throw new IllegalStateException("Migration didn't properly handle todo(com.journaldev.androidroomtodolist.modelEntitiyClass.Todo).\n"
                  + " Expected:\n" + _infoTodo + "\n"
                  + " Found:\n" + _existingTodo);
        }
      }
    }, "fb5d2a61b5db4a077552c16dc9f875f9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "todo");
  }

  @Override
  public DaoAccess daoAccess() {
    if (_daoAccess != null) {
      return _daoAccess;
    } else {
      synchronized(this) {
        if(_daoAccess == null) {
          _daoAccess = new DaoAccess_Impl(this);
        }
        return _daoAccess;
      }
    }
  }
}
