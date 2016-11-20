package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder à la table fagency avec JDBC.
 *
 * @author Thierry Baribaud
 * @version 0.13
 */
public class FagencyDAO extends PatternDAO {

    /**
     * Constructeur de la classe Fagency.
     *
     * @param MyConnection une connexion à la base de données courante.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FagencyDAO(Connection MyConnection)
            throws ClassNotFoundException, SQLException {

        this.MyConnection = MyConnection;

        setInvariableSelectStatement("select a6num, a6unum, a6extname, a6name, a6abbname, a6email,"
                + " a6daddress, a6daddress2, a6dposcode, a6dcity,"
                + " a6teloff, a6teldir, a6telfax,"
                + " a6active, a6begactive, a6endactive,"
                + " a6urglevel, a6uuid"
                + " from fagency");

//        if (myA6num > 0) {
//            Stmt.append(" where a6num = ").append(myA6num);
//        } else {
//            Stmt.append(" where a6num >0");
//        }
//        if (MyA6unum > 0) {
//            Stmt.append(" and a6unum = ").append(MyA6unum);
//        }
//        if (MyA6name != null) {
//            Stmt.append(" and a6name like \"").append(MyA6name).append("%\"");
//        }
//        Stmt.append(" order by a6num;");
//        setSelectStatement(Stmt.toString());
//        setSelectPreparedStatement();
//        setSelectResultSet();
        setUpdateStatement("update fagency"
                + " set a6unum=?, a6extname=?, a6name=?, a6abbname=?, a6email=?,"
                + " a6daddress=?, a6daddress2=?, a6dposcode=?, a6dcity=?,"
                + " a6teloff=?, a6teldir=?, a6telfax=?,"
                + " a6active=?, a6begactive=?, a6endactive=?,"
                + " a6urglevel=?, a6uuid=?"
                + " where a6num=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into fagency"
                + " (a6unum, a6extname, a6name, a6abbname, a6email,"
                + " a6daddress, a6daddress2, a6dposcode, a6dcity,"
                + " a6teloff, a6teldir, a6telfax,"
                + " a6active, a6begactive, a6endactive)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from fagency"
                + " where a6num=?;");
//        setDeletePreparedStatement();
    }

    /**
     * Sélectionne une agence.
     *
     * @return l'agence sélectionnée.
     */
    @Override
    public Fagency select() {
        Fagency MyFagency = null;

        try {
            if (SelectResultSet.next()) {
                MyFagency = new Fagency();
                MyFagency.setA6num(SelectResultSet.getInt("a6num"));
                MyFagency.setA6unum(SelectResultSet.getInt("a6unum"));
                MyFagency.setA6extname(SelectResultSet.getString("a6extname"));
                MyFagency.setA6name(SelectResultSet.getString("a6name"));
                MyFagency.setA6abbname(SelectResultSet.getString("a6abbname"));
                MyFagency.setA6email(SelectResultSet.getString("a6email"));
                MyFagency.setA6daddress(SelectResultSet.getString("a6daddress"));
                MyFagency.setA6daddress2(SelectResultSet.getString("a6daddress2"));
                MyFagency.setA6dposcode(SelectResultSet.getString("a6dposcode"));
                MyFagency.setA6dcity(SelectResultSet.getString("a6dcity"));
                MyFagency.setA6teloff(SelectResultSet.getString("a6teloff"));
                MyFagency.setA6teldir(SelectResultSet.getString("a6teldir"));
                MyFagency.setA6telfax(SelectResultSet.getString("a6telfax"));
                MyFagency.setA6active(SelectResultSet.getInt("a6active"));
                MyFagency.setA6begactive(SelectResultSet.getTimestamp("a6begactive"));
                MyFagency.setA6endactive(SelectResultSet.getTimestamp("a6endactive"));
                MyFagency.setA6UrgLevel(SelectResultSet.getString("a6urglevel"));
                MyFagency.setA6Uuid(SelectResultSet.getString("a6uuid"));
            } else {
                System.out.println("Lecture de fagency terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de fagency "
                    + MyException.getMessage());
        }
        return MyFagency;
    }

    /**
     * Met à jour une agence.
     *
     * @param MyFagency agence à mettre à jour.
     */
    public void update(Fagency MyFagency) {
        try {
            UpdatePreparedStatement.setInt(1, MyFagency.getA6unum());
            UpdatePreparedStatement.setString(2, MyFagency.getA6extname());
            UpdatePreparedStatement.setString(3, MyFagency.getA6name());
            UpdatePreparedStatement.setString(4, MyFagency.getA6abbname());
            UpdatePreparedStatement.setString(5, MyFagency.getA6email());
            UpdatePreparedStatement.setString(6, MyFagency.getA6daddress());
            UpdatePreparedStatement.setString(7, MyFagency.getA6daddress2());
            UpdatePreparedStatement.setString(8, MyFagency.getA6dposcode());
            UpdatePreparedStatement.setString(9, MyFagency.getA6dcity());
            UpdatePreparedStatement.setString(10, MyFagency.getA6teloff());
            UpdatePreparedStatement.setString(11, MyFagency.getA6teldir());
            UpdatePreparedStatement.setString(12, MyFagency.getA6telfax());
            UpdatePreparedStatement.setInt(13, MyFagency.getA6active());
            UpdatePreparedStatement.setTimestamp(14, MyFagency.getA6begactive());
            UpdatePreparedStatement.setTimestamp(15, MyFagency.getA6endactive());
            UpdatePreparedStatement.setString(16, MyFagency.getA6UrgLevel());
            UpdatePreparedStatement.setString(17, MyFagency.getA6Uuid());
            UpdatePreparedStatement.setInt(1, MyFagency.getA6num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour fagency");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de fagency "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement une agence.
     *
     * @param a6num identifiant de l'agence à supprimer.
     */
    @Override
    public void delete(int a6num) {
        try {
            DeletePreparedStatement.setInt(1, a6num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire une agence dans fagency");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'une agence dans fagency "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute une agence.
     *
     * @param MyFagency agence à ajouter.
     */
    public void insert(Fagency MyFagency) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("a6name=" + MyFagency.getA6name());
            InsertPreparedStatement.setInt(1, MyFagency.getA6unum());
            InsertPreparedStatement.setString(2, MyFagency.getA6extname());
            InsertPreparedStatement.setString(3, MyFagency.getA6name());
            InsertPreparedStatement.setString(4, MyFagency.getA6abbname());
            InsertPreparedStatement.setString(5, MyFagency.getA6email());
            InsertPreparedStatement.setString(6, MyFagency.getA6daddress());
            InsertPreparedStatement.setString(7, MyFagency.getA6daddress2());
            InsertPreparedStatement.setString(8, MyFagency.getA6dposcode());
            InsertPreparedStatement.setString(9, MyFagency.getA6dcity());
            InsertPreparedStatement.setString(10, MyFagency.getA6teloff());
            InsertPreparedStatement.setString(11, MyFagency.getA6teldir());
            InsertPreparedStatement.setString(12, MyFagency.getA6telfax());
            InsertPreparedStatement.setInt(13, MyFagency.getA6active());
            InsertPreparedStatement.setTimestamp(14, MyFagency.getA6begactive());
            InsertPreparedStatement.setTimestamp(15, MyFagency.getA6endactive());
            InsertPreparedStatement.setString(16, MyFagency.getA6UrgLevel());
            InsertPreparedStatement.setString(17, MyFagency.getA6Uuid());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter une agence dans fagency");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFagency.setA6num((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                MyFagency.setA6num(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'une agence dans fagency "
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
        Stmt.append(" where a6num = ").append(id).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     */
    @Override
    public void filterByGid(int gid) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where a6unum = ").append(gid).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant Performance Immo.
     *
     * @param Uuid à utiliser pour le filtrage.
     */
    public void filterByUuid(String Uuid) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where a6uuid = '").append(Uuid).append("';");
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
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where a6unum = ").append(gid);
        if (Code != null) {
            Stmt.append(" and a6abbname = '").append(Code.trim()).append("'");
        }
        Stmt.append(" order by a6abbname;");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par nom.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Name à utiliser pour le filtrage.
     */
    @Override
    public void filterByName(int gid, String Name) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where a6unum = ").append(gid);
        if (Name != null) {
            Stmt.append(" and a6name like '").append(Name.trim()).append("%'");
        }
        Stmt.append(" order by a6name;");
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
