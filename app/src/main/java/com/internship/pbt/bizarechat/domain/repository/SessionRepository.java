package com.internship.pbt.bizarechat.domain.repository;

import com.internship.pbt.bizarechat.domain.model.Session;

import rx.Observable;


public interface SessionRepository {
    Observable<Session> getSession();
}
