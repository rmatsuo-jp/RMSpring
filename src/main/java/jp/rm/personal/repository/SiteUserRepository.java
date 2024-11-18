package jp.rm.personal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.rm.personal.model.SiteUser;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
	Optional<SiteUser> findByUsername(String username);
}
