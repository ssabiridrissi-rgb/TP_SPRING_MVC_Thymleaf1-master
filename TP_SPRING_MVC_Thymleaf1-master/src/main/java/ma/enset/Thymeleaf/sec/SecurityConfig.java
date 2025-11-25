package ma.enset.Thymeleaf.sec;

import org.springframework.context.annotation.Bean;                     // J’ajoute un bean dans le contexte Spring
import org.springframework.context.annotation.Configuration;         // Je précise que cette classe contient une config Spring
import org.springframework.security.config.Customizer;               // Pour utiliser la config par défaut
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// J’active les annotations comme @PreAuthorize sur les méthodes

import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Je configure la sécurité HTTP
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// J’active Spring Security dans mon application

import org.springframework.security.core.userdetails.User;            // Je crée des utilisateurs en mémoire
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
// Service pour charger un utilisateur (BD, mémoire, etc.)

import org.springframework.security.core.userdetails.UsernameNotFoundException;
// Exception si l’utilisateur n’existe pas

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// J’utilise BCrypt pour encoder les mots de passe

import org.springframework.security.crypto.password.PasswordEncoder;  // Interface pour les encodeurs
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// Je stocke les utilisateurs dans la mémoire

import org.springframework.security.web.SecurityFilterChain;
// Contient la configuration finale de la sécurité


@Configuration // Je dis à Spring que cette classe contient des configurations
@EnableWebSecurity // J’active la sécurité Web
@EnableGlobalMethodSecurity(prePostEnabled = true) // Je peux utiliser @PreAuthorize
public class SecurityConfig {

    @Bean // Je crée un bean pour encoder les mots de passe
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // J’utilise BCrypt pour sécuriser les mots de passe
    }

    @Bean // Je crée un gestionnaire d’utilisateurs en mémoire
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder encoder = passwordEncoder(); // Je récupère l’encodeur

        System.out.println("========="); // Juste pour tester l’encodage
        String encodedPwd = encoder.encode("123456"); // Je vérifie l'encodage
        System.out.println(encodedPwd);
        System.out.println("=========");

        // Je déclare trois utilisateurs avec leurs mots de passe et rôles
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER", "ADMIN").build()
        );
    }

    //@Bean // Exemple si je voulais charger mes utilisateurs autrement
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // Ici normalement je cherche l’utilisateur dans une base de données
                return null; // Je laisse vide pour l’exemple
            }
        };
    }

    @Bean // Je configure ici toute la sécurité de mon application
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .formLogin(fl -> fl.loginPage("/login").permitAll())
                // Ma page personnalisée de login accessible à tous

                .csrf(Customizer.withDefaults())
                // J’active la protection CSRF

                //.authorizeHttpRequests(ar -> ar.requestMatchers("/user/**").hasRole("USER"))
                // Exemple pour sécuriser un espace utilisateur

                //.authorizeHttpRequests(ar -> ar.requestMatchers("/admin/**").hasRole("ADMIN"))
                // Exemple pour sécuriser un espace admin

                .authorizeHttpRequests(ar -> ar.requestMatchers("/public/**", "webjars/**").permitAll())
                // Ces ressources restent accessibles sans connexion

                .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
                // Le reste doit obligatoirement être authentifié

                .exceptionHandling(eh -> eh.accessDeniedPage("/notAuthorized"))
                // La page affichée si quelqu’un n’a pas le droit d’accéder à une page

                .build(); // Je finalise et je retourne la configuration
    }
}
