package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui décrit les méthodes pour accéder à la table ftype avec JDBC.
 *
 * @author Thierry Baribaud
 * @version Juillet 2016
 */
public class FtypeDAO extends PatternDAO {

    /**
     * Constructeur de la classe Ftype.
     *
     * @param MyConnection une connexion à la base de données courante.
     * @throws ClassNotFoundException en cas de classe non trouvée.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FtypeDAO(Connection MyConnection)
            throws ClassNotFoundException, SQLException {

        this.MyConnection = MyConnection;

        setInvariableSelectStatement("select ttnum, ttunum, ttextname,"
                + " ttypename, ttype"
                + " from ftype");

        setUpdateStatement("update ftype"
                + " set ttunum=?, ttextname=?, ttypename=?, ttype=?"
                + " where ttnum=?;");

        setInsertStatement("insert into ftype"
                + " (ttunum, ttextname, ttypename, ttype)"
                + " values(?, ?, ?, ?);");

        setDeleteStatement("delete from ftype"
                + " where ttnum=?;");
    }

    /**
     * Sélectionne une raison d'appel.
     *
     * @return la raison d'appel sélectionnée.
     */
    @Override
    public Ftype select() {
        Ftype MyFtype = null;

        try {
            if (SelectResultSet.next()) {
                MyFtype = new Ftype();
                MyFtype.setTtnum(SelectResultSet.getInt("ttnum"));
                MyFtype.setTtunum(SelectResultSet.getInt("ttunum"));
                MyFtype.setTtextname(SelectResultSet.getString("ttextname"));
                MyFtype.setTtypename(SelectResultSet.getString("ttypename"));
                MyFtype.setTtype(SelectResultSet.getInt("ttype"));
            } else {
                System.out.println("Lecture de ftype terminée");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur en lecture de ftype "
                    + MyException.getMessage());
        }
        return MyFtype;
    }

    /**
     * Met à jour une raison d'appel.
     *
     * @param MyFtype raison d'appel à mettre à jour.
     */
    public void update(Ftype MyFtype) {
        try {
            UpdatePreparedStatement.setInt(1, MyFtype.getTtunum());
            UpdatePreparedStatement.setString(2, MyFtype.getTtextname());
            UpdatePreparedStatement.setString(3, MyFtype.getTtypename());
            UpdatePreparedStatement.setInt(4, MyFtype.getTtype());
            UpdatePreparedStatement.setInt(5, MyFtype.getTtnum());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre à jour ftype");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise à jour de ftype "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime définitivement une raison d'appel.
     *
     * @param ttnum identifiant de la raison d'appel à supprimer.
     */
    @Override
    public void delete(int ttnum) {
        try {
            DeletePreparedStatement.setInt(1, ttnum);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de détruire une raison d'appel dans ftype");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'une raison d'appel dans ftype "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute une raison d'appel.
     *
     * @param MyFtype raison d'appel à ajouter.
     */
    public void insert(Ftype MyFtype) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("ttypename=" + MyFtype.getTtypename());
            InsertPreparedStatement.setInt(1, MyFtype.getTtunum());
            InsertPreparedStatement.setString(2, MyFtype.getTtextname());
            InsertPreparedStatement.setString(3, MyFtype.getTtypename());
            InsertPreparedStatement.setInt(4, MyFtype.getTtype());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter une raison d'appel dans ftype");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFtype.setTtnum((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                MyFtype.setTtnum(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'une raison d'appel dans ftype "
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
        Stmt.append(" where ttnum = ").append(id).append(";");
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
        Stmt.append(" where ttunum = ").append(gid).append(";");
        setSelectStatement(Stmt.toString());
    }

    /**
     * Méthode pour filter les résultats par identifiant de groupe et par code.
     *
     * @param gid l'identifiant de groupe à utiliser pour le filtrage.
     * @param Code à utiliser pour le filtrage.
     */
    public void filterByCode(int gid, int Code) {
        StringBuffer Stmt;

        Stmt = new StringBuffer(InvariableSelectStatement);
        Stmt.append(" where ttunum = ").append(gid);
        if (Code > 0) {
            Stmt.append(" and ttype = ").append(Code);
        }
        Stmt.append(" order by ttype;");
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
        Stmt.append(" where ttunum = ").append(gid);
        if (Name != null) {
            Stmt.append(" and ttypename like '").append(Name.trim()).append("%'");
        }
        Stmt.append(" order by ttypename;");
        setSelectStatement(Stmt.toString());
    }

    @Override
    public void filterByCode(int gid, String Code) {
        throw new UnsupportedOperationException("Non supporté actuellement"); //To change body of generated methods, choose Tools | Templates.
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
