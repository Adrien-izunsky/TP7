package garages;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Représente un garage où les voitures peuvent être stationnées.
 */
@Getter // Génère les getters pour tous les champs
@ToString // Génère la méthode toString()
@RequiredArgsConstructor // Génère un constructeur avec tous les champs "final" ou annotés avec @NonNull
public class Garage {

	/**
	 * Le nom du garage.
	 * Ce champ ne peut pas être null.
	 */
	@NonNull
	@Setter // Génère le setter pour ce champ
	private String name;

	/**
	 * Crée un nouveau garage avec le nom spécifié.
	 * 
	 * @param name le nom du garage (ne peut pas être null)
	 * @throws NullPointerException si le nom est null
	 */
	// Le constructeur est généré par @RequiredArgsConstructor

	/**
	 * Retourne le nom du garage.
	 * 
	 * @return le nom du garage
	 */
	// La méthode getName() est générée par @Getter

	/**
	 * Modifie le nom du garage.
	 * 
	 * @param name le nouveau nom du garage (ne peut pas être null)
	 * @throws NullPointerException si le nouveau nom est null
	 */
	// La méthode setName() est générée par @Setter

	/**
	 * Retourne une représentation sous forme de chaîne de caractères du garage.
	 * 
	 * @return une chaîne de caractères représentant le garage, incluant son nom
	 */
	// La méthode toString() est générée par @ToString
}