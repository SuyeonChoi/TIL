package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Locker {

	@Id @GeneratedValue
	private Long id;

	private String name;

	@OneToOne(mappedBy = "locker")
	private Member member; // 읽기 전용
}
