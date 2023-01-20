module org.checkers {
    requires java.persistence;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;
    requires java.sql;

    exports org.checkers;
    exports org.checkers.games;
    exports org.checkers.boards;
    exports org.checkers.enums;
    exports org.checkers.database;

    opens org.checkers.database.entities to org.hibernate.orm.core;
    opens org.checkers;


    opens org.checkers.games;
}
