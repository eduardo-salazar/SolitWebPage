package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the proposal_intereses database table.
 * 
 */
@Embeddable
public class ProposalInteresPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idProposal;

	private int idInteres;

	public ProposalInteresPK() {
	}
	public int getIdProposal() {
		return this.idProposal;
	}
	public void setIdProposal(int idProposal) {
		this.idProposal = idProposal;
	}
	public int getIdInteres() {
		return this.idInteres;
	}
	public void setIdInteres(int idInteres) {
		this.idInteres = idInteres;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProposalInteresPK)) {
			return false;
		}
		ProposalInteresPK castOther = (ProposalInteresPK)other;
		return 
			(this.idProposal == castOther.idProposal)
			&& (this.idInteres == castOther.idInteres);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idProposal;
		hash = hash * prime + this.idInteres;
		
		return hash;
	}
}