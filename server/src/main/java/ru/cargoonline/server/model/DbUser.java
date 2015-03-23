package ru.cargoonline.server.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "db_users")
public class DbUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String code;
    private String username;
    private String firstname;
    private String lastname;

    @Range(min = 0, max = 120)
    private int age;

    private Date birth;

    @Embedded
    private Address address;

    byte flags;

    public DbUser() {
    }

    public DbUser(String code, String username, String firstname, String lastname, int age, Date birth, Address address, byte flags) {
        this.code = code;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.birth = birth;
        this.address = address;
        this.flags = flags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DbUser dbUser = (DbUser) o;

        if (age != dbUser.age) return false;
        if (flags != dbUser.flags) return false;
        if (address != null ? !address.equals(dbUser.address) : dbUser.address != null) return false;
        if (birth != null ? !birth.equals(dbUser.birth) : dbUser.birth != null) return false;
        if (code != null ? !code.equals(dbUser.code) : dbUser.code != null) return false;
        if (firstname != null ? !firstname.equals(dbUser.firstname) : dbUser.firstname != null) return false;
        if (id != null ? !id.equals(dbUser.id) : dbUser.id != null) return false;
        if (lastname != null ? !lastname.equals(dbUser.lastname) : dbUser.lastname != null) return false;
        if (username != null ? !username.equals(dbUser.username) : dbUser.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (int) flags;
        return result;
    }
}
