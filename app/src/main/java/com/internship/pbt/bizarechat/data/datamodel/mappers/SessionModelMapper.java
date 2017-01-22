package com.internship.pbt.bizarechat.data.datamodel.mappers;


import com.internship.pbt.bizarechat.data.datamodel.SessionModel;
import com.internship.pbt.bizarechat.data.datamodel.response.SignInResponseModel;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponce;

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

    public static UserLoginResponce transform(SignInResponseModel response){
        UserLoginResponce userLoginResponce = null;
        if(response != null){
            userLoginResponce.setEmail(response.getEmail());
            userLoginResponce.setBlobId(response.getBlobId());
            userLoginResponce.setPhone(response.getPhone());
            userLoginResponce.setCreatedAt(response.getCreatedAt());
            userLoginResponce.setCustomParameters(response.getCustomParameters());
            userLoginResponce.setExternalUserId(response.getExternalUserId());
            userLoginResponce.setFacebookId(response.getFacebookId());
            userLoginResponce.setFullName(response.getFullName());
            userLoginResponce.setId(response.getId());
            userLoginResponce.setUpdatedAt(response.getUpdatedAt());
            userLoginResponce.setWebsite(response.getWebsite());
            userLoginResponce.setUserTags(response.getUserTags());
            userLoginResponce.setLastRequestAt(response.getLastRequestAt());
            userLoginResponce.setLogin(response.getLogin());
        }

        return userLoginResponce;
    }
}
