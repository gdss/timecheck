package com.gdss.timecheck.repositories.specs;

import com.gdss.timecheck.models.Clockin;
import com.gdss.timecheck.models.Clockin_;
import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.models.Employee_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClockinSpec {

    public static Specification<Clockin> days(Employee employee, LocalDate startDate, LocalDate endDate) {
        return (Specification<Clockin>) (root, query, cb) -> {
            Predicate pisPredicate = cb.equal(root.get(Clockin_.employee), employee);
            Predicate datePredicate = cb.between(root.get(Clockin_.date), startDate, endDate);
            query.orderBy(cb.asc(root.get(Clockin_.date)), cb.asc(root.get(Clockin_.time)));
            return cb.and(pisPredicate, datePredicate);
        };
    }


    /**
     * Only one register in the same minute is allowed in the system.
     *
     * @param pis
     * @param localDate
     * @param localTime
     * @return Specification
     */
    public static Specification<Clockin> checkExists(String pis, LocalDate localDate, LocalTime localTime) {
        return (Specification<Clockin>) (root, query, cb) -> {
            Join<Clockin, Employee> rootEmployee = root.join(Clockin_.employee, JoinType.LEFT);
            Predicate pisPredicate = cb.equal(rootEmployee.get(Employee_.pis), pis);
            Predicate datePredicate = cb.equal(root.get(Clockin_.date), localDate);
            Predicate timePredicate = cb.equal(root.get(Clockin_.time), localTime);
            return cb.and(pisPredicate, datePredicate, timePredicate);
        };
    }
}
