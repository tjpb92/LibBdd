package bdd;

/**
 * Etat d'un ticket, en-cours dans fcalls et archiv� dans f99calls.
 *
 * @author Thierry Baribaud
 * @version 0.16
 */
public enum EtatTicket {

    /**
     * Etat indiquant que le ticket est en cours de traitement
     */
    EN_COURS,

    /**
     * Etat indiquant que le ticket a �t� trait�
     */
    ARCHIVE
};
