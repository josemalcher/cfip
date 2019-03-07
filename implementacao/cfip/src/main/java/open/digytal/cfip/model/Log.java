package open.digytal.cfip.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Log {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_inclusao",nullable=false)
	private Date dataInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	private Date dataAlteracao;
	
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
}
