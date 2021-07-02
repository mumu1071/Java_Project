package util.bson;
import java.util.List;

public class StudentModel {
    private String id;

    @Column(name = "catalog_id")
    private String catalogId;
    private int item_code;
    @NotColumn
    private String product_id;
    private List<String> listStr;
    private DogModel dogModel;


    public StudentModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DogModel getDogModel() {
        return dogModel;
    }

    public void setDogModel(DogModel dogModel) {
        this.dogModel = dogModel;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public List<String> getListStr() {
        return listStr;
    }

    public void setListStr(List<String> listStr) {
        this.listStr = listStr;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "id='" + id + '\'' +
                ", catalogId='" + catalogId + '\'' +
                ", item_code=" + item_code +
                ", product_id='" + product_id + '\'' +
                ", listStr=" + listStr +
                ", dogModel=" + dogModel +
                '}';
    }
}
