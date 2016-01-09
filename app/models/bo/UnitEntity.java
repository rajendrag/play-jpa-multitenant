
package models.bo;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Data object for Unit entity record
 *
 * @author <a href="santhosh.g@leantaas.com">Santhosh Gandhe</a>
 * @version $Revision: 1.0 $, $Date: Apr 27, 2015
 */

@Entity
@Table(name = "iq_unit_mstr")
@Audited
public class UnitEntity extends PrimaryBaseDo  {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "iq_unit_mstr_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    
    @Column(name = "unit_name")
    private String unitName;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "nof_chairs")
    private Integer nofChairs;
    
    @Column(name = "nof_beds")
    private Integer nofBeds;
    
    @Column(name = "appt_start_time")
    private String apptStartTime;
    
    @Column(name = "nof_appt_start_with_in_hour")
    private Integer nofApptStartWithInHour;
    
    @Column(name="nof_appt_discharge_with_in_hour")
    private Integer nofApptDischargeWithInHour;
    
    @Column(name="owner")
    private String owner;
    
    public UnitEntity(){
        nofApptDischargeWithInHour=0;
        nofApptStartWithInHour=0;
        nofBeds=0;
        nofChairs=0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNofChairs() {
        return nofChairs;
    }

    public void setNofChairs(Integer nofChairs) {
        this.nofChairs = nofChairs;
    }

    public Integer getNofBeds() {
        return nofBeds;
    }

    public void setNofBeds(Integer nofBeds) {
        this.nofBeds = nofBeds;
    }

    public String getApptStartTime() {
        return apptStartTime;
    }

    public void setApptStartTime(String apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    public Integer getNofApptStartWithInHour() {
        return nofApptStartWithInHour;
    }

    public void setNofApptStartWithInHour(Integer nofApptStartWithInHour) {
        this.nofApptStartWithInHour = nofApptStartWithInHour;
    }

    public Integer getNofApptDischargeWithInHour() {
        return nofApptDischargeWithInHour;
    }

    public void setNofApptDischargeWithInHour(Integer nofApptDischargeWithInHour) {
        this.nofApptDischargeWithInHour = nofApptDischargeWithInHour;
    }

  
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getCapacity(){
        return (nofChairs == null || nofBeds == null) ? null : (nofChairs + nofBeds); 
    }

}
