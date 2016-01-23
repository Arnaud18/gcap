/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementationdao;

import hibernateUtil.HibernateUtil;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Arnaud
 */
public class statistiqueAbsDao {
    private Session session=null;

    public statistiqueAbsDao() {
    }
    public int returnNombreAbsService(String codeService){
        int nbAbsent=0;
        Date dateFin;
        Calendar c = Calendar.getInstance ();
        dateFin = c.getTime ();
        Integer nbreAbsent=0;
        session=HibernateUtil.getSessionFactory().openSession();
     try { 
    session.beginTransaction();
    String requete="select count( cong.numDemConge) from Conge cong, Agtraitedemconge agt where cong.numDemConge=agt.conge.numDemConge "
            + "and cong.agent.service.codeService=:codeService and cong.dateFin>:dateFin and agt.avis=true and agt.atraite=3";
    Query query = session.createQuery(requete);
    query.setParameter("dateFin", dateFin);
    query.setParameter("codeService", codeService);
    nbreAbsent=(Integer) query.uniqueResult();
    nbAbsent=nbreAbsent.intValue();
      return nbAbsent;
     }
     catch(Exception e){
           e.printStackTrace();
           System.out.println("Echec de la requête HQL");
           return 0;
    }
        
    }
}
