package org.mql.banque.application;

import java.util.Date;

import org.mql.banque.entities.Client;
import org.mql.banque.entities.CompteCourant;
import org.mql.banque.entities.Employe;
import org.mql.banque.entities.Groupe;
import org.mql.banque.metier.IBanqueMetier;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application1 {

	public Application1() {
		exp01();
	}
	private void exp01() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
		IBanqueMetier metier = (IBanqueMetier) context.getBean("metier");
		metier.addClient(new Client("C1", "AD1"));
		metier.addClient(new Client("C2", "AD2"));
		metier.addEmploye(new Employe("E1"), null);
		metier.addEmploye(new Employe("E2"), 1L);
		metier.addEmploye(new Employe("E2"), 1L);
		metier.addGroupe(new Groupe("G1"));
		metier.addGroupe(new Groupe("G2"));
		metier.addEmployeToGroupe(1L, 1L);
		metier.addEmployeToGroupe(2L, 2L);
		metier.addEmployeToGroupe(3L, 2L);
		
		metier.addCompte(new CompteCourant("CC1", new Date(), 9000, 8000), 1L, 2L);
		metier.addCompte(new CompteCourant("CE1", new Date(), 40000, 5.5), 2L, 2L);
	
		metier.versement("CC1", 5000, 2L);
		metier.retrait("CC1", 6000, 2L);
		
		metier.virement("CC1", "CE1", 4000, 1L);
	}
	public static void main(String[] args) {
		new Application1();

	}

}
