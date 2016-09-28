package com.jam01.littlelight.domain;

import rx.Observable;
import rx.subjects.PublishSubject;

public class DomainEventPublisher {

    private static DomainEventPublisher INSTANCE;

    private PublishSubject<DomainEvent> subject = PublishSubject.create();

    public static synchronized DomainEventPublisher instanceOf() {
        if (INSTANCE == null) {
            INSTANCE = new DomainEventPublisher();
        }
        return INSTANCE;
    }

    public void publish(DomainEvent object) {
        subject.onNext(object);
    }

    public Observable<DomainEvent> getEvents() {
        return subject;
    }
}
