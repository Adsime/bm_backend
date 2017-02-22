package com.acc.database;

import org.hibernate.Session;

/**
 * Created by nguyen.duy.j.khac on 22.02.2017.
 */

public class CloseableSession implements AutoCloseable {

    private final Session session;

    public CloseableSession(Session session) {
        this.session = session;
    }

    public Session delegate() {
        return session;
    }

    @Override
    public void close() {
        session.close();
    }
}