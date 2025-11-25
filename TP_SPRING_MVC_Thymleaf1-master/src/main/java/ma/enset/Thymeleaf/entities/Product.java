package ma.enset.Thymeleaf.entities; // Le package où se trouve l'entité Product

import jakarta.persistence.Entity; // Indique que cette classe est une entité JPA (table dans la BD)
import jakarta.persistence.GeneratedValue; // Pour générer automatiquement la valeur de l'id
import jakarta.persistence.GenerationType; // Définit la stratégie de génération de l'id
import jakarta.persistence.Id; // Marque ce champ comme clé primaire
import jakarta.validation.constraints.Min; // Validation : valeur minimum acceptée
import jakarta.validation.constraints.NotEmpty; // Validation : champ non vide
import jakarta.validation.constraints.Size; // Validation : taille minimum et maximum du texte
import lombok.*; // Lombok génère automatiquement getters, setters, constructeurs, toString, etc.

@Entity // Déclare que cette classe correspond à une table dans la base de données
@NoArgsConstructor // Génère un constructeur sans paramètres
@AllArgsConstructor // Génère un constructeur avec tous les champs
@Getter // Lombok génère tous les getters
@Setter // Lombok génère tous les setters
@ToString // Lombok génère la méthode toString()
@Builder //  créer des objets Product avec un pattern builder
public class Product {

    @Id // Indique que ce champ est la clé primaire
    @GeneratedValue(strategy= GenerationType.IDENTITY) // L'id est généré automatiquement par la BD (auto-incrément)
    private Long id ;

    @NotEmpty
    @Size(min=3, max=50)
    private String name;

    @Min(0)
    private double price;

    @Min(1)
    private double quantity;

}
