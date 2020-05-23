package gallery.soap.server.endpoint;

import gallery.soap.server.model.Work;
import gallery.soap.server.repository.WorkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import service.soap.gallery.*;

import javax.annotation.Resource;

@Endpoint
public class WorkEndpoint {

    Logger LOG = LoggerFactory.getLogger(WorkEndpoint.class);

    private static final String NAMESPACE_URI = "http://gallery.soap.service";

    @Resource
    private WorkRepository workRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWorkRequest")
    @ResponsePayload
    public GetWorkResponse getWork(@RequestPayload GetWorkRequest request) {

        LOG.info("inside getWork");
        ObjectFactory objectFactory = new ObjectFactory();
        GetWorkResponse response = objectFactory.createGetWorkResponse();

        Work workByTitle = workRepository.findWorkByTitle(request.getTitle());

        service.soap.gallery.Work objectFactoryWork = objectFactory.createWork();
        objectFactoryWork.setId((int) workByTitle.getId());
        objectFactoryWork.setTitle(workByTitle.getTitle());
        response.setWork(objectFactoryWork);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addWorkRequest")
    @ResponsePayload
    public AddWorkResponse addWork(@RequestPayload AddWorkRequest request) {

        ObjectFactory objectFactory = new ObjectFactory();

        LOG.debug("Creating AddWorkResponse");
        AddWorkResponse response = objectFactory.createAddWorkResponse();

        Work workToSave = new Work(request.getWork().getTitle());

        LOG.debug("Saving work in repository");
        long savedWorkId = workRepository.save(workToSave);
        response.setId(savedWorkId);

        LOG.debug("returning response");
        return response;
    }

}
