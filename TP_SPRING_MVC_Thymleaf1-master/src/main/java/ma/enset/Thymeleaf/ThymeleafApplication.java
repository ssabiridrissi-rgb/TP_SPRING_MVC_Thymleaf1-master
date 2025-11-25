package ma.enset.Thymeleaf; // Je mets ma classe principale dans ce package

import ma.enset.Thymeleaf.entities.Product; // J'utilise mon entité Product
import ma.enset.Thymeleaf.repository.ProductRepository; // J'accède à la base via le repository
import org.springframework.boot.CommandLineRunner; // Pour exécuter du code au démarrage
import org.springframework.boot.SpringApplication; // Pour lancer l'application Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Indique que c'est l'application principale
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication() // Je dis à Spring Boot que c’est l’application principale
//@SpringBootApplication(exclude={SecurityAutoConfiguration.class}) // J’aurais pu désactiver la sécurité, mais je la laisse
public class ThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThymeleafApplication.class, args); // Je lance mon application Spring Boot
    }

    @Bean // Je crée un bean exécuté au démarrage de l'application
    public CommandLineRunner start(ProductRepository productRepository) {
        return args -> {

            // J'enregistre un premier produit dans la base au démarrage
            productRepository.save(Product.builder()
                    .name("Computer")
                    .price(5400)
                    .quantity(12)
                    .build());

            // J'enregistre un deuxième produit
            productRepository.save(Product.builder()
                    .name("Printer")
                    .price(1200)
                    .quantity(11)
                    .build());

            // J'enregistre un troisième produit
            productRepository.save(Product.builder()
                    .name("Smart phone")
                    .price(12000)
                    .quantity(33)
                    .build());

            // J'affiche tous les produits dans la console pour vérifier
            productRepository.findAll().forEach(p ->
                    System.out.println(p.toString()));
        };
    }
}
