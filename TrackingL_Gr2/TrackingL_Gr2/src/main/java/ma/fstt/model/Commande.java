package ma.fstt.model;

public class Commande {
    private Long id_commande ;

    private Long id_produit ;

    private Long id_livreur ;

    private String date ;

    private String adresse ;

    public Commande() {
    }

    public Commande(Long id_commande, Long id_produit, Long id_livreur, String date, String adresse) {
        this.id_commande = id_commande;
        this.id_produit = id_produit;
        this.id_livreur = id_livreur;
        this.date = date;
        this.adresse = adresse;
    }

    public Long getId_commande() {
        return id_commande;
    }

    public void setId_commande(Long id_commande) {
        this.id_commande = id_commande;
    }

    public Long getId_produit() {
        return id_produit;
    }

    public void setId_produit(Long id_produit) {
        this.id_produit = id_produit;
    }

    public Long getId_livreur() {
        return id_livreur;
    }

    public void setId_livreur(Long id_livreur) {
        this.id_livreur = id_livreur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id_commande=" + id_commande +
                ", id_produit=" + id_produit +
                ", id_livreur=" + id_livreur +
                ", date='" + date + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
