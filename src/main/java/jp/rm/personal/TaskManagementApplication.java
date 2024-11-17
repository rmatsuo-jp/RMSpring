package jp.rm.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @SpringBootApplication : 以下3つのアノテーションの記述と同義
 * - @SpringBootConfiguration : @Configuration(構成クラス)を提供する
 * - @EnableAutoConfiguration : 自動構成機能を有効にする
 * - @ComponentScan : @Component等のクラスをスキャンして利用可能にする
 */

@SpringBootApplication
public class TaskManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}

}
