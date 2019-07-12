package cn.edu.hznu.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DatabaseProvider extends ContentProvider {

    public static final int CONTACT_DIR=0;  //访问contact表中的所有数据
    public static final int CONTACT_ITEM=1; //访问contact表中的单条数据
    public static final String AUTHORITY="cn.edu.hznu.databasetest.provider";

    private  static UriMatcher uriMatcher;
    private MyDatabaseHelper dbHelper;

    //初始化UriMatcher
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"contact",CONTACT_DIR);
        uriMatcher.addURI(AUTHORITY,"contact/#",CONTACT_ITEM);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper=new MyDatabaseHelper(getContext(),"ContactList.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //查询数据
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case CONTACT_DIR:
                cursor = db.query("contact", projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case CONTACT_ITEM:
                String contactId = uri.getPathSegments().get(1);
                cursor = db.query("contact", projection,"id = ?",
                        new String[] { contactId }, null, null,
                        sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //添加数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case CONTACT_DIR:
            case CONTACT_ITEM:
                long newContactId = db.insert(
                        "contact", null, values);
                uriReturn = Uri.parse("content://"
                        + AUTHORITY + "/contact/" + newContactId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        //更新数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case CONTACT_DIR:
                updatedRows = db.update("contact", values, selection, selectionArgs);
                break;
            case CONTACT_ITEM:
                String contactId = uri.getPathSegments().get(1);
                updatedRows = db.update("contact", values,
                        "id = ?", new String[] { contactId });
                break;
            default:
                break;
        }
        return updatedRows;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //删除数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case CONTACT_DIR:
                deletedRows = db.delete("contact", selection, selectionArgs);
                break;
            case CONTACT_ITEM:
                String contactId = uri.getPathSegments().get(1);
                deletedRows = db.delete("contact",
                        "id = ?", new String[] { contactId });
                break;
            default:
                break;
        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CONTACT_DIR:
                return "vnd.android.cursor.dir/vnd.cn.edu.hznu.databasetest.provider.contact";
            case CONTACT_ITEM:
                return "vnd.android.cursor.item/vnd.cn.edu.hznu.databasetest.provider.contact";
        }
        return null;
    }

}
