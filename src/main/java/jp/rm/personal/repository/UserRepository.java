package jp.rm.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.rm.personal.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
