package com.example.myda.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

public class NoteDao extends AbstractDao<Note, Long> {

    public static final String TABLENAME = "NOTE_01";


    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final  static Property author = new Property(3,String.class,"author",false,"AUTHOR");
        public final static Property Date = new Property(4, String.class, "date", false, "DATE");
    }


    public NoteDao(DaoConfig config) {
        super(config);
    }
    
    public NoteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOTE_01\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TITLE\" TEXT," + // 1: title
                "\"CONTENT\" TEXT," +// 2: content
                "\"AUTHOR\" TEXT," + //3.author
                "\"DATE\" TEXT);"); // 4. data
    }

    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOTE_01\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Note entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }


        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }

        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }

        String author = entity.getAuthor();
        if (author != null){
            stmt.bindString(4,author);
        }

        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(5, date);
        }

    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Note entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }

        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }

        String author = entity.getAuthor();
        if (author != null){
            stmt.bindString(4,author);
        }

        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(5, date);
        }
 

    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Note readEntity(Cursor cursor, int offset) {
        Note entity = new Note( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // content
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // author
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) //date
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Note entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAuthor(cursor.isNull(offset + 3) ? null :cursor.getString(offset + 3));
        entity.setDate(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
    }
    
    @Override
    protected final Long updateKeyAfterInsert(Note entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Note entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Note entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}