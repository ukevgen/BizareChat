package com.internship.pbt.bizarechat.domain.repository;

import com.internship.pbt.bizarechat.data.net.requests.SignUpRequestModel;
import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.model.UserSignUpResponce;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponse;

import rx.Observable;


public interface SessionRepository {
    Observable<Session> getSession();

    Observable<Session> getSessionWithAuth(UserRequestModel requestModel);

    Observable<UserLoginResponse> loginUser(UserRequestModel requestModel);

    Observable<UserSignUpResponce> signUpUser(SignUpRequestModel requestModel);


}
