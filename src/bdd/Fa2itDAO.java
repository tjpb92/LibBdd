package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder à la table fa2it avec JDBC.
 *
 * @author Thierry Baribaud
 * @version 0.27
 */
public class Fa2itDAO extends PatternDAO {

    /**
     * Constructeur de la classe Fa2it.
     *
     * @param connection une connexion à la base de données courante.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public Fa2itDAO(Connection connection)
            throws ClassNotFoundException, SQLException {

        this.connection = connection;

        setInvariableSelectStatement("select a11num, a11onum0, a11laguid,"
                + " a11evttype, a11data,"
                + " a11status, a11nberr, a11size,"
                + " a11credate, a11update"
                + " from fa2it");

        setUpdateStatement("update fa2it"
                + " set a11onum0=?, a11laguid=?,"
                + " a11evttype=?, a11data=?,"
                + " a11status=?, a11nberr=?, a11size=?,"
                + " a11credate=?, a11update=?"
                + " where a11num=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into fa2it"
                + " (a11onum0, a11laguid, "
                + " a11evttype, a11data,"
                + " a11status, a11nberr, a11size,"
                + " a11credate, a11update)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from fa2it"
                + " where a11num=?;");
//        setDeletePreparedStatement();
    }

    /**
     * Sélectionne un événement.
     *
     * @return l'événement sélectionné.
     */
    @Override
    public Fa2it select() {
        Fa2it fa2it = null;

        try {
            if (SelectResultSet.next()) {
                fa2it = new Fa2it();
                fa2it.setA11num(SelectResultSet.getInt("a11num"));
                fa2it.setA11onum0(SelectResultSet.getInt("a11onum0"));
                fa2it.setA11laguid(SelectResultSet.getString("a11laguid"));
                fa2it.setA11evttype(SelectResultSet.getInt("a11evttype"));
                fa2it.setA11data(SelectResultSet.getString("a11data"));
                fa2it.setA11status(SelectResultSet.getInt("a11status"));
                fa2it.setA11nberr(SelectResultSet.getInt("a11nberr"));
                fa2it.setA11size(SelectResultSet.getInt("a11size"));
                fa2it.setA11credate(SelectResultSet.getTimestamp("a11credate"));
                fa2it.setA11update(SelectResultSet.getTimestamp("a11update"));
            } else {
                System.out.println("Lecture de fa2it terminée");
            }
        } catch (SQLException exception) {
            System.out.println("Erreur en lecture de fa2it "
                    + exception.getMessage());
        }
        return fa2it;
    }

    /**
     * Met à jour un événement.
     *
     * @param fa2it agence à mettre à jour.
     */
    public void update(Fa2it fa2it) {
        try {
            UpdatePreparedStatement.setInt(1, fa2it.getA11onum0());
            UpdatePreparedStatement.setString(2, fa2it.getA11laguid());
            UpdatePreparedStatement.setInt(3, fa2it.getA11evttype());
            UpdatePreparedStatement.setString(4, fa2it.getA11data());
            UpdatePreparedStatement.setInt(5, fa2it.getA11status());
            UpdatePreparedStatement.setInt(6, fa2it.getA11nberr());
            UpdatePreparedStatement.setInt(7, fa2it.getA11size());
            UpdatePreparedStatement.setTimestamp(8, fa2it.getA11credate());
            UpdatePreparedStatement.setTimestamp(9, fa2it.getA11update());
            UpdatePreparedStatement.setInt(10, fa2it.getA11num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour fa2it");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de fa2it "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement un événement.
     *
     * @param a11num identifiant de l'événement à supprimer.
     */
    @Override
    public void delete(int a11num) {
        try {
            DeletePreparedStatement.setInt(1, a11num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire un événement dans fa2it");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'un événement dans fa2it "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute un événement.
     *
     * @param fa2it événement à ajouter.
     */
    public void insert(Fa2it fa2it) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("a11laguid=" + fa2it.getA11laguid());
            InsertPreparedStatement.setInt(1, fa2it.getA11onum0());
            InsertPreparedStatement.setString(2, fa2it.getA11laguid());
            InsertPreparedStatement.setInt(3, fa2it.getA11evttype());
            InsertPreparedStatement.setString(4, fa2it.getA11data());
            InsertPreparedStatement.setInt(5, fa2it.getA11status());
            InsertPreparedStatement.setInt(6, fa2it.getA11nberr());
            InsertPreparedStatement.setInt(7, fa2it.getA11size());
            InsertPreparedStatement.setTimestamp(8, fa2it.getA11credate());
            InsertPreparedStatement.setTimestamp(9, fa2it.getA11update());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un événement dans fa2it");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFa2it.setA11num((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                fa2it.setA11num(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un événement dans fa2it "
                    + MyException.getMessage());
        }
    }

    /**
     * Méthode pour filter les résultats par identifiant.
     *
     * @param id l'identifiant à utiliser pour le filtrage.
     */
    @Override
    public void filterById(int id) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where a11num = ").append(id).append(";");
        setSelectStatement(stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe.
     *
     * @param laguid l'identifiant de groupe à utiliser pour le filtrage.
     */
    public void filterByGid(String laguid) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where a11laguid = \"").append(laguid).append("\";");
        setSelectStatement(stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par status.
     *
     * @param status status à utiliser pour le filtrage.
     */
    public void filterByStatus(int status) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where a11status = ").append(status).append(";");
        setSelectStatement(stmt.toString());
    }

    @Override
    public void filterByGid(int gid) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filterByCode(int gid, String code) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filterByName(int gid, String name) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object object) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object object) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
