package org.mql.banque.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CE")
public class CompteEpargne extends Compte{

	private double taux;

	public CompteEpargne() {
		super();
	}

	public CompteEpargne(String codeCompte, Date dateCreation, double solde, double taux) {
		super(codeCompte, dateCreation, solde);
		this.taux = taux;
	}

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}


	
}
