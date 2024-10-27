package int202.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees", schema = "classicmodels")
public class Employee {
    @Id
    @Column(name = "employeeNumber", nullable = false)
    private Integer id;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "extension", nullable = false, length = 10)
    private String extension;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Transient
    private Integer mgrID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportsTo")
    private Employee reportsTo;

    @Column(name = "jobTitle", nullable = false, length = 50)
    private String jobTitle;

    @Transient
    private String officeCodeString;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "officeCode", nullable = false)
    private Office officeCode;

}