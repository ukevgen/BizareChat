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
            userLoginResponce.setEmail(response.getUser().getEmail());
            userLoginResponce.setBlobId(response.getUser().getBlobId());
            userLoginResponce.setPhone(response.getUser().getPhone());
            userLoginResponce.setCreatedAt(response.getUser().getCreatedAt());
            userLoginResponce.setCustomParameters(response.getUser().getCustomData());
            userLoginResponce.setExternalUserId(response.getUser().getExternalUserId());
            userLoginResponce.setFacebookId(response.getUser().getFacebookId());
            userLoginResponce.setFullName(response.getUser().getFullName());
            userLoginResponce.setId(response.getUser().getId());
            userLoginResponce.setUpdatedAt(response.getUser().getUpdatedAt());
            userLoginResponce.setWebsite(response.getUser().getWebsite());
            userLoginResponce.setUserTags(response.getUser().getUserTags());
            userLoginResponce.setLastRequestAt(response.getUser().getLastRequestAt());
            userLoginResponce.setLogin(response.getUser().getLogin());
        }
        return userLoginResponce;
    }
}
