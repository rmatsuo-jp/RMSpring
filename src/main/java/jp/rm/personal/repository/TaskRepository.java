package jp.rm.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.rm.personal.model.Task;

/*
 * JpaRepository　: CRUDに必要なメソッドを生成
 */

public interface TaskRepository extends JpaRepository<Task, Long> {

}