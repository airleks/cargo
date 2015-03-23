package ru.cargoonline.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cargoonline.server.model.DbUser;

import javax.persistence.Table;

/**
 * DbUser data access wrapper
 */
@Repository
@Table(name = "db_users")
public interface DbUserRepository extends JpaRepository<DbUser, Long>
{
}
