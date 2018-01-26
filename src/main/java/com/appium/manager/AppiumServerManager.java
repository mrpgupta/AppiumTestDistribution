package com.appium.manager;

import com.appium.utils.DevicesByHost;
import com.appium.utils.HostMachineDeviceManager;
import io.appium.java_client.service.local.flags.ServerArgument;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Appium Manager - this class contains method to start and stops appium server
 * To execute the tests from eclipse, you need to set PATH as
 * /usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin in run configuration
 */
public class AppiumServerManager {

    private static final Logger LOGGER = Logger.getLogger(Class.class.getSimpleName());
    RemoteAppiumManager remoteAppiumManager;
    DevicesByHost devicesByHost;

    public AppiumServerManager() throws IOException {
        remoteAppiumManager = new RemoteAppiumManager();
        devicesByHost = HostMachineDeviceManager.getInstance();
    }

    /**
     * start appium with auto generated ports : appium port, chrome port,
     * bootstrap port and device UDID
     * @param host
     */

    private void startAppiumServerSingleSession(String host)
            throws Exception {
        remoteAppiumManager.startAppiumServer(host);
    }

    /**
     * start appium with auto generated ports : appium port, chrome port,
     * bootstrap port and device UDID
     */
    ServerArgument webKitProxy = new ServerArgument() {
        @Override
        public String getArgument() {
            return "--webkit-debug-proxy-port";
        }
    };



    public void startAppiumServer() throws Exception {
        for (String host : devicesByHost.getAllHosts()) {
            startAppiumServerSingleSession(host);
        }
    }

    public void stopAppiumServer() throws IOException, InterruptedException {
        for ( String host: devicesByHost.getAllHosts()) {
            remoteAppiumManager.destroyAppiumNode(host);
        }
    }
}
