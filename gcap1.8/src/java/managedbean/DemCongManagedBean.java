/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import implementationdao.*;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import pojoandmapping.*;

/**
 *
 * @author Arnaud
 */
public class DemCongManagedBean {
private Conge conge=null;
private Conge conge2=null;
private Authentification authentification;
private AgtraitedemcongeId agTraiteDemCongId;
private Agtraitedemconge agTraiteDemCong;
private Integer nbreJrCong=30;
private String numDemCong=null;

    /**
     * Creates a new instance of DemCongManagedBean
     */
    public DemCongManagedBean() {
        conge=new Conge();
        conge2=new Conge();
        authentification=new Authentification();
        agTraiteDemCongId=new AgtraitedemcongeId();
        agTraiteDemCong=new Agtraitedemconge();
        
    }

    public String getNumDemCong() {
        return numDemCong;
    }

    public void setNumDemCong(String numDemCong) {
        this.numDemCong = numDemCong;
    }

    public Integer getNbreJrCong() {
        return nbreJrCong;
    }

    public void setNbreJrCong(Integer nbreJrCong) {
        this.nbreJrCong = nbreJrCong;
    }

    public Conge getConge() {
        return conge;
    }

    public void setConge(Conge conge) {
        this.conge = conge;
    }
    public String newDemConge(){
    //Declaration de variables 
        String dernierNumCong=null;
        String prochainNumCong=null;
        String inter1=null;
        String inter2=null;
        String tiret="-";
        int numDemEntier=0;
        String anneeEnCours=null;
        
        //Recupération de la date en cours
        Date date;
        Calendar calendar = Calendar.getInstance();
        int annee = calendar.get(Calendar.YEAR);
        int mois= calendar.get(Calendar.MONTH);
        int jour= calendar.get(Calendar.DAY_OF_MONTH);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ConnexionManagedBean connexionBean = (ConnexionManagedBean) fc.getExternalContext().getSessionMap().get("connexionManagedBean");     
        conge.setTypeconge(new DemCongAnnDao().returnTypeConge());
        
        Calendar c = Calendar.getInstance ();
        date = c.getTime ();
        authentification=connexionBean.getUser();
        conge.setAgent(connexionBean.getAgent());
        System.out.println("Le id du compte de l'agent1 est: "+authentification.getAgent().getClass());
        System.out.println("Le id du compte de l'agent est: "+authentification.getIdCompt());
        System.out.println("La date du jour est: "+date);
        conge2=new DemCongAnnDao().returnNewNumCong();
       
        dernierNumCong=conge2.getNumDemConge();
        dernierNumCong=dernierNumCong.substring(5);
        numDemEntier=Integer.parseInt(dernierNumCong);
        System.out.println("Le dernier numéro de demande en entier: "+numDemEntier);
        numDemEntier=numDemEntier+1;
        if(mois==1 && jour==1) numDemEntier=0;
        inter1=String.valueOf(numDemEntier);
        anneeEnCours=String.valueOf(annee);
        System.out.println("Le prochain numéro de demande en string: "+inter1);
        inter2=anneeEnCours.concat(tiret);
        System.out.println("La concaténation avec tiret: "+inter2);
        prochainNumCong=inter2.concat(inter1);
        System.out.println("Le prochain numéro de demande est: "+prochainNumCong);
        System.out.println("La date de debut du congé est: "+conge.getDateDebut());
        conge.setNumDemConge(prochainNumCong);
        agTraiteDemCongId.setNumDemConge(prochainNumCong);
        agTraiteDemCongId.setMatricAg("DEFAULT");
        agTraiteDemCong.setId(agTraiteDemCongId);
        agTraiteDemCong.setAtraite(0);
        if (authentification.getIdCompt().equalsIgnoreCase("000000A"))
        agTraiteDemCong.setAvis(true);
        else agTraiteDemCong.setAvis(false);
        conge.setDateDem(date);
        
        if(new DemCongAnnDao().newCongAnn(conge,agTraiteDemCong)==1){
            System.out.println("Demande transmise!");
            //this.findNumConge();
            new ConnexionManagedBean().findNumConge();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, " Demande transmise!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);    
            return "ok";
            }
            else return "ko";
        
    }
  public void returnNombreDeJourCong(){
        
        //Declaration de variables
        
        Calendar calendar=Calendar.getInstance();
        String matricAg=null;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ConnexionManagedBean connexionBean = (ConnexionManagedBean)fc.getExternalContext().getSessionMap().get("connexionManagedBean");
        authentification=connexionBean.getUser();
        matricAg=authentification.getIdCompt();
        nbreJrCong= new DemCongAnnDao().returnNombreDeJourCong(authentification);
        
        
    }
  
  public void findDateFin(){
      Calendar calendar=Calendar.getInstance();
      //conge.setDateFin(conge.);
      Date dateDebut=conge.getDateDebut();
      Date t=calendar.getTime();
      calendar.setTime(t);
      this.returnNombreDeJourCong();
        calendar.add(Calendar.DATE, nbreJrCong);
        Date dateFin=calendar.getTime();
        conge.setDateFin(dateFin);
        System.out.println("La date de fin est :" +conge.getDateFin());
  }
  
  public void findNumConge(){
        String dernierNumCong=null;
        String inter1=null;
        String inter2=null;
        String tiret="-";
        int numDemEntier=0;
        String anneeEnCours=null;
        
        //Recupération de la date en cours
        Calendar calendar = Calendar.getInstance();
        int annee = calendar.get(Calendar.YEAR);
        int mois= calendar.get(Calendar.MONTH);
        int jour= calendar.get(Calendar.DAY_OF_MONTH);
        String numCong=null;
        conge=new AuthentificationDao().returnNewNumCong();
        dernierNumCong=conge.getNumDemConge();
        dernierNumCong=dernierNumCong.substring(5);
        numDemEntier=Integer.parseInt(dernierNumCong);
        System.out.println("Le dernier numéro de demande en entier: "+numDemEntier);
        numDemEntier=numDemEntier+1;
        if(mois==1 && jour==1) numDemEntier=0;
        inter1=String.valueOf(numDemEntier);
        anneeEnCours=String.valueOf(annee);
        System.out.println("Le prochain numéro de demande en string: "+inter1);
        inter2=anneeEnCours.concat(tiret);
        System.out.println("La concaténation avec tiret encore: "+inter2);
        numDemCong=inter2.concat(inter1);
        
    }
	
}
