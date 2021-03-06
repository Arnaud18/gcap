package pojoandmapping;
// Generated 23 juin 2014 16:20:08 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Conge generated by hbm2java
 */
public class Conge  implements java.io.Serializable {


     private String numDemConge;
     private Typeconge typeconge;
     private Agent agent;
     private Date dateDem;
     private Date dateDebut;
     private Date dateFin;
     private String villeJouissance;
     private String paysJouissance;
     private String commentaire;
     private Set agtraitedemconges = new HashSet(0);

    public Conge() {
    }

	
    public Conge(String numDemConge) {
        this.numDemConge = numDemConge;
    }
    public Conge(String numDemConge, Typeconge typeconge, Agent agent, Date dateDem, Date dateDebut, Date dateFin, String villeJouissance, String paysJouissance, String commentaire, Set agtraitedemconges) {
       this.numDemConge = numDemConge;
       this.typeconge = typeconge;
       this.agent = agent;
       this.dateDem = dateDem;
       this.dateDebut = dateDebut;
       this.dateFin = dateFin;
       this.villeJouissance = villeJouissance;
       this.paysJouissance = paysJouissance;
       this.commentaire = commentaire;
       this.agtraitedemconges = agtraitedemconges;
    }
   
    public String getNumDemConge() {
        return this.numDemConge;
    }
    
    public void setNumDemConge(String numDemConge) {
        this.numDemConge = numDemConge;
    }
    public Typeconge getTypeconge() {
        return this.typeconge;
    }
    
    public void setTypeconge(Typeconge typeconge) {
        this.typeconge = typeconge;
    }
    public Agent getAgent() {
        return this.agent;
    }
    
    public void setAgent(Agent agent) {
        this.agent = agent;
    }
    public Date getDateDem() {
        return this.dateDem;
    }
    
    public void setDateDem(Date dateDem) {
        this.dateDem = dateDem;
    }
    public Date getDateDebut() {
        return this.dateDebut;
    }
    
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    public Date getDateFin() {
        return this.dateFin;
    }
    
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    public String getVilleJouissance() {
        return this.villeJouissance;
    }
    
    public void setVilleJouissance(String villeJouissance) {
        this.villeJouissance = villeJouissance;
    }
    public String getPaysJouissance() {
        return this.paysJouissance;
    }
    
    public void setPaysJouissance(String paysJouissance) {
        this.paysJouissance = paysJouissance;
    }
    public String getCommentaire() {
        return this.commentaire;
    }
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    public Set getAgtraitedemconges() {
        return this.agtraitedemconges;
    }
    
    public void setAgtraitedemconges(Set agtraitedemconges) {
        this.agtraitedemconges = agtraitedemconges;
    }




}


