package com.internship.pbt.bizarechat.domain.repository;

import com.internship.pbt.bizarechat.data.net.requests.SignUpRequestModel;
import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponce;
import com.internship.pbt.bizarechat.domain.model.UserSignUpResponce;

import rx.Observable;


public interface SessionRepository {
    Observable<Session> getSession();

    Observable<Session> getSessionWithAuth(UserRequestModel requestModel);

    Observable<UserLoginResponce> loginUser(UserRequestModel requestModel);

    Observable<UserSignUpResponce> signUpUser(SignUpRequestModel requestModel);
}
