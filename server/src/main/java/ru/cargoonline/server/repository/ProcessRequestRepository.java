package ru.cargoonline.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cargoonline.server.model.ProcessRequest;

import javax.persistence.Table;

/**
 * ProcessRequest data access wrapper
 */
@Repository
@Table(name = "process_requests")
public interface ProcessRequestRepository extends JpaRepository<ProcessRequest, String>
{
}
