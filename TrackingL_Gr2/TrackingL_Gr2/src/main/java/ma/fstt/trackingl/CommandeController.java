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
import ma.fstt.model.Commande;
import ma.fstt.model.CommandeDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static java.lang.Long.parseLong;

public class CommandeController implements Initializable {

    @FXML
    private Button menu;

    @FXML
    private TextField id_produit ;

    @FXML
    private TextField id_livreur ;

    @FXML
    private TextField date ;

    @FXML
    private TextField adresse ;

    @FXML
    private TableView<Commande> tabcom ;

    @FXML
    private TableColumn<Commande, Long> col_id ;

    @FXML
    private TableColumn<Commande, Long> col_id_produit ;

    @FXML
    private TableColumn<Commande, Long> col_id_livreur ;

    @FXML
    private TableColumn <Commande, String> col_date ;

    @FXML
    private TableColumn <Commande, String> col_adresse ;

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
            CommandeDAO commandeDAO = new CommandeDAO();

            Commande com = new Commande(0l , parseLong(id_produit.getText()) , parseLong(id_livreur.getText()), date.getText() ,  adresse.getText() );

            commandeDAO.save(com);

            UpdateTable();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Commande,Long>("id_commande"));
        col_id_produit.setCellValueFactory(new PropertyValueFactory<Commande,Long>("id_produit"));
        col_id_livreur.setCellValueFactory(new PropertyValueFactory<Commande,Long>("id_livreur"));
        col_date.setCellValueFactory(new PropertyValueFactory<Commande,String>("date"));
        col_adresse.setCellValueFactory(new PropertyValueFactory<Commande,String>("adresse"));


        tabcom.setItems(this.getDataCommandes());
    }

    public static ObservableList<Commande> getDataCommandes(){

        CommandeDAO commandeDAO = null;

        ObservableList<Commande> listfx = FXCollections.observableArrayList();

        try {
            commandeDAO = new CommandeDAO();
            for (Commande ettemp : commandeDAO.getAll())
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
        Commande selectedCommande = tabcom.getSelectionModel().getSelectedItem();

        if (selectedCommande == null) {
            // No item selected, display error message
            System.out.println("Error: no item selected for update");
        } else {
            try {
                // Get the updated values
                long updatedId_produit = parseLong(id_produit.getText());
                long updatedId_livreur = parseLong(id_livreur.getText());
                String updatedDate = date.getText();
                String updatedAdresse = adresse.getText();

                // Create a new Commande object with the updated values and the same ID as the selected Commande
                Commande updatedCommande = new Commande(selectedCommande.getId_commande(), updatedId_produit, updatedId_livreur, updatedDate, updatedAdresse);

                // Update the Commande in the database
                CommandeDAO commandeDAO = new CommandeDAO();
                commandeDAO.update(updatedCommande);

                // Update the table
                UpdateTable();

                // Clear the text fields and disable the modifier and supprimer buttons
                id_produit.setText("");
                id_livreur.setText("");
                date.setText("");
                adresse.setText("");
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

        Commande selectedCommande = tabcom.getSelectionModel().getSelectedItem();

        if (selectedCommande == null) {
            // No item selected, display error message
            System.out.println("Error: no item selected for deletion");

        }

        try {
            CommandeDAO commandeDAO = new CommandeDAO();
            commandeDAO.delete(selectedCommande);
            UpdateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void tableClick(javafx.scene.input.MouseEvent mouseEvent) {
        Commande commande = tabcom.getSelectionModel().getSelectedItem();
        id_produit.setText(String.valueOf(commande.getId_produit()));
        id_livreur.setText(String.valueOf(commande.getId_livreur()));
        date.setText(commande.getDate());
        adresse.setText(commande.getAdresse());
        ok.setDisable(true);
        modifier.setDisable(false);
        supprimer.setDisable(false);

    }

    @FXML
    protected  void reset(ActionEvent event){
        ok.setDisable(false);
        modifier.setDisable(true);
        supprimer.setDisable(true);
        id_produit.setText("");
        id_livreur.setText("");
        date.setText("");
        adresse.setText("");
    }
}
