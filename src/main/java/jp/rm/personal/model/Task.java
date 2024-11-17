package jp.rm.personal.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data	//lombokによってgetter, setterなどを生成
@Entity	//EntityとしてDIされる
public class Task {
	
	/*
	 * @GeneratedValue : データベースのid列を用いてキーを自動採番する
	 * GenerationType.IDENTITY : Webからデータを挿入するときに、データベースとの採番を一致させる
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 40)
	private String name;
	
	private Boolean isFinished;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate startDate;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate endDate;
}