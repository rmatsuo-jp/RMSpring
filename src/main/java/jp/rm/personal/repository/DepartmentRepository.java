package jp.rm.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.rm.personal.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
