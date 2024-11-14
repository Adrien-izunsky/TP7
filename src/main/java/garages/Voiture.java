package garages;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.PrintStream;
import java.util.*;

/***
 * Représente une voiture qui peut être stationnée dans des garages.
 */
@RequiredArgsConstructor
@ToString
public class Voiture {

	@Getter
	@NonNull
	private final String immatriculation;

	// Liste des stationnements de cette voiture
	@ToString.Exclude
	private final List<Stationnement> myStationnements = new LinkedList<>();

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws IllegalStateException Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws IllegalStateException {
		// Vérifie si la voiture est déjà dans un garage
		if (estDansUnGarage()) {
			throw new IllegalStateException("La voiture est déjà dans un garage.");
		}

		// Crée un nouveau stationnement pour la voiture dans le garage spécifié
		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws IllegalStateException si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws IllegalStateException {
		// Vérifie si la voiture est actuellement dans un garage
		if (!estDansUnGarage()) {
			throw new IllegalStateException("La voiture n'est pas dans un garage.");
		}

		// Trouve le dernier stationnement de la voiture et marque sa sortie
		Stationnement dernierStationnement = myStationnements.get(myStationnements.size() - 1);
		dernierStationnement.terminer();
	}

	/**
	 * Calcule l'ensemble des garages visités par cette voiture
	 *
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		// Crée un ensemble des garages visités en parcourant les stationnements
		Set<Garage> garages = new HashSet<>();
		for (Stationnement s : myStationnements) {
			garages.add(s.getGarageVisite());
		}
		return garages;
	}

	/**
	 * Détermine si la voiture est actuellement dans un garage
	 *
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
		// La voiture est dans un garage si elle a des stationnements et si le dernier est en cours
		return !myStationnements.isEmpty() && myStationnements.get(myStationnements.size() - 1).estEnCours();
	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des
	 * dates d'entrée / sortie dans ce garage
	 *
	 * @param out l'endroit où imprimer (ex: System.out pour imprimer dans la
	 *            console)
	 */
	public void imprimeStationnements(PrintStream out) {
		// Crée une map qui regroupe les stationnements par garage
		Map<Garage, List<Stationnement>> garagesMap = new HashMap<>();
		for (Stationnement s : myStationnements) {
			garagesMap.computeIfAbsent(s.getGarageVisite(), k -> new ArrayList<>()).add(s);
		}

		// Pour chaque garage, imprime les stationnements associés
		for (Map.Entry<Garage, List<Stationnement>> entry : garagesMap.entrySet()) {
			out.println(entry.getKey() + ":");
			for (Stationnement s : entry.getValue()) {
				out.println("\t" + s);
			}
		}
	}
}
