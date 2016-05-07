import javax.persistence.*;


@Entity
@Table(name = "Tb_Model", schema = "dbo", catalog = "ZTP_zad7")
public class TbModelEntity {
    private int id;
    private String model;

    @Id
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "model", nullable = true, length = 50)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbModelEntity that = (TbModelEntity) o;

        if (id != that.id) return false;
        if (model != null ? !model.equals(that.model) : that.model != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }
}