package util.bson;

public class DogModel {

    private String name;

    public DogModel() {
    }

    public DogModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DogModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
