package com.internship.pbt.bizarechat.db;


import android.content.Context;
import android.util.Log;

import com.internship.pbt.bizarechat.data.datamodel.DaoMaster;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.logs.Logger;

import org.greenrobot.greendao.database.Database;

public class DBHelper {
    private static final String DB_NAME = "bizare-db";
    private static final String TAG = "DB";
    private Database db = null;
    private DaoSession session = null;
    private Context context;

    public DBHelper(Context context) {
        this.context = context;
    }


/*DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "bizare-db");
    db = openHelper.getWritableDb();    */

    private DaoMaster getMaster() {
        if (db == null) {
            db = getDatabase(DB_NAME, false);
        }
        return new DaoMaster(db);
    }

    public DaoSession getSession(boolean newSession) {
        if (newSession) {
            return getMaster().newSession();
        }
        if (session == null) {
            session = getMaster().newSession();
        }
        return session;
    }

    private synchronized Database getDatabase(String name, boolean readOnly) {
        String s = "getDB(" + name + ",readonly=" + (readOnly ? "true" : "false") + ")";
        try {
            readOnly = false;
            Log.i(TAG, s);
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
            if (readOnly) {
                return helper.getReadableDb();
            } else {
                return helper.getWritableDb();
            }
        } catch (Exception ex) {
            Logger.logExceptionToFabric(ex);
            Log.e(TAG, s, ex);
            return null;
        } catch (Error err) {
            Logger.logExceptionToFabric(err);
            Log.e(TAG, s, err);
            return null;
        }
    }

}
