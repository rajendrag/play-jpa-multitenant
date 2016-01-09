
package models.bo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Base data object for all the 
 * 
 * @author <a href="santhosh.g@leantaas.com">Santhosh Gandhe</a>
 * @version $Revision: 1.0 $, $Date: Apr 27, 2015
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@MappedSuperclass
public abstract class BaseDo implements Serializable {
   
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Column(name="created_by")
    protected String createdBy;
    
    @Column(name="modified_by")
    protected String modifiedBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_datetime")
    protected Date createdDateTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_datetime")
    @RevisionTimestamp
    protected Date modifiedDateTime;
    
    /**
     *  This version field is used only for Optimistic locking.
     *  Hibernate will take care of setting its value.
     *  We never need to set/get  its value in our code.
     */
    @Version
    @RevisionNumber
    protected Integer version;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Date getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(Date modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public static <T extends BaseDo> Set<T> findAll(Class<T> t) {
        Query query = JPA.em().createQuery("SELECT t FROM "+ t.getName()+" t");
        return new LinkedHashSet<>(query.getResultList());
    }

    public static <T extends BaseDo> T find(Class<T> t, Object primaryKey) {
        return JPA.em().find(t, primaryKey);
    }
}
