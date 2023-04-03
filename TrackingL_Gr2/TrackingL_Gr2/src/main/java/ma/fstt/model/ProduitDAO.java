package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends BaseDAO<Produit>{
    public ProduitDAO() throws SQLException {

        super();
    }

    @Override
    public void save(Produit object) throws SQLException {

        String request = "insert into produit (nomProd , quantite, prix, description) values (? , ? , ?, ?)";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setString(1 , object.getNomProd());

        this.preparedStatement.setLong(2 , object.getQuantite());

        this.preparedStatement.setLong(3 , object.getPrix());

        this.preparedStatement.setString(4 , object.getDescription());


        this.preparedStatement.execute();
    }

    @Override
    public void update(Produit object) throws SQLException {
        String request = "update produit set nomProd=?, quantite=?, prix=?, description=? where id_produit=?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setString(1 , object.getNomProd());
        this.preparedStatement.setLong(2 , object.getQuantite());
        this.preparedStatement.setLong(3 , object.getPrix());
        this.preparedStatement.setString(4 , object.getDescription());
        this.preparedStatement.setLong(5, object.getId_produit());
        this.preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Produit object) throws SQLException {
        String request = "delete from produit where id_produit=?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, object.getId_produit());
        this.preparedStatement.executeUpdate();
    }

    @Override
    public List<Produit> getAll()  throws SQLException {

        List<Produit> mylist = new ArrayList<Produit>();

        String request = "select * from produit ";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

// parcours de la table
        while ( this.resultSet.next()){

// mapping table objet
            mylist.add(new Produit(this.resultSet.getLong(1) ,
                    this.resultSet.getString(2) ,
                    this.resultSet.getLong(3) ,
                    this.resultSet.getLong(4) ,
                    this.resultSet.getString(5)));
        }


        return mylist;
    }

    @Override
    public Produit getOne(Long id) throws SQLException {
        return null;
    }
}
