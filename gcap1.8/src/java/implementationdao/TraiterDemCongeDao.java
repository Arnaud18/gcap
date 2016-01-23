/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementationdao;

import hibernateUtil.HibernateUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import managedbean.ConnexionManagedBean;
import org.hibernate.Query;
import org.hibernate.Session;
import pojoandmapping.*;

/**
 *
 * @author Arnaud
 */
public class TraiterDemCongeDao {
 private Session session=null;
 private List<Conge> conge;
 private DataModel<Agtraitedemconge> agTraiteDemConge;
 FacesContext fc = FacesContext.getCurrentInstance();
 ConnexionManagedBean connexionBean = (ConnexionManagedBean) fc.getExternalContext().getSessionMap().get("connexionManagedBean");

 
 public TraiterDemCongeDao(){
     conge= new ArrayList();
     agTraiteDemConge=new ListDataModel();
 }
 
 public List<Conge> afficheDemCongAnEnCours(){
     session=HibernateUtil.getSessionFactory().openSession();
     try { 
    session.beginTransaction();
    Query query = session.createQuery("from Conge where numDemConge != (select a.id.numDemConge from Agtraitedemconge as a)");
    conge=(List<Conge>)query.list();
    session.beginTransaction().commit();
    return conge;
     }
     catch(Exception e){
           e.printStackTrace();
           System.out.println("Echec de la requête HQL");
           return null;
    } 
     finally
        {
            session.close();
        }
     
 }
 
  public DataModel<Agtraitedemconge> returnDemATraiteParDD(Authentification authentification){
      
     List<Agtraitedemconge>agTraiteDemCong =new ArrayList();
     String codeDirect=authentification.getAgent().getService().getDirection().getCodeDirect();
     System.out.println("La valeur de codeDirect est: "+ codeDirect);
     session=HibernateUtil.getSessionFactory().openSession();
     try { 
    session.beginTransaction();
    Query query = session.createQuery("from Agtraitedemconge where agent.matricAg='DEFAULT' and conge.agent.service.direction.codeDirect=:codeDirect");
    query.setParameter("codeDirect", codeDirect);
    agTraiteDemCong=(List<Agtraitedemconge>)query.list();
    session.beginTransaction().commit();
    agTraiteDemConge.setWrappedData(agTraiteDemCong);
    System.out.println("Le nombre de demandes à traiter est: "+agTraiteDemConge.getRowCount());
    return agTraiteDemConge;
     }
     catch(Exception e){
           e.printStackTrace();
           System.out.println("Echec de la requête HQL");
           return null;
    } 
     finally
        {
            session.close();
        }
     
 }
  
    public DataModel<Agtraitedemconge> returnDemATraiteParDRH(){
        
     List<Agtraitedemconge>agTraiteDemCong =new ArrayList();   
     session=HibernateUtil.getSessionFactory().openSession();
     try { 
    session.beginTransaction();
    Query query = session.createQuery("from Agtraitedemconge where avis=true and aTraite=1");
    agTraiteDemCong=(List<Agtraitedemconge>)query.list();
    session.beginTransaction().commit();
    agTraiteDemConge.setWrappedData(agTraiteDemCong);
        return agTraiteDemConge;
     }
     catch(Exception e){
           e.printStackTrace();
           System.out.println("Echec de la requête HQL");
           return null;
    } 
     finally
        {
            session.close();
        }
     
 }
    
public DataModel<Agtraitedemconge> returnDemATraiteParDG(){
    
     List<Agtraitedemconge>agTraiteDemCong =new ArrayList();     
     session=HibernateUtil.getSessionFactory().openSession();
     try { 
    session.beginTransaction();
    Query query = session.createQuery("from Agtraitedemconge where avis=1 and aTraite=2");
    agTraiteDemCong=(List<Agtraitedemconge>)query.list();
    session.beginTransaction().commit();
    agTraiteDemConge.setWrappedData(agTraiteDemCong);
    return agTraiteDemConge;
     }
     catch(Exception e){
           e.printStackTrace();
           System.out.println("Echec de la requête HQL");
           return null;
    } 
     finally
        {
            session.close();
        }
     
 }    
 
public void enregistreTraitement(Agtraitedemconge agTraiteDemCong){

    session=HibernateUtil.getSessionFactory().openSession();
     try { 
    session.beginTransaction();
    session.save(agTraiteDemCong);
    session.beginTransaction().commit();
        
     }
     catch(Exception e){
           e.printStackTrace();
           System.out.println("Echec de la requête HQL");
           
    } 
     finally
        {
            session.close();
        }

}

public void miseAJourTraitement(Agtraitedemconge agTraiteDemConge){
    
        String numDemConge=agTraiteDemConge.getConge().getNumDemConge();
        String matricAg=connexionBean.getAgent().getMatricAg();
        String commentaire=agTraiteDemConge.getCommentaire();
        int atraite=agTraiteDemConge.getAtraite()+1;
        boolean avis=agTraiteDemConge.getAvis();
        Calendar c = Calendar.getInstance ();
        Date dateTraitement = c.getTime ();
        
        session=HibernateUtil.getSessionFactory().openSession();
        try { 
        session.beginTransaction();    
        String hql = "update Agtraitedemconge AgT set AgT.id.matricAg=:matricAg, AgT.commentaire=:commentaire,AgT.dateTraitement=:dateTraitement, AgT.atraite=:atraite, AgT.avis=:avis where AgT.id.numDemConge=:numDemConge";
        Query query = session.createQuery(hql);
        
        query.setParameter("matricAg", matricAg);
        query.setParameter("commentaire", commentaire);
        query.setParameter("dateTraitement", dateTraitement);
        query.setParameter("atraite",atraite);
        query.setParameter("numDemConge",numDemConge);
        query.setParameter("avis",avis);
        System.out.println("La nouvelle valeur de avis" + avis);
        
        query.executeUpdate();
        
             }
     catch(Exception e){
           e.printStackTrace();
           System.out.println("Echec de la requête HQL");
           
    } 
     finally
        {
            session.close();
        }
    
}
public DataModel<Agtraitedemconge> returnReponseDeTraitement(Authentification authentification){
    
    List<Agtraitedemconge>agTraiteDemCong =new ArrayList(); 
    String matricAg=authentification.getAgent().getMatricAg();
    session=HibernateUtil.getSessionFactory().openSession();
        try { 
         session.beginTransaction();
         String hql = "from Agtraitedemconge where conge.agent.matricAg=:matricAg"
                      + " and ((avis=false or atraite=3)and conge.numDemConge not in "
                      + "(select Ar.numDemConge from ArchiveConge Ar)) ";
        Query query = session.createQuery(hql);
        
        query.setParameter("matricAg", matricAg);        
        agTraiteDemCong=(List<Agtraitedemconge>)query.list();
        session.beginTransaction().commit();
        agTraiteDemConge.setWrappedData(agTraiteDemCong);
        return agTraiteDemConge;
        
        
     }
     catch(Exception e){
           e.printStackTrace();
           System.out.println("Echec de la requête HQL");
           return null;
    } 
     finally
        {
            session.close();
        }

}

public void enregistreDemVu(ArchiveConge archiveConge){
        
        session=HibernateUtil.getSessionFactory().openSession();
        try { 
         session.beginTransaction();
         session.save(archiveConge);
         session.beginTransaction().commit();
         
        }
     catch(Exception e){
           e.printStackTrace();
           System.out.println("Echec de la requête HQL");
           
    } 
     finally
        {
            session.close();
        }
    }

}
