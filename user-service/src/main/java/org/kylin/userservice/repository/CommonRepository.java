package org.kylin.userservice.repository;

import org.kylin.userservice.entity.Filter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommonRepository<T> {
    @PersistenceContext
    private EntityManager em;

    public List<T> getEntitiesByFilter(Class<T> clazz, List<Filter> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        List<Predicate> predicates = createPreByFilter(filters, cb, root);
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<T> tq = em.createQuery(cq);
        return tq.getResultList();
    }

    private List<Predicate> createPreByFilter(List<Filter> filters, CriteriaBuilder cb, Root<T> root) {
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach(x -> {
            switch (x.getOp()) {
                case EQ:
                    predicates.add(cb.equal(root.get(x.getKey()), x.getValue()));
                    break;
                //TODO: other case
                default:
                    throw new IllegalArgumentException("Illegal operation.");
            }
        });
        return predicates;
    }
}
