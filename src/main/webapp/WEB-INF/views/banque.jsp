<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Banque</title>
<link rel="styleSheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/style1.css" />
</head>
<body>
	<div>
		<f:form modelAttribute="banqueForm" method="post"
			action="chargerCompte">
			<table>
				<tr>
					<td>Code :</td>
					<td><f:input path="code" /></td>
					<td><f:errors path="code"></f:errors></td>
				</tr>
				<tr>
					<td><input type="submit" value="OK" /></td>
				</tr>
			</table>
		</f:form>
	</div>
	<c:if test="${banqueForm.exception == null}">
		<c:if test="${banqueForm.compte != null}">
			<div>
				<table>
					<tr>
						<td>Solde :</td>
						<td>${banqueForm.compte.solde}</td>
					</tr>
					<tr>
						<td>Date Création :</td>
						<td>${banqueForm.compte.dateCreation}</td>
					</tr>
					<tr>
						<td>Type de compte :</td>
						<td>${banqueForm.typeCompte}</td>
					</tr>
					<c:if test="${banqueForm.typeCompte == 'CompteCourant'}">
						<tr>
							<td>Découvert :</td>
							<td>${banqueForm.compte.decouvert}</td>
						</tr>
					</c:if>
					<c:if test="${banqueForm.typeCompte == 'CompteEpargne'}">
						<tr>
							<td>Taux :</td>
							<td>${banqueForm.compte.taux}</td>
						</tr>
					</c:if>
				</table>
			</div>


			<div>
				<table>
					<tr>
						<td>Code client :</td>
						<td>${banqueForm.compte.client.codeClient}</td>
					</tr>
					<tr>
						<td>Nom client :</td>
						<td>${banqueForm.compte.client.nomClient}</td>
					</tr>
				</table>
			</div>

			<div>
				<table>
					<tr>
						<td>Code Employé :</td>
						<td>${banqueForm.compte.employe.codeEmploye}</td>
					</tr>
					<tr>
						<td>Nom Employé :</td>
						<td>${banqueForm.compte.employe.nomEmploye}</td>
					</tr>
				</table>
			</div>
			<div>
				<f:form modelAttribute="banqueForm" action="saveOperation">
					<f:hidden path="code" />
					<table>
						<tr>
							<td>Versemnt :<f:radiobutton path="typeOperation"
									value="VER" onclick="this.form.submit()" /></td>
							<td>Retrait :<f:radiobutton path="typeOperation" value="RET"
									onclick="this.form.submit()" /></td>
							<td>Virement :<f:radiobutton path="typeOperation"
									value="VIR" onclick="this.form.submit()" /></td>
						</tr>
						<br>
						<c:if test="${banqueForm.typeOperation != null}">
							<tr>
								<td>Montant :</td>
								<td><f:input path="montant" /></td>
								<td><f:errors path="montant"></f:errors></td>
							</tr>
							<c:if test="${banqueForm.typeOperation =='VIR'}">
								<tr>
									<td>Vers le compte</td>
									<td><f:input path="code2" /></td>
									<td><f:errors path="code2" /></td>
								</tr>
							</c:if>
							<tr>
								<td><input type="submit" name="action" value="Save"></td>
							</tr>
						</c:if>
					</table>
				</f:form>
			</div>
			<div>
				<table class="table1">
					<tr>
						<th>Numéro</th>
						<th>Type</th>
						<th>Date</th>
						<th>Montant</th>
					</tr>
					<c:forEach items="${banqueForm.operations}" var="op">
						<tr>
							<td>${op.numeroOperation}</td>
							<td>${op}</td>
							<td>${op.dateOperation}</td>
							<td>${op.montant}</td>
						</tr>
					</c:forEach>

				</table>
				<div>
					<c:forEach begin="0" end="${banqueForm.nombrePages-1}" var="p">
						<c:choose>
							<c:when test="${p==banqueForm.page}">
							<span class="current"> Page ${p}</span>
							</c:when>
							<c:otherwise>
								<a href="chargerCompte?page=${p}&code=${banqueForm.code}">
								<span class="autrePage">Page ${p}</span></a>
							</c:otherwise>
						</c:choose>

					</c:forEach>
				</div>
			</div>
		</c:if>
	</c:if>
	<c:if test="${banqueForm.exception != null}">
		<div>${banqueForm.exception}</div>
	</c:if>
</body>
</html>