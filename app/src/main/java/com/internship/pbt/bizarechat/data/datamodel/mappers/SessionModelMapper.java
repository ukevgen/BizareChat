package com.internship.pbt.bizarechat.data.datamodel.mappers;


import com.internship.pbt.bizarechat.data.datamodel.SessionModel;
import com.internship.pbt.bizarechat.data.datamodel.response.SignInResponseModel;
import com.internship.pbt.bizarechat.data.datamodel.response.SignUpResponseModel;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponce;
import com.internship.pbt.bizarechat.domain.model.UserSignUpResponce;

public class SessionModelMapper {
    private SessionModelMapper() {
    }

    public static Session transform(SessionModel sessionModel) {
        Session session = null;
        if (sessionModel != null) {
            session = new Session();
            session.setApplicationId(sessionModel.getSession().getApplicationId());
            session.setId(sessionModel.getSession().getId());
            session.setToken(sessionModel.getSession().getToken());
            session.setUserId(sessionModel.getSession().getUserId());
        }
        return session;
    }

    public static UserLoginResponce transform(SignInResponseModel response) {
        UserLoginResponce userLoginResponce = null;
        if (response != null) {
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

    public static UserSignUpResponce transform(SignUpResponseModel response) {
        UserSignUpResponce userSignUpResponce = null;
        if (response != null) {
            userSignUpResponce.setEmail(response.getEmail());
            userSignUpResponce.setBlobId(response.getBlobId());
            userSignUpResponce.setPhone(response.getPhone());
            userSignUpResponce.setCreatedAt(response.getCreatedAt());
            userSignUpResponce.setCustomParameters(response.getCustomParameters());
            userSignUpResponce.setExternalUserId(response.getExternalUserId());
            userSignUpResponce.setFacebookId(response.getFacebookId());
            userSignUpResponce.setFullName(response.getFullName());
            userSignUpResponce.setId(response.getId());
            userSignUpResponce.setUpdatedAt(response.getUpdatedAt());
            userSignUpResponce.setWebsite(response.getWebsite());
            userSignUpResponce.setUserTags(response.getUserTags());
            userSignUpResponce.setLastRequestAt(response.getLastRequestAt());
            userSignUpResponce.setLogin(response.getLogin());
        }
        return userSignUpResponce;
    }

}
