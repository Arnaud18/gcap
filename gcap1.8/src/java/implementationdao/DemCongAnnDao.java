/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementationdao;
import hibernateUtil.HibernateUtil;
import java.util.Date;
import javax.faces.context.FacesContext;
import managedbean.ConnexionManagedBean;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import pojoandmapping.*;

/**
 *
 * @author Arnaud
 */
public class DemCongAnnDao {
private Object object;
private Conge conge;
private Typeconge typeConge;
private Session session=null;
FacesContext fc = FacesContext.getCurrentInstance();
ConnexionManagedBean connexionBean = (ConnexionManagedBean) fc.getExternalContext().getSessionMap().get("connexionManagedBean");

     
 public void DemCongAnnDao(){
   conge=new Conge();
   typeConge=new Typeconge();
 }
 public int newCongAnn(Conge conge,Agtraitedemconge agTraiteDemCong){
     session=HibernateUtil.getSessionFactory().openSession();
try {
    String numDemConge=conge.getNumDemConge();
    String matricAg=conge.getAgent().getMatricAg();
    session.beginTransaction();
    session.save(conge);
    session.save(agTraiteDemCong);
    session.flush();
    session.beginTransaction().commit();
    session.close();
    session=HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    String requete="update Conge cong set cong.agent.matricAg=:matricAg where cong.numDemConge=:numDemConge";
    Query query=session.createQuery(requete);
    query.setParameter("matricAg", matricAg);
    query.setParameter("numDemConge", numDemConge);
    query.executeUpdate();
    System.out.println("MATRICAG= " +matricAg);
    session.beginTransaction().commit();
    session.close();
    return 1;
}
 catch(Exception e){
     e.printStackTrace();
    return 0;
     
}
 }
 
 
 public Conge returnNewNumCong()
 {
     System.out.println("On est dans le DAO");
     String lastNumConge=null;
     String anneeEnCours=null;
     int nbDem=0;
     String newNumConge=null;
    try 
    {
        System.out.println("debut du try");
    session=HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Query query = session.createQuery("from Conge as Cong where Cong.numDemConge=(select max(a.numDemConge) from Conge as a)");
    conge = (Conge) query.uniqueResult();
    session.beginTransaction().commit();
    session.close();
    return conge;
    
}
 catch(Exception e){
     System.out.println("Echec de la requête!");
     e.printStackTrace();
     System.out.println("Fin du DAO2");
    return null;
     
}

    // return "2014-3";
 } 
 public Typeconge returnTypeConge(){
     session=HibernateUtil.getSessionFactory().openSession();
     session.beginTransaction();
     Query query = session.createQuery("from Typeconge where codeTypeConge='CA'");
     typeConge=(Typeconge)query.uniqueResult();
     session.beginTransaction().commit();
     session.close();
      return typeConge;  
 }
 public int returnNombreDeJourCong(Authentification authentification){
     
     String matricAg=authentification.getIdCompt();
     int nbreJrCong=0;
     int nbreAbsDeductTotal=0;
     Integer nbreAbsDeduct=0;
     int nbreJourNormalCong=30;
    
     Integer nbreAnneeService=0;
     int nbreAnneeServiceInt=0;
     Integer absNonJustif=0;
     Date dateDemDernierCong;
     try 
    {     session=HibernateUtil.getSessionFactory().openSession();
     session.beginTransaction();
     // Recupérer la dernière demande de congé
     String requete="from Conge as Cong where ( Cong.numDemConge=(select max(a.numDemConge) from Conge as a where a.numDemConge in (select numDemConge from Conge where matricAg=:matricAg)))";
     Query query0 = session.createQuery(requete);
     query0.setParameter("matricAg", matricAg);
     System.out.println("La requête 0 marche");
     conge= (Conge)query0.uniqueResult();
     dateDemDernierCong=conge.getDateDem();
     System.out.println("La date de dernière demande de congé est: "+dateDemDernierCong);
     
     //Recupérer le nombre jour d'absences non justifiées
     Query query1 = session.createQuery("select SUM(absence.nbJourDeduct) from Sabsente as absence where matricAg=:matricAg and dateDebut >=:dateDemDernierCong");
     query1.setDate("dateDemDernierCong", dateDemDernierCong);
     query1.setParameter("matricAg", matricAg);
     System.out.println("La requête 1 marche");
     absNonJustif=(Integer)query1.uniqueResult();
     
     //Recupérer le nombre de jour déductible pour les absences déductibles
     String requete2="select DATEDIFF(dateRetourEff,dateFinAbs) from Autorisationabsence where matricAg=:matricAg and codeTypeAbs in (select codeTypeAbs from Typeabsence where absDeduct=1)";
     Query query3=session.createQuery(requete2);
     query3.setParameter("matricAg", matricAg);
     System.out.println("La requête 3 marche");
     nbreAbsDeduct=(Integer)query3.uniqueResult();
     
     //System.out.println("\nLe nombre d'absences non justifiées est: "+absNonJustif.intValue());
     //nbreAbsDeductTotal = nbreAbsDeduct.intValue()+ absNonJustif.intValue();
     System.out.println("Fin de la requête 3 marche");
     //Retourne le nombre de jour de congé normal pour l'agent en Question
     requete="select DATEDIFF(sysdate(),datePriseService) from Agent where matricAg=:matricAg";
     System.out.println("Debut de la requête 4 marche");
     Query query4=session.createQuery(requete);
     query4.setParameter("matricAg", matricAg);
     nbreAnneeService=(Integer)query4.uniqueResult();
     nbreAnneeServiceInt=nbreAnneeService.intValue()/366;
     System.out.println("La requête 4 marche");
     
     if(nbreAnneeServiceInt>=30) nbreJourNormalCong=36;
      else{
      if(nbreAnneeServiceInt>=25) nbreJourNormalCong=34;
      if(nbreAnneeServiceInt<25 && nbreAnneeServiceInt >=20) nbreJourNormalCong=32;
      
      nbreAbsDeductTotal=nbreAbsDeduct;
      nbreJrCong=nbreJourNormalCong-nbreAbsDeductTotal;
      }
     session.beginTransaction().commit();
     session.close();
     System.out.println("Le nombre de jour de congé est: "+nbreJrCong);
      return nbreJrCong;
 
    }
     catch(Exception e){
     System.out.println("Echec de la requête!!");
     return 0;     
}

}
}
