package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder à la table foperat avec JDBC.
 *
 * @author Thierry Baribaud
 * @version Juillet 2016
 */
public class FoperatDAO extends PatternDAO {

    /**
     * Constructeur de la classe FoperatDAO.
     *
     * @param MyConnection connexion à la base de données courante.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FoperatDAO(Connection MyConnection)
            throws ClassNotFoundException, SQLException {

        this.connection = MyConnection;

        setInvariableSelectStatement("select onum, oabbname, oname, onumpabx"
                + " from foperat");

        setUpdateStatement("update foperat"
                + " set oabbname=?, oname=?, onumpabx=?"
                + " where onum=?;");

        setInsertStatement("insert into foperat"
                + " (oabbname, oname, onumpabx)"
                + " values(?, ?, ?);");

        setDeleteStatement("delete from foperat"
                + " where onum=?;");
    }

    /**
     * Selectionne un opérateur.
     *
     * @return un opérateur.
     */
    @Override
    public Foperat select() {
        Foperat MyFoperat = null;

        try {
            if (SelectResultSet.next()) {
                MyFoperat = new Foperat();
                MyFoperat.setOnum(SelectResultSet.getInt("onum"));
                MyFoperat.setOabbname(SelectResultSet.getString("oabbname"));
                MyFoperat.setOname(SelectResultSet.getString("oname"));
                MyFoperat.setOnumpabx(SelectResultSet.getInt("onumpabx"));
            } else {
                System.out.println("Lecture de foperat terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de foperat "
                    + MyException.getMessage());
        }
        return MyFoperat;
    }

    /**
     * Met à jour un opérateur.
     *
     * @param MyFoperat opérateur à mettre à jour.
     */
    public void update(Foperat MyFoperat) {
        try {
            UpdatePreparedStatement.setString(1, MyFoperat.getOabbname());
            UpdatePreparedStatement.setString(2, MyFoperat.getOname());
            UpdatePreparedStatement.setInt(3, MyFoperat.getOnumpabx());
            UpdatePreparedStatement.setInt(4, MyFoperat.getOnum());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour foperat");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de foperat "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement un opérateur.
     *
     * @param onum identiant du opérateur à supprimer.
     */
    @Override
    public void delete(int onum) {
        try {
            DeletePreparedStatement.setInt(1, onum);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire un opérateur dans foperat");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'un opérateur dans foperat "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute un opérateur dans la table foperat.
     *
     * @param MyFoperat opérateur à ajouter à la table foperat.
     */
    public void insert(Foperat MyFoperat) {
//        ResultSet MyKeyResultSet = null;

        try {
            System.out.println("oname=" + MyFoperat.getOname());
            InsertPreparedStatement.setString(1, MyFoperat.getOabbname());
            InsertPreparedStatement.setString(2, MyFoperat.getOname());
            InsertPreparedStatement.setInt(3, MyFoperat.getOnumpabx());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un opérateur dans foperat");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFoperat.setOnum((int) MyKeyResultSet.getInt(1));
//                MyKeyResultSet.close();
                MyFoperat.setOnum(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un opérateur dans foperat "
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
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where onum = ").append(id).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     */
    @Override
    public void filterByGid(int gid) {
        throw new UnsupportedOperationException("Non supporté actuellement");
    }

    /**
     * Méthode pour filter les résultats par code.
     *
     * @param Code à utiliser pour le filtrage.
     */
    public void filterByCode(String Code) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where oabbname = '").append(Code).append("';");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par code.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Code à utiliser pour le filtrage.
     */
    @Override
    public void filterByCode(int gid, String Code) {
        throw new UnsupportedOperationException("Non supporté actuellement");
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Name à utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int gid, String Name) {
        throw new UnsupportedOperationException("Non supporté actuellement");
    }

    /**
     * Méthode pour filter les résultats par nom.
     *
     * @param Name à utiliser pour le filtrage.
     */
    public void filterByName(String Name) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where oname like '").append(Name).append("%';");
        setSelectStatement(Stmt.toString());
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
