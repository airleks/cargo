package ru.cargoonline.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cargoonline.common.context.LogDataSyncAction;
import ru.cargoonline.common.generated.ObjectFactory;
import ru.cargoonline.common.generated.Xmlusers;
import ru.cargoonline.server.service.DataProcessService;
import ru.cargoonline.server.util.DataConverter;

import java.util.Map;

@RestController
public class DataSyncController {

    Logger logger = LoggerFactory.getLogger(DataSyncController.class);

    @Autowired
    private DataProcessService dataProcessService;

    @LogDataSyncAction
    @RequestMapping(value = "{file}/{offset}", method = RequestMethod.POST, produces = "application/xml")
    public Xmlusers createBatch(
                         @RequestBody Xmlusers users,
                         @PathVariable("file") String file,
                         @PathVariable("offset") Integer offset
    )
    {
        logger.debug("Processing file={}, offset={}", file, offset);
        dataProcessService.batchDataProcessing(DataConverter.xmlUserList2DbUserList(users.getUsers()), file, offset);
        return users;
    }

    @RequestMapping(method=RequestMethod.GET, produces = "application/xml")
    public Xmlusers test()
    {
        return new ObjectFactory().createXmlusers();
    }

//    @Autowired
//    DbUserRepository dbUserRepository;
//
//    @Autowired
//    ProcessRequestRepository processRequestRepository;
//
//    @Deprecated
//    @RequestMapping(method=RequestMethod.GET, produces = "application/json")
//    public Map test()           // todo temporary: to check db
//    {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("dbu", String.valueOf(dbUserRepository.count()));
//        map.put("pr", String.valueOf(processRequestRepository.count()));
//
//        return map;
//    }
}
