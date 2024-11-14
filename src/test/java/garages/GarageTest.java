package garages;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GarageTest {

	private Garage garageCastres, garageAlbi;
	private Voiture voitureISIS;

	@BeforeEach
	public void setUp() throws NullPointerException {
		voitureISIS = new Voiture("123 XX 456");
		garageCastres = new Garage("ISIS Castres");
		garageAlbi = new Garage("Universite Champollion Albi");
	}

	@Test
	void lesVoituresSontBienInitialisees() {
		// Given: une voiture après initialisation
		// Then: la voiture n'est pas dans un garage
		assertFalse(voitureISIS.estDansUnGarage());
		// Then: la voiture n'a visité aucun garage
		assertTrue(voitureISIS.garagesVisites().isEmpty());
	}

	@Test
	void entrerAuGarageChangeGarageVisites() throws Exception {
		// Given: une voiture après initialisation
		// When: On fait entrer la voiture au garage g1
		voitureISIS.entreAuGarage(garageCastres);
		// Then: la voiture doit être dans un garage
		assertTrue(voitureISIS.estDansUnGarage());
		// Then: g1 fait partie des garages visités par la voiture
		assertTrue(voitureISIS.garagesVisites().contains(garageCastres));
	}

	@Test
	void sortirDuGarageChangeGarageVisites() throws Exception {
		// Given: une voiture après initialisation
		// When: On fait entrer puis sortir la voiture du garage g1
		voitureISIS.entreAuGarage(garageCastres);
		voitureISIS.sortDuGarage();
		// Then: la voiture n'est plus dans un garage
		assertFalse(voitureISIS.estDansUnGarage());
		// Then: g1 fait partie des garages visités par la voiture
		assertTrue(voitureISIS.garagesVisites().contains(garageCastres));
	}

	@Test
	void pasDeDoubleSortie() throws Exception {
		// Given: une voiture après initialisation
		// When: On la fait entrer puis sortir la voiture du garage g1
		voitureISIS.entreAuGarage(garageCastres);
		voitureISIS.sortDuGarage(); // Ici il ne doit pas y avoir d'exception
		try {
			// When: on essaie de la faire sortir à nouveau
			voitureISIS.sortDuGarage(); // Que doit-il se passer ?
			// Then: on doit avoir une exception
			// Si on arrive ici, il n'y a pas eu d'exception, échec
			fail();
		} catch (IllegalStateException e) {
			// Si on arrive ici, il y a eu une exception, c'est ce qui est attendu
		}
	}

	@Test
	void pasDeDoubleEntree() throws Exception {
		// Given: une voiture après initialisation
		// When: On fait entrer la voiture au garage g1
		voitureISIS.entreAuGarage(garageCastres);
		try {
			voitureISIS.entreAuGarage(garageAlbi); // Que doit-il se passer ?
			// Si on arrive ici, il n'y a pas eu d'exception, échec
			fail("On ne doit pas pouvoir entrer la voiture dans un autre garage sans en sortir d'abord.");
		} catch (IllegalStateException e) {
			// Si on arrive ici, il y a eu une exception, c'est ce qui est attendu
		}
	}

	/**
	 * Exemple de test qui vérifie un format d'impression correct.<br>
	 * `
	 * La méthode "imprimeStationnements" est conçue pour être testable :<br>
	 * Elle prend un paramètre "PrintStream" qui indique où on doit imprimer
	 * (Injection de dépendance).<br>
	 * Dans le test, on imprime dans une chaîne de caractères au lieu d'imprimer
	 * directement dans la console (System.out)<br>
	 * On peut ensuite vérifier que le contenu de la chaîne générée est conforme au
	 * résultat attendu.
	 * 
	 * @throws Exception
	 */
	@Test
	void testCorrectPrintFormat() throws Exception {
		voitureISIS.entreAuGarage(garageCastres);
		voitureISIS.sortDuGarage();
		voitureISIS.entreAuGarage(garageAlbi);
		voitureISIS.sortDuGarage();
		voitureISIS.entreAuGarage(garageCastres);

		// La méthode imprimeGarages va écrire dans une chaine de caractères
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		// On imprime dans ps
		voitureISIS.imprimeStationnements(ps);

		// On récupère le résultat de l'impression
		String output = os.toString("UTF8");

		// Le garage g1 doit apparaître une fois
		assertEquals(1, countSubstring(output, garageCastres.toString()),
				garageCastres.toString() + " doit apparaître une fois");
		// Le garage g2 doit apparaître une fois
		assertEquals(1, countSubstring(output, garageAlbi.toString()),
				garageAlbi.toString() + " doit apparaître une fois");

		assertEquals(3, countSubstring(output, "Stationnement"),
				"On doit imprimer trois stationnements");

		assertEquals(1, countSubstring(output, "en cours"),
				"Il doit y avoir un seul stationnement en cours");
	}

	/**
	 * Une méthode utilitaire pour le test ci-dessus
	 * Compter le nombre d'occurrences d'une sous-chaîne dans une chaîne
	 *
	 * @param string    String to look for substring in.
	 * @param substring Sub-string to look for.
	 * @return Count of substrings in string.
	 */
	private int countSubstring(final String string, final String substring) {
		int count = 0;
		int idx = 0;

		while ((idx = string.indexOf(substring, idx)) != -1) {
			idx++;
			count++;
		}

		return count;
	}
}
