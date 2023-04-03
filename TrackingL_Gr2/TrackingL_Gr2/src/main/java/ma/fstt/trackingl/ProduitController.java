package ma.fstt.trackingl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ma.fstt.model.Produit;
import ma.fstt.model.ProduitDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static java.lang.Long.parseLong;

public class ProduitController implements Initializable {

    @FXML
    private Button menu;

    @FXML
    private TextField nomProd ;


    @FXML
    private TextField quantite ;

    @FXML
    private TextField prix ;

    @FXML
    private TextField description ;

    @FXML
    private TableView<Produit> tabprod ;


    @FXML
    private TableColumn<Produit, Long> col_id ;

    @FXML
    private TableColumn <Produit, String> col_nom ;

    @FXML
    private TableColumn <Produit, Long> col_quantite ;

    @FXML
    private TableColumn <Produit, Long> col_prix ;

    @FXML
    private TableColumn <Produit, String> col_description ;

    @FXML
    private Button ok;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    @FXML
    private Button annuler;

    @FXML
    void afficherMenu(ActionEvent event) {
        try {
            // Load the FXML file for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root = loader.load();

            // Create a new scene with the root node
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void onSaveButtonClick() {

        // accees a la bdd

        try {
            ProduitDAO produitDAO = new ProduitDAO();

            Produit prod = new Produit(0l , nomProd.getText() , parseLong(quantite.getText()) , parseLong(prix.getText()), description.getText() );

            produitDAO.save(prod);


            UpdateTable();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Produit,Long>("id_produit"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Produit,String>("nomProd"));
        col_quantite.setCellValueFactory(new PropertyValueFactory<Produit,Long>("quantite"));
        col_prix.setCellValueFactory(new PropertyValueFactory<Produit,Long>("prix"));
        col_description.setCellValueFactory(new PropertyValueFactory<Produit,String>("description"));


        tabprod.setItems(this.getDataProduits());
    }

    public static ObservableList<Produit> getDataProduits(){

        ProduitDAO produitDAO = null;

        ObservableList<Produit> listfx = FXCollections.observableArrayList();

        try {
            produitDAO = new ProduitDAO();
            for (Produit ettemp : produitDAO.getAll())
                listfx.add(ettemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
        modifier.setDisable(true);
        supprimer.setDisable(true);
    }


    @FXML
    protected void onUpdateButtonClick() {
        Produit selectedProduit = tabprod.getSelectionModel().getSelectedItem();

        if (selectedProduit == null) {
            // No item selected, display error message
            System.out.println("Error: no item selected for update");
        } else {
            try {
                // Get the updated values
                String updatedNomProd = nomProd.getText();
                long updatedQuantite = parseLong(quantite.getText());
                long updatedPrix = parseLong(prix.getText());
                String updatedDescription = description.getText();

                // Create a new Produit object with the updated values and the same ID as the selected Produit
                Produit updatedProduit = new Produit(selectedProduit.getId_produit(), updatedNomProd, updatedQuantite, updatedPrix, updatedDescription);

                // Update the Produit in the database
                ProduitDAO produitDAO = new ProduitDAO();
                produitDAO.update(updatedProduit);

                // Update the table
                UpdateTable();

                // Clear the text fields and disable the modifier and supprimer buttons
                nomProd.setText("");
                quantite.setText("");
                prix.setText("");
                description.setText("");
                ok.setDisable(false);
                modifier.setDisable(true);
                supprimer.setDisable(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void onDeleteButtonClick() {

        Produit selectedProduit = tabprod.getSelectionModel().getSelectedItem();

        if (selectedProduit == null) {
            // No item selected, display error message
            System.out.println("Error: no item selected for deletion");

        }

        try {
            ProduitDAO produitDAO = new ProduitDAO();
            produitDAO.delete(selectedProduit);
            UpdateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void tableClick(javafx.scene.input.MouseEvent mouseEvent) {
        Produit produit = tabprod.getSelectionModel().getSelectedItem();
        nomProd.setText(produit.getNomProd());
        quantite.setText(String.valueOf(produit.getQuantite()));
        prix.setText(String.valueOf(produit.getPrix()));
        description.setText(produit.getDescription());
        ok.setDisable(true);
        modifier.setDisable(false);
        supprimer.setDisable(false);

    }

    @FXML
    protected  void reset(ActionEvent event){
        ok.setDisable(false);
        modifier.setDisable(true);
        supprimer.setDisable(true);
        nomProd.setText("");
        quantite.setText("");
        prix.setText("");
        description.setText("");
    }
}
