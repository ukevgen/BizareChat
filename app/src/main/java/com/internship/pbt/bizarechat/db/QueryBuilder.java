package com.internship.pbt.bizarechat.db;

import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.UserModelDao;

import java.util.List;


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
        daoSession.getDialogModelDao().delete(model);
        return true;
    }

    public boolean saveNewDialog(DialogModel model) {
        daoSession.getDialogModelDao().insertOrReplace(model);
        return true;
    }

    public boolean addUserToUsersDao(UserModel user) {
        daoSession.getUserModelDao().insertOrReplace(user);
        return true;
    }

    public boolean isUserExist(int lastUserId) {
        long count = daoSession
                .getUserModelDao()
                .queryBuilder()
                .where(UserModelDao.Properties.UserId.eq(lastUserId))
                .count();
        return count == 0;
    }

    public int getUserBlobId(int lastUserId) {
        List<UserModel> user = daoSession
                .getUserModelDao()
                .queryBuilder()
                .where(UserModelDao.Properties.UserId.eq(lastUserId))
                .list();
        if (user.size() != 0)
            return user.get(0).getBlobId();
        else
            return -1;
    }
}
