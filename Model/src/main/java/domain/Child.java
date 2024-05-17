package domain;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.Set;

@jakarta.persistence.Entity
@Table(name = "CHILDREN")
public class Child extends Entity<Long>{
    private Long cnp;
    private String name;
    private Integer age;

    @OneToMany(mappedBy = "child")
    private Set<Enrollment> enrollments;

    public Child(Long cnp, String name, Integer age) {
        this.cnp = cnp;
        this.name = name;
        this.age = age;
    }

    public Child() {

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

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
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
