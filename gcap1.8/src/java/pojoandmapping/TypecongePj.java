package pojoandmapping;
// Generated 23 juin 2014 16:20:08 by Hibernate Tools 3.2.1.GA



/**
 * TypecongePj generated by hbm2java
 */
public class TypecongePj  implements java.io.Serializable {


     private TypecongePjId id;
     private Typeconge typeconge;
     private Piecesjustif piecesjustif;

    public TypecongePj() {
    }

    public TypecongePj(TypecongePjId id, Typeconge typeconge, Piecesjustif piecesjustif) {
       this.id = id;
       this.typeconge = typeconge;
       this.piecesjustif = piecesjustif;
    }
   
    public TypecongePjId getId() {
        return this.id;
    }
    
    public void setId(TypecongePjId id) {
        this.id = id;
    }
    public Typeconge getTypeconge() {
        return this.typeconge;
    }
    
    public void setTypeconge(Typeconge typeconge) {
        this.typeconge = typeconge;
    }
    public Piecesjustif getPiecesjustif() {
        return this.piecesjustif;
    }
    
    public void setPiecesjustif(Piecesjustif piecesjustif) {
        this.piecesjustif = piecesjustif;
    }




}


