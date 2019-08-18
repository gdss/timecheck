package com.gdss.timecheck.repositories.specs;

import com.gdss.timecheck.models.Clockin;
import com.gdss.timecheck.models.Clockin_;
import com.gdss.timecheck.models.Employee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Date;

public class ClockinSpec {

    public static Specification<Clockin> days(Employee employee, Date startDate, Date endDate) {
        return (Specification<Clockin>) (root, query, cb) -> {
            Predicate pisPredicate = cb.equal(root.get(Clockin_.employee), employee);
            Predicate datePredicate = cb.between(root.get(Clockin_.date), startDate, endDate);
            query.orderBy(cb.asc(root.get(Clockin_.date)), cb.asc(root.get(Clockin_.time)));
            return cb.and(pisPredicate, datePredicate);
        };
    }

}
