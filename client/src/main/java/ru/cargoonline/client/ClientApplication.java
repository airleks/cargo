package ru.cargoonline.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Client application runner/client application spring config.
 */
@Configuration
@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@EnableAutoConfiguration
public class ClientApplication
{
    private static final int PORT = 2222;
    private static ServerSocket clientSocket;

    public static void main(String[] args)
    {
        if (hasRunningInstance())
        {
            System.out.println("Client application is already run");
            return;
        }

        SpringApplication.run(ClientApplication.class, args);
    }

    private static boolean hasRunningInstance()
    {
        try
        {
            clientSocket = new ServerSocket(PORT);
        }
        catch (IOException e)
        {
            return true;
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (!clientSocket.isClosed())
                {
                    try
                    {
                        clientSocket.close();
                    }
                    catch (IOException e)
                    {
                        System.out.println("Failed to close socket");
                        e.printStackTrace();
                    }
                }
            }
        });

        return false;
    }
}
