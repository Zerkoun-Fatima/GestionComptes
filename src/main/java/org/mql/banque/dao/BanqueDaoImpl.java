package org.mql.banque.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.mql.banque.entities.Client;
import org.mql.banque.entities.Compte;
import org.mql.banque.entities.Employe;
import org.mql.banque.entities.Groupe;
import org.mql.banque.entities.Operation;

public class BanqueDaoImpl implements IBanqueDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Client addClient(Client c) {
		em.persist(c);
		return c;
	}

	@Override
	public Employe addEmploye(Employe e, Long codeSup) {
		if (codeSup != null) {
			Employe sup = em.find(Employe.class, codeSup);
			e.setEmployeSup(sup);
		}
		em.persist(e);
		return e;
	}

	@Override
	public Groupe addGroupe(Groupe g) {
		em.persist(g);
		return g;
	}

	@Override
	public void addEmployeToGroupe(Long codeEmp, Long codeGr) {
		Employe e = em.find(Employe.class, codeEmp);
		Groupe g = em.find(Groupe.class, codeGr);
		e.getGroupes().add(g);
		g.getEmployes().add(e);

	}

	@Override
	public Compte addCompte(Compte cp, Long codeCli, Long codeEmp) {
		Client clt = em.find(Client.class, codeCli);
		Employe emp = em.find(Employe.class, codeEmp);
		cp.setClient(clt);
		cp.setEmploye(emp);
		em.persist(cp);
		return cp;
	}

	@Override
	public Operation addOperation(Operation o, String codeCpte, Long codeEmp) {
		Compte cp = consulterCompte(codeCpte);
		Employe emp = em.find(Employe.class, codeEmp);
		o.setCompte(cp);
		o.setEmploye(emp);
		em.persist(o);
		return o;
	}

	@Override
	public Compte consulterCompte(String codeCpte) {
		Compte cp = em.find(Compte.class, codeCpte);
		if (cp == null)
			throw new RuntimeException("Compte introuvable !");
		return cp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> consulterOperations(String codeCpte) {
		Query query = em.createQuery("select o from Operation o where o.compte.codeCompte=:x");
		query.setParameter("x", codeCpte);
		return query.getResultList();
	}

	@Override
	public Client consulterClient(Long codeCli) {
		Client clt = em.find(Client.class, codeCli);
		if (clt == null)
			throw new RuntimeException("Client introuvable !");
		return clt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> consulterClients(String mc) {
		Query query = em.createQuery("select c from Client c where c.nomClient=:x");
		query.setParameter("x", "%" + mc + "%");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Compte> getComptesByClient(Long codeCli) {
		Query query = em.createQuery("select c from Compte c where c.client.codeClient=:x");
		query.setParameter("x", codeCli);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Compte> getComptesByEmploye(Long codeEmp) {
		Query query = em.createQuery("select c from Compte c where c.employe.codeEmploye=:x");
		query.setParameter("x", codeEmp);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employe> getEmployes() {
		Query query = em.createQuery("select e from Employe e");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Groupe> getGroupes() {
		Query query = em.createQuery("select g from Groupe g");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employe> getEmployesByGroupe(Long codeGr) {
		Query query = em.createQuery("select e from Employe e where e.groupes.codeGroupe=:x");
		query.setParameter("x", codeGr);
		return query.getResultList();
	}

}
