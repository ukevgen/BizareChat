package com.internship.pbt.bizarechat.data.datamodel.mappers;


import com.internship.pbt.bizarechat.data.datamodel.SessionModel;
import com.internship.pbt.bizarechat.data.datamodel.response.SignInResponseModel;
import com.internship.pbt.bizarechat.data.datamodel.response.SignUpResponseModel;
import com.internship.pbt.bizarechat.domain.model.Session;
//import com.internship.pbt.bizarechat.domain.model.UserLoginResponce;
import com.internship.pbt.bizarechat.domain.model.UserSignUpResponce;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponse;

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

    public static UserLoginResponse transform(SignInResponseModel response) {
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        if (response != null) {
            userLoginResponse.setEmail(response.getUser().getEmail());
            userLoginResponse.setBlobId(response.getUser().getBlobId());
            userLoginResponse.setPhone(response.getUser().getPhone());
            userLoginResponse.setCreatedAt(response.getUser().getCreatedAt());
            userLoginResponse.setCustomParameters(response.getUser().getCustomData());
            userLoginResponse.setExternalUserId(response.getUser().getExternalUserId());
            userLoginResponse.setFacebookId(response.getUser().getFacebookId());
            userLoginResponse.setFullName(response.getUser().getFullName());
            userLoginResponse.setId(response.getUser().getId());
            userLoginResponse.setUpdatedAt(response.getUser().getUpdatedAt());
            userLoginResponse.setWebsite(response.getUser().getWebsite());
            userLoginResponse.setUserTags(response.getUser().getUserTags());
            userLoginResponse.setLastRequestAt(response.getUser().getLastRequestAt());
            userLoginResponse.setLogin(response.getUser().getLogin());
        }
        return userLoginResponse;
    }

    public static UserSignUpResponce transform(SignUpResponseModel response) {
        UserSignUpResponce userSignUpResponce = null;
        if (response != null) {
            userSignUpResponce.setEmail(response.getUser().getEmail());
            userSignUpResponce.setBlobId(response.getUser().getBlobId());
            userSignUpResponce.setPhone(response.getUser().getPhone());
            userSignUpResponce.setCreatedAt(response.getUser().getCreatedAt());
            userSignUpResponce.setCustomParameters(response.getUser().getCustomData());
            userSignUpResponce.setExternalUserId(response.getUser().getExternalUserId());
            userSignUpResponce.setFacebookId(response.getUser().getFacebookId());
            userSignUpResponce.setFullName(response.getUser().getFullName());
            userSignUpResponce.setId(response.getUser().getId());
            userSignUpResponce.setUpdatedAt(response.getUser().getUpdatedAt());
            userSignUpResponce.setWebsite(response.getUser().getWebsite());
            userSignUpResponce.setUserTags(response.getUser().getUserTags());
            userSignUpResponce.setLastRequestAt(response.getUser().getLastRequestAt());
            userSignUpResponce.setLogin(response.getUser().getLogin());
        }
        return userSignUpResponce;
    }

}
