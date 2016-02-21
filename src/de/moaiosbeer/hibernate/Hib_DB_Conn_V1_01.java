package de.moaiosbeer.hibernate;



import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Hib_DB_Conn_V1_01 {
	Session session = null;
	Transaction  transaction = null; 

	public Session getSession() {
		return session;
	}



	public void setSession(Session session) {
		this.session = session;
	}



	public Transaction getTransaction() {
		return transaction;
	}



	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}



	/***
	 * Erstellt mit hilfe der Klasse de.das_portal.helper.hibernate.HibernateUtil.java eine Session und giebt diese zurück
	 * @return Session
	 */
	public void Transaction_Start(){
		
		session = de.moaiosbeer.hibernate.HibernateUtil.getSessionFactory().openSession();
		System.out.println(new Date()+" || Hibernate: New Session opened");
		transaction = session.beginTransaction();
		System.out.println(new Date()+" || Hibernate: Transaction started");
		//tx.setTimeout(5); if wished Transaction timeout
		
		// dont forget tx.commit(); to close Transaction
	}
	


	/***
	 * 
	 * @param Session
	 */
	public void session_Close(){
		//TODO hibernate Session handling von http Session handling trennen
		// call in finally block of Transaction
		session.close();
		System.out.println(new Date()+" || Hibernate: Session closed");
		
	}
	public void transaktion_commit(){
		
		transaction.commit();
		System.out.println(new Date()+" || Hibernate: Transaction commited");
		
	}
	
}
