package garages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Représente un stationnement d'une voiture dans un garage.
 */
@Getter
@RequiredArgsConstructor // génère un constructeur avec un paramètre pour chaque champ @NonNull ou final
public class Stationnement {

	/**
	 * La voiture qui est stationnée.
	 */
	private final Voiture vehiculeRecu;

	/**
	 * Le garage où la voiture est stationnée.
	 */
	private final Garage garageVisite;

	/**
	 * La date d'entrée du véhicule dans le garage.
	 */
	private final Date entree = new Date(); // Aujourd'hui

	/**
	 * La date de sortie du véhicule du garage. Null si le véhicule est toujours
	 * dans le garage.
	 */
	private Date fin;

	/**
	 * Termine le stationnement en enregistrant la date de fin.
	 */
	public void terminer() {
		fin = new Date();
	}

	/**
	 * Vérifie si le stationnement est en cours.
	 *
	 * @return true si le stationnement est en cours, false sinon
	 */
	public boolean estEnCours() {
		return (fin == null);
	}

	/**
	 * Retourne une représentation textuelle du statut du stationnement.
	 *
	 * @return Une chaîne représentant l'état du stationnement, incluant la date d'entrée
	 *         et soit "en cours" soit la date de sortie.
	 */
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
		return String.format("Stationnement{ entree=%s, %s }",
				dateFormat.format(entree),
				estEnCours() ? "en cours" : "sortie=" + dateFormat.format(fin));
	}
}
