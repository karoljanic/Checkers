module org.checkers {
    requires java.persistence;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;
    requires java.sql;

    exports org.checkers;

    opens org.checkers.database.models to org.hibernate.orm.core;

    opens org.checkers;
}
