package bdd;

import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder à la table factivity avec JDBC.
 *
 * @author Thierry Baribaud.
 * @version Juin 2015
 */
public class FactivityDAO extends PaternDAO {

    /**
     * Constructeur de la classe FactivityDAO.
     *
     * @param MyConnection connexion à la base de données courante.
     * @param myA4num identifiant interne d'une activité,
     * @throws ClassNotFoundException en cas de classse non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FactivityDAO(Connection MyConnection, int myA4num)
            throws ClassNotFoundException, SQLException {

        StringBuffer Stmt;

        this.MyConnection = MyConnection;

        Stmt = new StringBuffer("select a4num, a4abbname, a4name"
                + " from factivity");

        if (myA4num > 0) {
            Stmt.append(" where a4num = ").append(myA4num);
        } else {
            Stmt.append(" where a4num >0");
            Stmt.append(" order by a4num");
        }
        Stmt.append(";");

        setReadStatement(Stmt.toString());
        setReadPreparedStatement();
        setReadResultSet();

        setUpdateStatement("update factivity"
                + " set a4abbname=?, a4name=?"
                + " where a4num=?;");
        setUpdatePreparedStatement();

        setInsertStatement("insert into factivity"
                + " (a4abbname, a4name)"
                + " values(?, ?);");
        setInsertPreparedStatement();

        setDeleteStatement("delete from factivity"
                + " where a4num=?;");
        setDeletePreparedStatement();
    }

    /**
     * Selectionne une activité.
     *
     * @return une activité.
     */
    @Override
    public Factivity select() {
        Factivity MyFactivity = null;

        try {
            if (ReadResultSet.next()) {
                MyFactivity = new Factivity();
                MyFactivity.setA4num(ReadResultSet.getInt("a4num"));
                MyFactivity.setA4abbname(ReadResultSet.getString("a4abbname"));
                MyFactivity.setA4name(ReadResultSet.getString("a4name"));
            } else {
                System.out.println("Lecture de factivity terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de factivity "
                    + MyException.getMessage());
        }
        return MyFactivity;
    }

    /**
     * Met à jour d'une activité.
     *
     * @param MyFactivity activité à mettre à jour.
     */
    public void update(Factivity MyFactivity) {
        try {
            UpdatePreparedStatement.setString(1, MyFactivity.getA4abbname());
            UpdatePreparedStatement.setString(2, MyFactivity.getA4name());
            UpdatePreparedStatement.setInt(3, MyFactivity.getA4num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour factivity");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de factivity "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement une activité.
     *
     * @param a4num identiant d'une activité à supprimer.
     */
    @Override
    public void delete(int a4num) {
        try {
            DeletePreparedStatement.setInt(1, a4num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire un service d'urgence dans factivity");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'un service d'urgence dans factivity "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute une activité dans la table factivity.
     *
     * @param MyFactivity activité à ajouter à la table factivity.
     */
    public void insert(Factivity MyFactivity) {
        ResultSet MyKeyResultSet = null;

        try {
            System.out.println("a4name=" + MyFactivity.getA4name());
            InsertPreparedStatement.setString(1, MyFactivity.getA4abbname());
            InsertPreparedStatement.setString(2, MyFactivity.getA4name());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter une activité dans factivity");
            } else {
                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
                if (MyKeyResultSet.next()) {
                    MyFactivity.setA4num((int) MyKeyResultSet.getInt(1));
                }
            }
            MyKeyResultSet.close();
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'une activité dans factivity "
                    + MyException.getMessage());
        }
    }

    @Override
    public void update(Object MyObject) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
