package dto;

import domain.Child;

import java.util.Objects;

public class ChildDTO {
    private Long cnp;
    private String name;
    private Integer age;

    public ChildDTO(Long cnp, String name, Integer age) {
        this.cnp = cnp;
        this.name = name;
        this.age = age;
    }

    public Long getCnp() {
        return cnp;
    }

    public void setCnp(Long cnp) {
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ChildDTO{" +
                "cnp=" + cnp +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
