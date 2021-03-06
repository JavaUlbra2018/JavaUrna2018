package br.edu.ulbra.election.candidate.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Candidate {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long partyId;

    @Column(nullable = false)
    private Long electionId;
    
    @Column(nullable = false)
    private Long numberElection;
    


    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public Long getElectionId() {
		return electionId;
	}

	public void setElectionId(Long electionId) {
		this.electionId = electionId;
	}

	public Long getNumberElection() {
		return numberElection;
	}

	public void setNumberElection(Long numberElection) {
		this.numberElection = numberElection;
	}
	
}
