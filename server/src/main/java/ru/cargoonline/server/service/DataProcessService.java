package ru.cargoonline.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cargoonline.common.context.LogDataSyncAction;
import ru.cargoonline.server.model.DbUser;
import ru.cargoonline.server.model.ProcessRequest;
import ru.cargoonline.server.repository.DataProcessRepository;

import java.util.List;


/**
 * Data processing service (to tune transaction management)
 */
@Service
public class DataProcessService {

    @Autowired
    DataProcessRepository dataProcessRepository;

    /**
     * Store list of db users
     * @param users db users list
     */
    @LogDataSyncAction
    @Transactional(rollbackFor = Exception.class)        // rollback on any checked/unchecked exception
    public int batchDataProcessing(List<DbUser> users, String requestId, int offset)
    {
        if (users == null) throw new IllegalArgumentException("Users list must not be null");
        if (requestId == null) throw new IllegalArgumentException("Request must not be null");
        if (offset < 0) throw new IllegalArgumentException("Offset must be non-negative");

        ProcessRequest request = dataProcessRepository.findOrCreateProcessRequest(requestId);

        int total = users.size();
        int currentOffset = request.getOffset();

        if ((currentOffset < offset) || (currentOffset >= offset + total))
        {
            return 0;         // todo probably some IllegalStateException could be useful for second condition but we just skip all illegal data
        }

        int delta = currentOffset - offset;

        dataProcessRepository.batchCreateDbUsers(delta > 0 ? users.subList(delta, total) : users);

        request.setOffset(offset + total);
        dataProcessRepository.updateProcessRequest(request);

        return total - delta;
    }
}
