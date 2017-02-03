package com.internship.pbt.bizarechat.domain.repository;

import com.internship.pbt.bizarechat.data.net.requests.UserRequestModel;
import com.internship.pbt.bizarechat.data.net.requests.signup.SignUpRequestM;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponse;
import com.internship.pbt.bizarechat.domain.model.signup.ResponseSignUpModel;

import retrofit2.Response;
import rx.Observable;


public interface SessionRepository {
    Observable<Session> getSession();

    Observable<Session> getSessionWithAuth(UserRequestModel requestModel);

    Observable<UserLoginResponse> loginUser(UserRequestModel requestModel);

    Observable<ResponseSignUpModel> signUpUser(SignUpRequestM requestModel);

}
