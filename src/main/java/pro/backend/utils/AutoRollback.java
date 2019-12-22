package pro.backend.utils;

import org.hibernate.Transaction;

public class AutoRollback implements AutoCloseable {
	private Transaction tr;
	private boolean committed;

	public AutoRollback(Transaction conn) {
		this.tr = conn;
	}

	public void commit() {
		tr.commit();
		committed = true;
	}

	@Override
	public void close() {
		if (!committed) {
			tr.rollback();
		}
	}

}