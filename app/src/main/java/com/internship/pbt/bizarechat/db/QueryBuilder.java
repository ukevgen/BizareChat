package com.internship.pbt.bizarechat.db;

import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.UserModelDao;

import java.util.List;

public class QueryBuilder {
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


    public List<DialogModel> getPrivateDialogs() {
        List<DialogModel> modelList;
        modelList = daoSession
                .getDialogModelDao()
                .queryBuilder()
                .where(DialogModelDao.Properties.Type.eq(DialogsType.PRIVATE_CHAT))
                .orderDesc(DialogModelDao.Properties.LastMessageDateSent)
                .list();
        return modelList;
    }

    public List<DialogModel> getPublicDialogs() {
        List<DialogModel> modelList;
        modelList = daoSession
                .getDialogModelDao()
                .queryBuilder()
                .where(DialogModelDao.Properties.Type.notEq(DialogsType.PRIVATE_CHAT))
                .orderDesc(DialogModelDao.Properties.LastMessageDateSent)
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

    public boolean isUserExist(long userId) {
        List<UserModel> users = daoSession
                .getUserModelDao()
                .queryBuilder()
                .where(UserModelDao.Properties.UserId.eq(userId))
                .list();
        for (UserModel m : users) {
            if (m.getUserId() == userId)
                break;
        }

        return users.size() != 0;
    }

    public Integer getUserBlobId(long lastUserId) {
        List<UserModel> users = daoSession
                .getUserModelDao()
                .queryBuilder()
                .where(UserModelDao.Properties.UserId.eq(lastUserId))
                .list();

        return users.get(0).getBlobId();
    }
}
