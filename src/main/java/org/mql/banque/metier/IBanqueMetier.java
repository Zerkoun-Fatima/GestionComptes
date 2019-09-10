package org.mql.banque.metier;

import java.util.List;

import org.mql.banque.entities.Client;
import org.mql.banque.entities.Compte;
import org.mql.banque.entities.Employe;
import org.mql.banque.entities.Groupe;
import org.mql.banque.entities.Operation;

public interface IBanqueMetier {

	public Client addClient(Client c);
	public Employe addEmploye(Employe e, Long codeSup);
	public Groupe addGroupe(Groupe g);
	public void addEmployeToGroupe(Long codeEmp, Long codeGr);
	public Compte addCompte(Compte cp, Long codeCli, Long codeEmp);
	
	public void versement(String codeCpte, double mt, Long codeEmp);
	public void retrait(String codeCpte, double mt, Long codeEmp);
	public void virement(String codeCpte1, String codeCpte2, double mt, Long codeEmp);
	
	public Compte consulterCompte(String codeCpte);
	public List<Operation> consulterOperations(String codeCpte);
	public Client consulterClient(Long codeCli);
	public List<Client> consulterClients(String mc);
	public List<Compte> getComptesByClient(Long codeCli);
	public List<Compte> getComptesByEmploye(Long codeEmp);
	public List<Employe> getEmployes();
	public List<Groupe> getGroupes();
	public List<Employe> getEmployesByGroupe(Long codeGr);
}
