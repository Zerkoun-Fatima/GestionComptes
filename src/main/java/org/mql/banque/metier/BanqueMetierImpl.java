package org.mql.banque.metier;

import java.util.Date;
import java.util.List;

import org.mql.banque.dao.IBanqueDao;
import org.mql.banque.entities.Client;
import org.mql.banque.entities.Compte;
import org.mql.banque.entities.Employe;
import org.mql.banque.entities.Groupe;
import org.mql.banque.entities.Operation;
import org.mql.banque.entities.Retrait;
import org.mql.banque.entities.Versement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BanqueMetierImpl implements IBanqueMetier {

	private IBanqueDao dao;

	@Override
	public Client addClient(Client c) {
		return dao.addClient(c);
	}

	@Override
	public Employe addEmploye(Employe e, Long codeSup) {
		return dao.addEmploye(e, codeSup);
	}

	@Override
	public Groupe addGroupe(Groupe g) {
		return dao.addGroupe(g);
	}

	@Override
	public void addEmployeToGroupe(Long codeEmp, Long codeGr) {
		dao.addEmployeToGroupe(codeEmp, codeGr);
	}

	@Override
	public Compte addCompte(Compte cp, Long codeCli, Long codeEmp) {
		return dao.addCompte(cp, codeCli, codeEmp);
	}

	@Override
	public void versement(String codeCpte, double mt, Long codeEmp) {
		dao.addOperation(new Versement(new Date(), mt), codeCpte, codeEmp);
		Compte cp = dao.consulterCompte(codeCpte);
		cp.setSolde(cp.getSolde() + mt);
	}

	@Override
	public void retrait(String codeCpte, double mt, Long codeEmp) {
		dao.addOperation(new Retrait(new Date(), mt), codeCpte, codeEmp);
		Compte cp = dao.consulterCompte(codeCpte);
		cp.setSolde(cp.getSolde() - mt);
	}

	@Override
	public void virement(String codeCpte1, String codeCpte2, double mt, Long codeEmp) {
		retrait(codeCpte1, mt, codeEmp);
		versement(codeCpte2, mt, codeEmp);
	}

	@Override
	public Compte consulterCompte(String codeCpte) {
		return dao.consulterCompte(codeCpte);
	}

	@Override
	public List<Operation> consulterOperations(String codeCpte, int position, int nbOperation) {
		return dao.consulterOperations(codeCpte, position, nbOperation);
	}

	@Override
	public Client consulterClient(Long codeCli) {
		return dao.consulterClient(codeCli);
	}

	@Override
	public List<Client> consulterClients(String mc) {
		return dao.consulterClients(mc);
	}

	@Override
	public List<Compte> getComptesByClient(Long codeCli) {
		return dao.getComptesByClient(codeCli);
	}

	@Override
	public List<Compte> getComptesByEmploye(Long codeEmp) {
		return dao.getComptesByEmploye(codeEmp);
	}

	@Override
	public List<Employe> getEmployes() {
		return dao.getEmployes();
	}

	@Override
	public List<Groupe> getGroupes() {
		return dao.getGroupes();
	}

	@Override
	public List<Employe> getEmployesByGroupe(Long codeGr) {
		return dao.getEmployesByGroupe(codeGr);
	}

	public void setDao(IBanqueDao dao) {
		this.dao = dao;
	}

	@Override
	public long getNombreOperation(String numCompte) {
		return dao.getNombreOperation(numCompte);
	}
}
