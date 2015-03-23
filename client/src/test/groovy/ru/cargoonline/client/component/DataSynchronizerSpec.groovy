package ru.cargoonline.client.component

import groovy.mock.interceptor.MockFor
import org.springframework.boot.test.TestRestTemplate
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.web.client.RestTemplate
import ru.cargoonline.common.generated.Xmlusers
import spock.lang.Shared
import spock.lang.Specification

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import java.util.concurrent.Future

class DataSynchronizerSpec extends Specification
{
    @Shared
    def dataDir = System.getProperty('java.io.tmpdir');     // expect this folder will be always available

    @Shared
    def batchSize = 10;

    @Shared
    def host = 'localhost';

    @Shared
    def port = 8080;


    File testFolder
    Marshaller m

    //def rest = new TestRestTemplate()
    def dataSynchronizer

    def setup()
    {
        testFolder = new File(new File(dataDir), 'DataSynchronizerSpec')
        if (!testFolder.exists()) assert testFolder.mkdir()
        dataSynchronizer = new DataSynchronizer(dataDir: testFolder, batchSize: batchSize, host: host, port: port)     // not thread-safe

        JAXBContext context = JAXBContext.newInstance(Xmlusers.class)
        m = context.createMarshaller()

    }

    def 'test data sync of empty folder'()
    {
        given:
            testFolder.listFiles().each { file -> file.delete() }
        when:
            dataSynchronizer.syncData()
        then:
            testFolder.list().length == 0
    }

    def 'test data sync of non-exsisting folder'()
    {
        given:
            dataSynchronizer = new DataSynchronizer(dataDir:  new File(new File(dataDir), "${System.currentTimeMillis()}" ), batchSize: batchSize, host: host, port: port)     // not thread-safe
        when:
            dataSynchronizer.syncData()
        then:
            thrown(IllegalStateException)
    }

    def 'test data sync of single file'()
    {
        given:
            testFolder.listFiles().each { file -> file.delete() }

            File file = new File(testFolder, 'test.xml')
            assert file.createNewFile()
            m.marshal(new Xmlusers(), file)
        when:
            dataSynchronizer.syncData()
        then:
            testFolder.list().length == 0
    }

    def 'test data sync of number of files'()
    {
        given:
            def num = 5
            testFolder.listFiles().each { file -> file.delete() }

            num.times { count ->
                File file = new File(testFolder, "test$count")
                assert file.createNewFile()
                m.marshal(new Xmlusers(), file)
            }
        when:
            dataSynchronizer.syncData()
        then:
            testFolder.list().length == 0
    }

    def 'test send data from valid file'()
    {
        given:
            File file = new File(testFolder, 'test.xml')
            assert file.createNewFile()
            m.marshal(new Xmlusers(), file)
        when:
            Future<Integer> result = dataSynchronizer.sendData(file)
        then:
            result.get() == 0
    }

    def 'test send data from non-exsisting file'()
    {
        given:
            File file = new File(testFolder, 'nonexsisting.xml')
            assert !file.exists()
        when:
            Future<Integer> result = dataSynchronizer.sendData(file)
        then:
            result.get() == 0
    }

    def 'test send data from null file'()
    {
        when:
            dataSynchronizer.sendData(null)
        then:
            thrown(IllegalArgumentException)
    }

    def 'test send data from empty file'()
    {
        given:
            File file = new File(testFolder, 'test.xml')
            assert file.createNewFile()
        when:
            Future<Integer> result = dataSynchronizer.sendData(file)
        then:
            result.get() == 0
        cleanup:
            file.delete()
    }

    def 'test send data from invalid file'()
    {
        given:
            File file = new File(testFolder, 'test.xml')
            assert file.createNewFile()
            file.write('invalid file data')
        when:
            Future<Integer> result = dataSynchronizer.sendData(file)
        then:
            result.get() == 0
        cleanup:
            file.delete()
    }
}
