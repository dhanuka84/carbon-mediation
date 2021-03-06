/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.inbound.endpoint.persistence.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.inbound.endpoint.persistence.ServiceReferenceHolder;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.utils.ConfigurationContextService;

/**
 * @scr.component name="inbound.endpoint.persistence.service" immediate="true"
 * @scr.reference name="registry.service"
 * interface="org.wso2.carbon.registry.core.service.RegistryService" cardinality="1..1"
 * policy="dynamic" bind="setRegistryService" unbind="unsetRegistryService"
 * @scr.reference name="config.context.service"
 * interface="org.wso2.carbon.utils.ConfigurationContextService" cardinality="1..1"
 * policy="dynamic" bind="setConfigurationContextService" unbind="unsetConfigurationContextService"
 */
public class InboundEndpointPersistenceServiceDSComponent {

    private static final Log log = LogFactory.getLog(InboundEndpointPersistenceServiceDSComponent.class);

    private static ConfigurationContextService configContextService = null;
    
    protected void activate(ComponentContext ctx) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Activating Inbound Endpoint Persistence service....!");
        }
        BundleContext bndCtx = ctx.getBundleContext();
        bndCtx.registerService(InboundEndpointPersistenceService.class.getName(),
                               new InboundEndpointPersistenceServiceImpl(), null);

    }

    protected void deactivate(ComponentContext compCtx) throws Exception {
    }

    protected void setRegistryService(RegistryService registryService) {
        if (log.isDebugEnabled()) {
            log.debug("RegistryService bound to the inbound endpoint persistence service");
        }
        ServiceReferenceHolder.getInstance().setRegistrySvc(registryService);
    }

    protected void unsetRegistryService(RegistryService registryService) {
        if (log.isDebugEnabled()) {
            log.debug("RegistryService unbound from the inbound endpoint persistence service");
        }
        ServiceReferenceHolder.getInstance().setRegistrySvc(null);
    }

    protected void setConfigurationContextService(ConfigurationContextService contextService) {
        configContextService = contextService;
    }

    protected void unsetConfigurationContextService(ConfigurationContextService contextService) {        
        configContextService = null;
    }    
    
    public static ConfigurationContextService getConfigContextService(){
   	 return configContextService;
    }
    
}
