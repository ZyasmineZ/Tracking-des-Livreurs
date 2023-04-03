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
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button menu;

    @FXML
    private TextField nom ;


    @FXML
    private TextField tele ;


    @FXML
    private TableView<Livreur> mytable ;


    @FXML
    private TableColumn<Livreur ,Long> col_id ;

    @FXML
    private TableColumn <Livreur ,String> col_nom ;

    @FXML
    private TableColumn <Livreur ,String> col_tele ;

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
            LivreurDAO livreurDAO = new LivreurDAO();

            Livreur liv = new Livreur(0l , nom.getText() , tele.getText());

            livreurDAO.save(liv);

            UpdateTable();
            nom.setText("");
            tele.setText("");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Livreur,Long>("id_livreur"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Livreur,String>("nom"));
        col_tele.setCellValueFactory(new PropertyValueFactory<Livreur,String>("telephone"));



        mytable.setItems(this.getDataLivreurs());
    }

    public static ObservableList<Livreur> getDataLivreurs(){

        LivreurDAO livreurDAO = null;

        ObservableList<Livreur> listfx = FXCollections.observableArrayList();

        try {
            livreurDAO = new LivreurDAO();
            for (Livreur ettemp : livreurDAO.getAll())
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
        Livreur selectedLivreur = mytable.getSelectionModel().getSelectedItem();

        if (selectedLivreur == null) {
            // No item selected, display error message
            System.out.println("Error: no item selected for update");
        } else {
            try {
                // Get the updated name and telephone values
                String updatedNom = nom.getText();
                String updatedTele = tele.getText();

                // Create a new Livreur object with the updated values and the same ID as the selected Livreur
                Livreur updatedLivreur = new Livreur(selectedLivreur.getId_livreur(), updatedNom, updatedTele);

                // Update the Livreur in the database
                LivreurDAO livreurDAO = new LivreurDAO();
                livreurDAO.update(updatedLivreur);

                // Update the table
                UpdateTable();

                // Clear the text fields and disable the modifier and supprimer buttons
                nom.setText("");
                tele.setText("");
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

        Livreur selectedLivreur = mytable.getSelectionModel().getSelectedItem();

        if (selectedLivreur == null) {
            // No item selected, display error message
            System.out.println("Error: no item selected for deletion");

        }

        try {
            LivreurDAO livreurDAO = new LivreurDAO();
            livreurDAO.delete(selectedLivreur);
            UpdateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void tableClick(javafx.scene.input.MouseEvent mouseEvent) {
        Livreur livreur = mytable.getSelectionModel().getSelectedItem();
        nom.setText(livreur.getNom());
        tele.setText(livreur.getTelephone());
        ok.setDisable(true);
        modifier.setDisable(false);
        supprimer.setDisable(false);

    }

    @FXML
    protected  void reset(ActionEvent event){
        ok.setDisable(false);
        modifier.setDisable(true);
        supprimer.setDisable(true);
        nom.setText("");
        tele.setText("");
    }
}