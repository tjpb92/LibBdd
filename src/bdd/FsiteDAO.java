package bdd;

import com.informix.jdbc.IfmxStatement;
import java.sql.*;

/**
 * Classe qui d�crit les m�thodes pour acc�der � la table fsite avec JDBC.
 *
 * @author Thierry Baribaud
 * @version 0.28
 */
public class FsiteDAO extends PatternDAO {

    /**
     * Constructeur de la classe Fsite.
     *
     * @param connection une connexion � la base de donn�es courante.
     * @throws ClassNotFoundException en cas de classe non trouv�e.
     * @throws java.sql.SQLException en cas d'erreur SQL.
     */
    public FsiteDAO(Connection connection)
            throws ClassNotFoundException, SQLException {

        this.connection = connection;

        setInvariableSelectStatement("select s3num, s3onum, s3number,"
                + " s3unum, s3number2,"
                + " s3type, s3name, s3address,"
                + " s3address2, s3poscode, s3city"
                + " from fsite");

        setUpdateStatement("poscode fsite"
                + " set s3onum=?, s3number=?,"
                + " s3unum=?, s3number2=?,"
                + " s3type=?, s3name=?, s3address=?,"
                + " s3address2=?, s3poscode=?, s3city=?"
                + " where s3num=?;");
//        setUpdatePreparedStatement();

        setInsertStatement("insert into fsite"
                + " (s3onum, s3number, "
                + " s3unum, s3number2,"
                + " s3type, s3name, s3address,"
                + " s3address2, s3poscode, s3city)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
//        setInsertPreparedStatement();

        setDeleteStatement("delete from fsite"
                + " where s3num=?;");
//        setDeletePreparedStatement();
    }

    /**
     * S�lectionne un �v�nement.
     *
     * @return l'�v�nement s�lectionn�.
     */
    @Override
    public Fsite select() {
        Fsite fsite = null;

        try {
            if (SelectResultSet.next()) {
                fsite = new Fsite();
                fsite.setS3num(SelectResultSet.getInt("s3num"));
                fsite.setS3onum(SelectResultSet.getInt("s3onum"));
                fsite.setS3number(SelectResultSet.getString("s3number"));
                fsite.setS3unum(SelectResultSet.getInt("s3unum"));
                fsite.setS3number2(SelectResultSet.getString("s3number2"));
                fsite.setS3type(SelectResultSet.getInt("s3type"));
                fsite.setS3name(SelectResultSet.getString("s3name"));
                fsite.setS3address(SelectResultSet.getString("s3address"));
                fsite.setS3address2(SelectResultSet.getString("s3address2"));
                fsite.setS3poscode(SelectResultSet.getString("s3poscode"));
                fsite.setS3city(SelectResultSet.getString("s3city"));
            } else {
                System.out.println("Lecture de fsite termin�e");
            }
        } catch (SQLException exception) {
            System.out.println("Erreur en lecture de fsite "
                    + exception.getMessage());
        }
        return fsite;
    }

    /**
     * Met � jour un �v�nement.
     *
     * @param fsite agence � mettre � jour.
     */
    public void update(Fsite fsite) {
        try {
            UpdatePreparedStatement.setInt(1, fsite.getS3onum());
            UpdatePreparedStatement.setString(2, fsite.getS3number());
            UpdatePreparedStatement.setInt(3, fsite.getS3unum());
            UpdatePreparedStatement.setString(4, fsite.getS3number2());
            UpdatePreparedStatement.setInt(5, fsite.getS3type());
            UpdatePreparedStatement.setString(6, fsite.getS3name());
            UpdatePreparedStatement.setString(7, fsite.getS3address());
            UpdatePreparedStatement.setString(8, fsite.getS3address2());
            UpdatePreparedStatement.setString(9, fsite.getS3poscode());
            UpdatePreparedStatement.setString(10, fsite.getS3city());
            UpdatePreparedStatement.setInt(11, fsite.getS3num());
            setNbAffectedRow(UpdatePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de mettre � jour fsite");
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de la mise � jour de fsite "
                    + MyException.getMessage());
        }
    }

    /**
     * Supprime d�finitivement un �v�nement.
     *
     * @param s3num identifiant de l'�v�nement � supprimer.
     */
    @Override
    public void delete(int s3num) {
        try {
            DeletePreparedStatement.setInt(1, s3num);
            setNbAffectedRow(DeletePreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible de d�truire un �v�nement dans fsite");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression d'un �v�nement dans fsite "
                    + e.getMessage());
        }
    }

    /**
     * Ajoute un �v�nement.
     *
     * @param fsite �v�nement � ajouter.
     */
    public void insert(Fsite fsite) {
//        ResultSet MyKeyResultSet;

        try {
            System.out.println("s3number=" + fsite.getS3number());
            InsertPreparedStatement.setInt(1, fsite.getS3onum());
            InsertPreparedStatement.setString(2, fsite.getS3number());
            InsertPreparedStatement.setInt(3, fsite.getS3unum());
            InsertPreparedStatement.setString(4, fsite.getS3number2());
            InsertPreparedStatement.setInt(5, fsite.getS3type());
            InsertPreparedStatement.setString(6, fsite.getS3name());
            InsertPreparedStatement.setString(7, fsite.getS3address());
            InsertPreparedStatement.setString(8, fsite.getS3address2());
            InsertPreparedStatement.setString(9, fsite.getS3poscode());
            InsertPreparedStatement.setString(10, fsite.getS3city());
            setNbAffectedRow(InsertPreparedStatement.executeUpdate());
            if (getNbAffectedRow() == 0) {
                System.out.println("Impossible d'ajouter un �v�nement dans fsite");
            } else {
//                Does not work with Informix IDS2000
//                MyKeyResultSet = InsertPreparedStatement.getGeneratedKeys();
//                if (MyKeyResultSet.next()) {
//                    MyFsite.setS3num((int) MyKeyResultSet.getInt(1));
//                }
//                MyKeyResultSet.close();
                fsite.setS3num(
                        ((IfmxStatement) InsertPreparedStatement).getSerial());
            }
        } catch (SQLException MyException) {
            System.out.println("Erreur lors de l'insertion d'un �v�nement dans fsite "
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
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where s3num = ").append(id).append(";");
        setSelectStatement(stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par identifiant de groupe.
     *
     * @param number l'identifiant de groupe � utiliser pour le filtrage.
     */
    public void filterByGid(String number) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where s3number = \"").append(number).append("\";");
        setSelectStatement(stmt.toString());
    }

    /**
     * M�thode pour filter les r�sultats par type.
     *
     * @param gid identifiant du client
     * @param type type � utiliser pour le filtrage.
     */
    public void filterByType(int gid, int type) {
        StringBuffer stmt;

        stmt = new StringBuffer(InvariableSelectStatement);
        stmt.append(" where s3unum = ").append(gid);
        stmt.append(" and s3type = ").append(type);
        stmt.append(" and s3generic = 0");
        stmt.append(" and s3active = 1");
        stmt.append(";");
        setSelectStatement(stmt.toString());
    }

    @Override
    public void filterByGid(int gid) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filterByCode(int gid, String code) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filterByName(int gid, String name) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object object) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Object object) {
        throw new UnsupportedOperationException("Non support� actuellement"); //To change body of generated methods, choose Tools | Templates.
    }
}
