package org.checkers;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseMain {
    private static SessionFactory sessionFactory;
    public static void main(String[] args) {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.get()
        }
        catch ()
    }
}
