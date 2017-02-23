package com.internship.pbt.bizarechat.query;

import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;

import java.util.List;

/**
 * Created by ukevgen on 21.02.2017.
 */

public class QueryBuilder {
    private static final int THREE = 3;
    private volatile static QueryBuilder INSTANCE;

    private DaoSession daoSession;

    private QueryBuilder(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public static QueryBuilder getQueryBuilder(DaoSession daoSession) {
        if (INSTANCE == null) {
            synchronized (QueryBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new QueryBuilder(daoSession);
                }
            }
        }
        return INSTANCE;
    }


    public List<DialogModel> getPrivateDialogs(int type) {
        List<DialogModel> modelList;
        modelList = daoSession
                .getDialogModelDao()
                .queryBuilder()
                .where(DialogModelDao.Properties.Type.eq(type))
                .orderAsc(DialogModelDao.Properties.LastMessageDateSent)
                .list();
        return modelList;
    }

    public List<DialogModel> getPublicDialogs(int type) {
        List<DialogModel> modelList;
        modelList = daoSession
                .getDialogModelDao()
                .queryBuilder()
                .where(DialogModelDao.Properties.Type.notEq(THREE))
                .orderAsc(DialogModelDao.Properties.LastMessageDateSent)
                .list();
        return modelList;
    }

    public boolean removeDialog(DialogModel model) {
        DialogModelDao modelDao = daoSession.getDialogModelDao();
        modelDao.deleteByKey(model.getDialogId());
        daoSession.getDialogModelDao().count();
        daoSession.clear();

        return true;
    }
}
