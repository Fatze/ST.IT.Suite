package by.st.it.suite.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 *
 */
public class SuitePlugin implements SuitePluginMBean {

    private static final transient Logger LOG = LoggerFactory.getLogger(SuitePlugin.class);

    private String name;
    private String context;
    private String domain;
    private String scripts[];
    private int counter = 0;
    private ObjectName objectName = null;
    private MBeanServer mBeanServer = null;

    public SuitePlugin() {

    }

    public void init() {
        try {
            if (objectName == null) {
                objectName = getObjectName();
                LOG.info("getObjectName " + objectName);
            }

            if (mBeanServer == null) {
                LOG.info("ManagementFactory.getPlatformMBeanServer() starting " + objectName);
                mBeanServer = ManagementFactory.getPlatformMBeanServer();
                LOG.info("ManagementFactory.getPlatformMBeanServer() finished " + objectName);
            }

            LOG.info("mBeanServer.isRegistered(objectName) " + objectName);
            if (mBeanServer.isRegistered(objectName)) {
                // Update of existing plugin
                LOG.info("Unregistering existing plugin " + objectName);
                mBeanServer.unregisterMBean(objectName);
            }

            LOG.debug("Registering plugin " + objectName);
            mBeanServer.registerMBean(this, objectName);

        } catch (Throwable t) {
            LOG.error("Failed to register plugin: ", t);
        }
    }

    public void destroy() {
        try {
            if (mBeanServer != null) {
                LOG.debug("Unregistering plugin " + objectName);
                mBeanServer.unregisterMBean(objectName);
            }
        } catch (Throwable t) {
            LOG.error("Failed to register plugin: ", t);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return this.context;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setScripts(String[] scripts) {
        this.scripts = scripts;
    }

    public void setScripts(String scripts) {
        String[] temp = scripts.split(",");

        for (int i = 0; i > temp.length; i++) {
            temp[i] = temp[i].trim();
        }

        this.scripts = temp;
    }

    public String[] getScripts() {
        return this.scripts;
    }

    public int getCount() {
        return counter;
    }

    protected ObjectName getObjectName() throws MalformedObjectNameException {
//        return new ObjectName("suite:type=plugin");//"javax.management.InstanceNotFoundException : st.by:type=service,name=myService"
        return new ObjectName("suite:type=plugin,name=" + getName());
    }

}
