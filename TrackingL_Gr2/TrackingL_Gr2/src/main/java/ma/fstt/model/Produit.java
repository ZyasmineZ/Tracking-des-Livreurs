package ma.fstt.model;

public class Produit {
    private Long id_produit ;

    private String nomProd ;

    private Long quantite ;

    private Long prix ;

    private String description;


    public Produit() {
    }

    public Produit(Long id_produit, String nomProd, Long quantite, Long prix, String description) {
        this.id_produit = id_produit;
        this.nomProd = nomProd;
        this.quantite = quantite;
        this.prix = prix;
        this.description = description;
    }

    public Long getId_produit() {
        return id_produit;
    }

    public void setId_produit(Long id_produit) {
        this.id_produit = id_produit;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getPrix() {
        return prix;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + id_produit +
                ", nomProd='" + nomProd + '\'' +
                ", quantite=" + quantite +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                '}';
    }
}
