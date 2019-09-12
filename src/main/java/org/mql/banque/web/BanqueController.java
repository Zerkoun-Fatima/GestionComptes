package org.mql.banque.web;

import java.util.List;

import javax.validation.Valid;

import org.mql.banque.entities.Compte;
import org.mql.banque.entities.Operation;
import org.mql.banque.metier.IBanqueMetier;
import org.mql.banque.models.BanqueForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BanqueController {

	@Autowired
	private IBanqueMetier metier;

	@RequestMapping(value = "/index")
	public String index(Model model) {
		model.addAttribute("banqueForm", new BanqueForm());
		return "banque";
	}

	@RequestMapping(value = "/chargerCompte")
	public String chargerCompte(@Valid BanqueForm banqueForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "banque";
		}
		chargerCompte(banqueForm);
		model.addAttribute("banqueForm", banqueForm);
		return "banque";
	}

	@RequestMapping(value = "/saveOperation")
	public String saveOperation(@Valid BanqueForm banqueForm, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return "banque";
			}
			if (banqueForm.getAction() != null) {
				if (banqueForm.getTypeOperation().equals("VER")) {
					metier.versement(banqueForm.getCode(), banqueForm.getMontant(), 1L);
				} else if (banqueForm.getTypeOperation().equals("RET")) {
					metier.retrait(banqueForm.getCode(), banqueForm.getMontant(), 1L);
				} else if (banqueForm.getTypeOperation().equals("VIR")) {
					metier.virement(banqueForm.getCode(), banqueForm.getCode2(), banqueForm.getMontant(), 1L);
				}
			}
		} catch (Exception e) {
			banqueForm.setException(e.getMessage());
		}
		chargerCompte(banqueForm);

		return "banque";
	}

	public void chargerCompte(@Valid BanqueForm banqueForm) {
		try {
			Compte compte = metier.consulterCompte(banqueForm.getCode());
			banqueForm.setTypeCompte(compte.getClass().getSimpleName());
			banqueForm.setCompte(compte);
		
			int position = banqueForm.getNbLignes() * banqueForm.getPage();
			List<Operation> operations = metier.consulterOperations(banqueForm.getCode(), position, banqueForm.getNbLignes());
			banqueForm.setOperations(operations);
			
			long nbOperations = metier.getNombreOperation(banqueForm.getCode());
			banqueForm.setNombrePages((int) (nbOperations / banqueForm.getNbLignes()) + 1);
		
		} catch (Exception e) {
			banqueForm.setException(e.getMessage());
		}
	}
}
