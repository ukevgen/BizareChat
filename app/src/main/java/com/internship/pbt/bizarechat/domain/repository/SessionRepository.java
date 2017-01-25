package com.internship.pbt.bizarechat.domain.repository;

import com.internship.pbt.bizarechat.data.net.requests.User;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.model.UserLoginResponce;

import rx.Observable;


public interface SessionRepository {
    Observable<Session> getSession();

    Observable<Session> getSessionWithAuth(User requestModel);

    Observable<UserLoginResponce> loginUser(User requestModel);
}
