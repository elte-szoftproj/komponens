/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.web2;

import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author evan
 */
@Stateless
@LocalBean
public class TransactionalEntityManager implements EntityManager {
    
    @PersistenceContext(unitName = "hu.elte.komp_kompgame-pu")
    private EntityManager em;    

    @Override
    public void persist(Object o) {
        em.persist(o);
    }

    @Override
    public <T> T merge(T t) {
        return em.merge(t);
    }

    @Override
    public void remove(Object o) {
        em.remove(em.merge(o));
    }

    @Override
    public <T> T find(Class<T> type, Object o) {
        return em.find(type, o);
    }

    @Override
    public <T> T find(Class<T> type, Object o, Map<String, Object> map) {
        return em.find(type, o, map);
    }

    @Override
    public <T> T find(Class<T> type, Object o, LockModeType lmt) {
        return em.find(type, o, lmt);
    }

    @Override
    public <T> T find(Class<T> type, Object o, LockModeType lmt, Map<String, Object> map) {
        return em.find(type, o, lmt, map);
    }

    @Override
    public <T> T getReference(Class<T> type, Object o) {
        return em.getReference(type, o);
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public void setFlushMode(FlushModeType fmt) {
        em.setFlushMode(fmt);
    }

    @Override
    public FlushModeType getFlushMode() {
        return em.getFlushMode();
    }

    @Override
    public void lock(Object o, LockModeType lmt) {
        em.lock(o, lmt);
    }

    @Override
    public void lock(Object o, LockModeType lmt, Map<String, Object> map) {
        em.lock(o, lmt, map);
    }

    @Override
    public void refresh(Object o) {
        em.refresh(o);
    }

    @Override
    public void refresh(Object o, Map<String, Object> map) {
        em.refresh(o, map);
    }

    @Override
    public void refresh(Object o, LockModeType lmt) {
        em.refresh(o, lmt);
    }

    @Override
    public void refresh(Object o, LockModeType lmt, Map<String, Object> map) {
        em.refresh(o, lmt, map);
    }

    @Override
    public void clear() {
        em.clear();
    }

    @Override
    public void detach(Object o) {
        em.detach(o);
    }

    @Override
    public boolean contains(Object o) {
        return em.contains(o);
    }

    @Override
    public LockModeType getLockMode(Object o) {
        return em.getLockMode(o);
    }

    @Override
    public void setProperty(String string, Object o) {
        em.setProperty(string, o);
    }

    @Override
    public Map<String, Object> getProperties() {
        return em.getProperties();
    }

    @Override
    public Query createQuery(String string) {
        return em.createQuery(string);
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> cq) {
        return em.createQuery(cq);
    }

    @Override
    public Query createQuery(CriteriaUpdate cu) {
        return em.createQuery(cu);
    }

    @Override
    public Query createQuery(CriteriaDelete cd) {
        return em.createQuery(cd);
    }

    @Override
    public <T> TypedQuery<T> createQuery(String string, Class<T> type) {
        return em.createQuery(string, type);
    }

    @Override
    public Query createNamedQuery(String string) {
        return em.createNamedQuery(string);
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String string, Class<T> type) {
        return em.createNamedQuery(string, type);
    }

    @Override
    public Query createNativeQuery(String string) {
        return em.createNativeQuery(string);
    }

    @Override
    public Query createNativeQuery(String string, Class type) {
        return em.createNativeQuery(string, type);
    }

    @Override
    public Query createNativeQuery(String string, String string1) {
        return em.createNativeQuery(string, string1);
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String string) {
        return em.createNamedStoredProcedureQuery(string);
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String string) {
        return em.createStoredProcedureQuery(string);
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String string, Class... types) {
        return em.createStoredProcedureQuery(string, types);
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String string, String... strings) {
        return em.createStoredProcedureQuery(string, strings);
    }

    @Override
    public void joinTransaction() {
        em.joinTransaction();
    }

    @Override
    public boolean isJoinedToTransaction() {
        return em.isJoinedToTransaction();
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return em.unwrap(type);
    }

    @Override
    public Object getDelegate() {
        return em.getDelegate();
    }

    @Override
    public void close() {
        em.close();
    }

    @Override
    public boolean isOpen() {
        return em.isOpen();
    }

    @Override
    public EntityTransaction getTransaction() {
        return em.getTransaction();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return em.getEntityManagerFactory();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return em.getMetamodel();
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> type) {
        return em.createEntityGraph(type);
    }

    @Override
    public EntityGraph<?> createEntityGraph(String string) {
        return em.createEntityGraph(string);
    }

    @Override
    public EntityGraph<?> getEntityGraph(String string) {
        return em.getEntityGraph(string);
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> type) {
        return em.getEntityGraphs(type);
    }
}
