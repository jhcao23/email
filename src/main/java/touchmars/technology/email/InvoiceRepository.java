package touchmars.technology.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	public Invoice findByClientNo(String clientNo);
	
	public Invoice findByClientNoAndTask(String clientNo, Task task);
}
