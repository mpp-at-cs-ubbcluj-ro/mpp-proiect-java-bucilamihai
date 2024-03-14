package domain;

import java.util.Objects;

public class Child extends Entity{
    private Long cnp;
    private String name;
    private Integer age;

    public Child(Long cnp, String name, Integer age) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Child child)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(cnp, child.cnp) && Objects.equals(name, child.name) && Objects.equals(age, child.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cnp, name, age);
    }

    @Override
    public String toString() {
        return "Child{" +
                "cnp=" + cnp +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}