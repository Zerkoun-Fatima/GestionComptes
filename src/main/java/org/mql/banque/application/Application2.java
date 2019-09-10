package org.mql.banque.application;

import java.util.List;

import org.mql.banque.entities.Compte;
import org.mql.banque.entities.Operation;
import org.mql.banque.metier.IBanqueMetier;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application2 {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
		IBanqueMetier metier = (IBanqueMetier) context.getBean("metier");
		
		Compte compte= metier.consulterCompte("CC1");
		System.out.println("Solde :"+ compte.getSolde());
		System.out.println("Date :"+ compte.getDateCreation());
		System.out.println("Client :"+ compte.getClient().getNomClient());
		System.out.println("Employe :"+ compte.getEmploye().getNomEmploye());
		
		List<Operation> operations = metier.consulterOperations("CC1");
		for (Operation operation : operations) {
			System.out.println("***************************");
			System.out.println(operation.getNumeroOperation());
			System.out.println(operation.getDateOperation());
			System.out.println(operation.getMontant());
			System.out.println(operation.getClass().getSimpleName());
		}
	}
}
