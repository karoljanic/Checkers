package org.checkers;

import javax.persistence.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

//            Employee dalia = new Employee();
//            dalia.setId(6);
//            dalia.setFirstName("Dalia");
//            dalia.setLastName("Abo Sheasha");
//            entityManager.persist(dalia);

         //   TypedQuery<GameTypeEntity> empByDeptQuery = entityManager.createNamedQuery("Employee.byDept", Employee.class);
         //   empByDeptQuery.setParameter(1, "Java Advocacy");
         //   for (GameTypeEntity employee : empByDeptQuery.getResultList()) {
         //       System.out.println(employee);
           // }

            Query countEmpByDept = entityManager.createNativeQuery("SELECT COUNT(*) FROM GameType");
            //countEmpByDept.setParameter("deptName", "Java Advocacy");
            System.out.println("There are " + countEmpByDept.getSingleResult() + " Java Advocates.");

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
