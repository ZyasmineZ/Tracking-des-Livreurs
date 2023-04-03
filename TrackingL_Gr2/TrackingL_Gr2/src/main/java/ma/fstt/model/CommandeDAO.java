package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO extends BaseDAO<Commande>{
    public CommandeDAO() throws SQLException {
        super();
    }

    @Override
    public void save(Commande object) throws SQLException {

        String request = "insert into commande (id_produit , id_livreur, date, adresse) values (? , ? , ?, ?)";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setLong(1 , object.getId_produit());

        this.preparedStatement.setLong(2 , object.getId_livreur());

        this.preparedStatement.setString(3 , object.getDate());

        this.preparedStatement.setString(4 , object.getAdresse());


        this.preparedStatement.execute();
    }

    @Override
    public void update(Commande object) throws SQLException {
        String request = "update commande set id_produit=?, id_livreur=?, date=?, adresse=? where id_commande=?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1 , object.getId_produit());
        this.preparedStatement.setLong(2 , object.getId_livreur());
        this.preparedStatement.setString(3 , object.getDate());
        this.preparedStatement.setString(4 , object.getAdresse());
        this.preparedStatement.setLong(5, object.getId_commande());
        this.preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Commande object) throws SQLException {
        String request = "delete from commande where id_commande=?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, object.getId_commande());
        this.preparedStatement.executeUpdate();
    }

    @Override
    public List<Commande> getAll()  throws SQLException {

        List<Commande> mylist = new ArrayList<Commande>();

        String request = "select * from commande ";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

// parcours de la table
        while ( this.resultSet.next()){

// mapping table objet
            mylist.add(new Commande(this.resultSet.getLong(1) ,
                    this.resultSet.getLong(2) ,
                    this.resultSet.getLong(3) ,
                    this.resultSet.getString(4) ,
                    this.resultSet.getString(5)));
        }


        return mylist;
    }

    @Override
    public Commande getOne(Long id) throws SQLException {
        return null;
    }
}
