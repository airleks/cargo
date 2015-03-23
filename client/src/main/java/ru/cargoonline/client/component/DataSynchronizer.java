package ru.cargoonline.client.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.cargoonline.common.context.LogDataSyncAction;
import ru.cargoonline.common.generated.Address;
import ru.cargoonline.common.generated.ObjectFactory;
import ru.cargoonline.common.generated.Xmluser;
import ru.cargoonline.common.generated.Xmlusers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Data synchronization component
 */
@Component
public class DataSynchronizer
{
    Logger logger = LoggerFactory.getLogger(DataSynchronizer.class);

    @Value("${cargo.client.data.dir}")
    private String dataDir;

    @Value("${cargo.client.batch.size}")
    private int batchSize;

    @Value("${cargo.client.host}")
    private String host;

    @Value("${cargo.client.port}")
    private int port;

    private ObjectFactory objectFactory = new ObjectFactory();

    /**
     * Scheduled data synchronization job.
     */
    @Scheduled(fixedRate = 10000)
    public void syncData()
    {
        logger.debug("Start data synchronization from '{}'", dataDir);

        File dir = new File(dataDir);

        if (!dir.exists() || !dir.isDirectory())
        {
            throw new IllegalStateException(String.format("Can't find valid directory in '%s'", dataDir));
        }

        File[] files = dir.listFiles();
        Collection<Future<Integer>> jobs = new ArrayList<Future<Integer>>(files.length);

        for (File file : files)
        {
            try
            {
                jobs.add(sendData(file));
            }
            catch (Throwable t)
            {
                logger.error("File processing was terminated", t);
            }
        }

        // wait for jobs to finish
        for (Future<Integer> job : jobs)
        {
            try
            {
                job.get();
            }
            catch (Throwable t)
            {
                logger.error("File processing was terminated", t);
            }
        }

        logger.debug("Finished data synchronization from '{}'", dataDir);
    }

    /**
     * Send data to server
     */
    @Async
    @LogDataSyncAction
    public Future<Integer> sendData(File file)
    {
        if (file == null)
        {
            throw new IllegalArgumentException("File argument is null");
        }

        try
        {
            JAXBContext context = JAXBContext.newInstance(Xmlusers.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Xmlusers source = (Xmlusers) unmarshaller.unmarshal(new FileReader(file));
            int usersNum = source.getUsers().size();

            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> params = new HashMap<String, String>();
            FileTime creationTime = Files.readAttributes(file.toPath(), BasicFileAttributes.class).creationTime();
            params.put("host", host);
            params.put("port", String.valueOf(port));
            params.put("file", URLEncoder.encode(file.getName(), "UTF-8") + "T" + creationTime.toMillis());

            String serviceLocation = "http://{host}:{port}/{file}/{offset}";

            for (int counter = 0; counter < usersNum; counter += batchSize)
            {
                Xmlusers data = objectFactory.createXmlusers();
                data.getUsers().addAll(source.getUsers().subList(counter, Math.min(counter + batchSize, usersNum)));
                params.put("offset", String.valueOf(counter));
                restTemplate.postForObject(serviceLocation, data, Xmlusers.class, params);
            }

            if (!file.delete())
            {
                throw new IllegalStateException("Failed to delete file " + file.getName());
            }

            return new AsyncResult<Integer>(usersNum);
        }
        catch (IOException e)
        {
            logger.error("Failed to process file " + file.getName(), e);
        }
        catch (JAXBException e)
        {
            logger.error("Failed to process file " + file.getName(), e);
        }
        catch ( IllegalStateException e)
        {
            logger.error("Failed to delete file " + file.getName(), e);
        }

        return new AsyncResult<Integer>(0);
    }
}
