package ma.enset.Thymeleaf.repository; // Dossier où se trouve le repository

import ma.enset.Thymeleaf.entities.Product; // J'utilise l'entité Product
import org.springframework.data.jpa.repository.JpaRepository; // Fournit toutes les opérations CRUD + JPA avancées
import org.springframework.data.repository.CrudRepository; // Import inutile ici (non utilisé)

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Interface qui gère l'accès à la table Product dans la base de données
    // JpaRepository donne automatiquement : findAll(), save(), deleteById(), findById(), etc.
    // Product = type de l'entité, Long = type de l'id
}
