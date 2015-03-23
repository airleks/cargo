package ru.cargoonline.server.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.cargoonline.server.model.DbUser;
import ru.cargoonline.server.model.ProcessRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class DataProcessRepository
{
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Batch create of db users collection.
     * @param users users collection
     */
    public void batchCreateDbUsers(List<DbUser> users)
    {
        for (int counter = 0; counter < users.size(); counter++)
        {
            entityManager.persist(users.get(counter));

            if (counter % 50 == 0)
            {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    /**
     * Find exsisting process request object or create new
     * @param id process request id
     * @return data object
     */
    public ProcessRequest findOrCreateProcessRequest(String id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("Process Request id is null");
        }

        ProcessRequest request = entityManager.find(ProcessRequest.class, id);

        if (request == null)
        {
            request = new ProcessRequest(id);
            entityManager.persist(request);
//            entityManager.refresh(request);
        }

        return request;
    }

    /**
     * Update process request
     * @param request process request object
     * @return refreshed data object
     */
    public ProcessRequest updateProcessRequest(ProcessRequest request)
    {
        return entityManager.merge(request);
    }
}
