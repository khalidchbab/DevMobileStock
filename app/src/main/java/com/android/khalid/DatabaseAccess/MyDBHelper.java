package com.android.khalid.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.android.khalid.Models.Article;
import com.android.khalid.Models.Operation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(@Nullable Context context) {
        super(context, "gestion_product",null,  1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE article(prod_id INTEGER not null Primary key autoIncrement,prod_name TEXT not null,prod_price REAL NOT NULL,prod_qte INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE operation(trans_id INTEGER not null Primary key autoIncrement,prod_name TEXT not null,prod_price REAL NOT NULL,qte INTEGER NOT NULL,dateOperation TEXT NOT NULL)");
    }

    public long addProduct(Article article) throws SQLException {
        SQLiteDatabase  db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("prod_name", article.getProdName());
        contentValues.put("prod_price", article.getProdPrice());
        contentValues.put("prod_qte", article.getProdQte());
        return db.insertOrThrow("article",null,contentValues);
    }
    public ArrayList<Article> getAllProducts() throws SQLException {
        SQLiteDatabase  db =  this.getReadableDatabase();
        ArrayList<Article> articles = new ArrayList<Article>();
        Cursor cursor = db.rawQuery("SELECT * FROM article ORDER BY prod_id DESC",null);
        while(cursor.moveToNext()){

            articles.add(new Article(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getInt(3)));
        }
        cursor.close();
        return articles;
    }
    public long addTranscation(Article article, int qte) throws SQLException {
        SQLiteDatabase  db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("prod_qte", article.getProdQte() - qte);
        contentValues.put("prod_name", article.getProdName());
        contentValues.put("prod_price", article.getProdPrice());
        contentValues.put("qte",qte);
        contentValues.put("dateOperation",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        long transId =  db.insertOrThrow("operation",null,contentValues);
        db.update("article",contentValues2,"prod_id = ?",new String[]{String.valueOf(article.getProdId())});
        return  transId;
    }
    public ArrayList<Operation> getAllTransactions() throws SQLException {
        SQLiteDatabase  db =  this.getReadableDatabase();
        ArrayList<Operation> operations = new ArrayList<Operation>();
        Cursor cursor = db.rawQuery("SELECT * FROM operation",null);
        while(cursor.moveToNext()){

            operations.add(new Operation(cursor.getLong(0),cursor.getString(1),cursor.getDouble(2), cursor.getInt(3),cursor.getString(4)));
        }
        cursor.close();
        return operations;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS article");
        db.execSQL("DROP TABLE IF EXISTS operation");
        onCreate(db);
    }
}
