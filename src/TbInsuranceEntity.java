import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Basic;
import java.sql.Timestamp;

/**
 * Client application Class.
 *
 * @author Konrad Szwedo
 * @version 0.7L
 */
@Entity
@Table(name = "TB_Insurance")
public class TbInsuranceEntity {
    private int id;
    private int customerId;
    private int modelId;
    private Timestamp dateFrom;
    private Timestamp dateTo;

    /**
     * Getter for insurance ID.
     *
     * @return insurance id
     */
    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    /**
     * Setter for insurance ID.
     *
     * @param id insurance ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for customer ID.
     *
     * @return customer ID
     */
    @Basic
    @Column(name = "customerId")
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter for customer ID.
     *
     * @param customerId customer ID, foreign key from TbCustomerEntity
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for Car model ID.
     *
     * @return int car model ID
     */
    @Basic
    @Column(name = "modelId")
    public int getModelId() {
        return modelId;
    }

    /**
     * Setter for car model.
     *
     * @param modelId car model ID, foreign key from TbModelEntity
     */
    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    /**
     * Getter for date from field.
     *
     * @return Date date from insurance valid
     */
    @Basic
    @Column(name = "dateFrom")
    public Timestamp getDateFrom() {
        return dateFrom;
    }

    /**
     * Setter for dateFrom insurance.
     *
     * @param dateFrom Date object to set
     */
    public void setDateFrom(Timestamp dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Getter for date to insurance valid.
     *
     * @return dateTo Date object
     */
    @Basic
    @Column(name = "dateTo")
    public Timestamp getDateTo() {
        return dateTo;
    }

    /**
     * Setter for dateTo field.
     *
     * @param dateTo Date object to set
     */
    public void setDateTo(Timestamp dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @param o second object to compare
     * @return true if equals, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbInsuranceEntity that = (TbInsuranceEntity) o;

        return id == that.id && customerId == that.customerId && modelId ==
                that.modelId && (dateFrom != null ?
                dateFrom.equals(that.dateFrom) : that.dateFrom == null &&
                (dateTo != null ? dateTo.equals(that.dateTo) :
                        that.dateTo == null));

    }

    /**
     * Hashcode of object.
     *
     * @return object hashcode.
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + customerId;
        result = 31 * result + modelId;
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        return result;
    }
}
