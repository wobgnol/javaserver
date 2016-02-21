package de.moaiosbeer.hibernate_examples.manytomany;

import de.moaiosbeer.hibernate.Hib_DB_Conn_V1_01;


public class ManytoManyTest {

	public static void main(String[] args) {

		StudentCertification studentCertification1 = new StudentCertification();
		studentCertification1.setCertification_name("Core JAVA Certification Exam");

		StudentCertification studentCertification2 = new StudentCertification();
		studentCertification2.setCertification_name("Oracle DB Certification Exam");

		Student student1 = new Student();
		student1.setStudent_name("Gontu1");
		(student1.getStudentCertification()).add(studentCertification1);

		Student student2 = new Student();
		student2.setStudent_name("Gontu1");
		(student2.getStudentCertification()).add(studentCertification2);

		Hib_DB_Conn_V1_01 Con = new Hib_DB_Conn_V1_01();
		Con.Transaction_Start();
		// Timeout der Transaktion setzen
		Con.getTransaction().setTimeout(5);

		// please note I am not saving studentCertification object but still it will
		// be saved in a database that's the magic of Many to Many mapping

		Con.getSession().save(student1);
		Con.getSession().save(student2);
		
		Con.getSession().getTransaction().commit();
		Con.getSession().close();
	

	}

}
