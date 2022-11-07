/* Написать ResourceServer, который будет содержать ссылку на TestResource.
Вывести ResourceServer в JMX с именем:
Admin:type=ResourceServerController
Сделать переменные "name" и "age" доступными для чтения из jmx клиента.

Написать сервлет, который будет обрабатывать запросы на /resources.
При получении POST запроса с параметром path=path_to_resource сервлет прочитает ресурс TestResource из
указанного в параметре файла и сохранит ссылку в ResourceService.
После чтения, значения name и age должны быть доступны по JMX.

Во время проверки тестовая система:
-запустит ваш сервер;
-подождет пока "Server started";
- создаст файл resource.xml в локальной файловой системе;
-пришлет по POST запросу имя этого файла в сервер;
проверит через JMX, что значение name и age совпадают с записанными.
  */




package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resources.ResourceServer;
import resources.TestResource;
import servlets.Servlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        ResourceServer resourceServer = new ResourceServer(new TestResource("Lika", 32));

        //получаем экземпляр MBeanServer, который реализует интерфейс MBeanServer
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        //создаем наш MBean и регистрируем его на платформе MBeanServer
        ObjectName name = new ObjectName("Admin:type=ResourceServerController");
        mBeanServer.registerMBean(resourceServer, name);

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new Servlet(resourceServer)), Servlet.PAGE_URL);
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{context});
        server.setHandler(handlers);

        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();

    }
}
