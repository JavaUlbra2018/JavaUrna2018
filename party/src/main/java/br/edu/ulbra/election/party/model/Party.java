package br.edu.ulbra.election.party.model;

import javax.persistence.*;

@Entity
public class Party {

    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

	@Column
    private String code;
    
    @Column
    private String name;

    @Column
    private Integer number;


    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
}
