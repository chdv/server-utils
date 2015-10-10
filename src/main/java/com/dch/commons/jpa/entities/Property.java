package com.dch.commons.jpa.entities;

import javax.persistence.*;

/**
 * Created by dcherdyntsev on 05.08.2015.
 */
@Entity
@Table(name = "properties", indexes = {@Index(name = "properties_code_index",  columnList="code", unique = true)})
@NamedQueries({
        @NamedQuery(name= "findPropertyByKey", query = "SELECT en FROM Property en WHERE en.key = :key")
})
public class Property {

    @Id
    @Column(name="id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="code")
    private String key;

    @Column(name="value")
    private String value;

    @Column(name="description")
    private String description;

    @Column(name="module")
    private String module;

    public Property() {

    }

    public Property(Integer id, String key, String value, String description, String module) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.description = description;
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
