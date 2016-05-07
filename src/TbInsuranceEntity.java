import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "TB_Insurance", schema = "dbo", catalog = "ZTP_zad7")
public class TbInsuranceEntity {
    private int id;
    private Timestamp dateFrom;
    private Timestamp dateTo;

    @Id
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dateFrom", nullable = false)
    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {

        this.dateFrom = dateFrom;
    }

    @Basic
    @Column(name = "dateTo", nullable = false)
    public Timestamp getDateTo() {
        return dateTo;
    }

    public void setDateTo(Timestamp dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbInsuranceEntity that = (TbInsuranceEntity) o;

        if (id != that.id) return false;
        if (dateFrom != null ? !dateFrom.equals(that.dateFrom) : that.dateFrom != null)
            return false;
        if (dateTo != null ? !dateTo.equals(that.dateTo) : that.dateTo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        return result;
    }
}
