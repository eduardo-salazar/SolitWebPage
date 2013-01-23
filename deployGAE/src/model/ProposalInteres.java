package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the proposal_intereses database table.
 * 
 */
@Entity
@Table(name="proposal_intereses")
public class ProposalInteres implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProposalInteresPK id;

	public ProposalInteres() {
	}

	public ProposalInteresPK getId() {
		return this.id;
	}

	public void setId(ProposalInteresPK id) {
		this.id = id;
	}

}