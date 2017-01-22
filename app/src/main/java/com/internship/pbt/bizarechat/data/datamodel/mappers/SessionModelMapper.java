package com.internship.pbt.bizarechat.data.datamodel.mappers;


import com.internship.pbt.bizarechat.data.datamodel.SessionModel;
import com.internship.pbt.bizarechat.domain.model.Session;

public class SessionModelMapper {
    private SessionModelMapper(){}

    public static Session transform(SessionModel sessionModel){
        Session session = null;
        if(sessionModel != null){
            session = new Session();
            session.setApplicationId(sessionModel.getSession().getApplicationId());
            session.setId(sessionModel.getSession().getId());
            session.setToken(sessionModel.getSession().getToken());
            session.setUserId(sessionModel.getSession().getUserId());
        }
        return session;
    }
}
