package pro.backend.utils;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;

public class ExtensionUtil<T> {
	protected Session session;

	protected Class<T> clazz;

	public ExtensionUtil(Session session, Class<T> clazz) {
		this.session = session;
		this.clazz = clazz;
	}

	public T get(long id) {
		return session.get(clazz, id);
	}

	public List<T> listAll() {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = criteriaBuilder.createQuery(clazz);
		criteria.from(clazz);
		List<T> list = session.createQuery(criteria).getResultList();
		return list;
	}

	public T getFirst() {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = criteriaBuilder.createQuery(clazz);
		criteria.from(clazz);
		return session.createQuery(criteria).getSingleResult();
	}

	public void save(T obj) {
		session.save(obj);
	}

	public void delete(long id) {
		T obj = session.byId(clazz).load(id);
		session.delete(obj);
	}

	public boolean deleteById(long id) {
		T persistentInstance = session.load(clazz, id);
		if (persistentInstance != null) {
			session.delete(persistentInstance);
			return true;
		}
		return false;
	}

}
