package demax.geoterre.application.datas;

import java.sql.Date;

/**
 * Created by 2Max on 02/11/2015.
 */
public class Task {

    private int ID;
    private int IDParcelle;
    private String libelle;
    private Date datestart;
    private Date dateend;
    private String commentaire;

    public Task(int ID, int IDParcelle, String libelle, Date datestart, Date dateend, String commentaire) {
        this.ID = ID;
        this.IDParcelle = IDParcelle;
        this.libelle = libelle;
        this.datestart = datestart;
        this.dateend = dateend;
        this.commentaire = commentaire;
    }

    public int getID() {
        return ID;
    }

    public int getIDParcelle() {
        return IDParcelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public Date getDatestart() {
        return datestart;
    }

    public Date getDateend() {
        return dateend;
    }

    public String getCommentaire() {
        return commentaire;
    }
}
