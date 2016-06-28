package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui d�crit les m�thodes pour acc�der � la table factivity avec JDBC.
 *
 * @author Thierry Baribaud.
 * @version Juin 2016
 */
public class FactivityDAO extends PatternDAO {

    /**
     * Constructeur de la classe FactivityDAO.
     *
     * @param MyConnection connexion � la base de donn�es courante.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FactivityDAO(Connection MyConnection)
            throws ClassNotFoundException, SQLException {

        this.MyConnection = MyConnection;

        setInvariableSelectStatement("select a4num, a4abbname, a4name"
                + " from factivity");

//        if (myA4num > 0) {
//            Stmt.append(" where a4num = ").append(myA4num);
//        } else {
//            Stmt.append(" where a4num >0");
//            Stmt.append(" order by a4num");
//        }
//        Stmt.append(";");
//
//        setSelectStatement(Stmt.toString());
//        setSelectPreparedStatement();
//        setSelectResultSet();
        setUpdateStatement("update factivity"
                + " set a4abbname=?, a4name=?"
                + " where a4num=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into factivity"
                + " (a4abbname, a4name)"
                + " values(?, ?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from factivity"
                + " where a4num=?;");
//        setDeletePreparedStatement();
    }

    /**
     * Selectionne une activit�.
     *
     * @return une activit�.
     */
    @Override
    public Factivity select() {
        Factivity MyFactivity = null;

        try {
            if (SelectResultSet.next()) {
                MyFactivity = new Factivity();
                MyFactivity.setA4num(SelectResultSet.getInt("a4num"));
                MyFactivity.setA4abbname(SelectResultSet.getString("a4abbname"));
                MyFactivity.setA4name(SelectResultSet.getString("a4name"));
            } else {
                System.out.println("Lecture de factivity termin�e");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de factivity "
                    + MyException.getMessage());
        }
        return MyFactivity;
    }

    /**
     * Met � jour d'une activit�.
     *
     * @param MyFactivity activit� � mettre � jour.
     */
    public void update(Factivity MyFactivity) {
        try {
            UpdatePreparedStatement.setString(1, MyFactivity.getA4abbname());
            UpdatePreparedStatement.setString(2, MyFactivity.getA4name());
            UpdatePreparedStatement.setInt(3, MyFactivity.getA4num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre � jour factivity");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise � jour de factivity "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime d�finitivement une activit�.
     *
     * @param a4num identiant d'une activit� � supprimer.
     */
    @Override
    public void delete(int a4num) {
        try {
            DeletePreparedStatement.setInt(1, a4num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de d�truire une activit� dans factivity");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'une activit� dans factivity "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute une activit� dans la table factivity.
     *
     * @param MyFactivity activit� � ajouter � la table factivity.
     */
    public void insert(Factivity MyFactivity) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("a4name=" + MyFactivity.getA4name());
            InsertPreparedStatement.setString(1, MyFactivity.getA4abbname());
            InsertPreparedStatement.setString(2, MyFactivity.getA4name());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter une activit� dans factivity");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFactivity.setA4num((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                MyFactivity.setA4num(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'une activit� dans factivity "
                    + MyException.getMessage());
        }
    }

    /**
     * M�thode pour filter les r�sultats par identifiant.
     *
     * @param id l'identifiant � utiliser pour le filtrage.
     */
    @Override
    public void filterById(int id) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where a4num = ").append(id).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     */
    @Override
    public void filterByGid(int gid) {
        throw new UnsupportedOperationException("Non support� actuellement");
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe et par code.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     * @param Code � utiliser pour le filtrage.
     */
    @Override
    public void filterByCode(int gid, String Code) {
        throw new UnsupportedOperationException("Non support� actuellement");
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant de groupe � utiliser pour le filtrage.
     * @param Name � utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int gid, String Name) {
        throw new UnsupportedOperationException("Non support� actuellement");
    }

    @Override
    public void update(Object MyObject) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object MyObject) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
