package touchmars.technology.email;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="invoice_line_item")
public class InvoiceLineItem {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)	
	private Long id;
	
	@Column(name = "doc_id")
	private String docId;		//doc id
	
	@Column(name = "doc_dt")
	@Temporal(TemporalType.DATE)	
	private Date docDt;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "balance", precision = 22, scale = 0)
	private Double balance;
	
	@Column(name = "range1", precision = 22, scale = 0)
	private Double range1;
	
	@Column(name = "range2", precision = 22, scale = 0)
	private Double range2;
	
	@Column(name = "range3", precision = 22, scale = 0)
	private Double range3;
	
	@Column(name = "range4", precision = 22, scale = 0)
	private Double range4;
	
	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="invoice_id", nullable=false)
	@JsonIgnore	
	private Invoice invoice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getRange1() {
		return range1;
	}
	public void setRange1(Double range1) {
		this.range1 = range1;
	}
	public Double getRange2() {
		return range2;
	}
	public void setRange2(Double range2) {
		this.range2 = range2;
	}
	public Double getRange3() {
		return range3;
	}
	public void setRange3(Double range3) {
		this.range3 = range3;
	}
	public Double getRange4() {
		return range4;
	}
	public void setRange4(Double range4) {
		this.range4 = range4;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public Date getDocDt() {
		return docDt;
	}
	public void setDocDt(Date docDt) {
		this.docDt = docDt;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	

}
