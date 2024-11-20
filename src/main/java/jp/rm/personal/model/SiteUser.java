package jp.rm.personal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import jp.rm.personal.util.Authority;
import jp.rm.personal.validator.UniqueLogin;
import lombok.Data;

@Data
@Entity
public class SiteUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=2, max=20)
	@UniqueLogin	//自作バリデーション
	private String username;
	
	@Size(min=4, max=255)
	private String password;
	
	@Email
	private String email;
	
	private boolean admin;
	private Authority authority;
}
