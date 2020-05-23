package gallery.soap.server.endpoint;

import gallery.soap.server.model.Work;
import gallery.soap.server.repository.WorkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import service.soap.gallery.*;

import javax.annotation.Resource;

@Endpoint
public class WorkEndpoint {

    private static final String NAMESPACE_URI = "http://gallery.soap.service";

    private ObjectFactory objectFactory;

    @Autowired
    public WorkEndpoint(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Resource
    private WorkRepository workRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWorkRequest")
    @ResponsePayload
    public GetWorkResponse getWork(@RequestPayload GetWorkRequest request) {

        GetWorkResponse response = objectFactory.createGetWorkResponse();

        Work workByTitle = workRepository.findWorkByTitle(request.getTitle());

        service.soap.gallery.Work workDto = objectFactory.createWork();
        workDto.setId((int) workByTitle.getId());
        workDto.setTitle(workByTitle.getTitle());
        response.setWork(workDto);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addWorkRequest")
    @ResponsePayload
    public AddWorkResponse addWork(@RequestPayload AddWorkRequest request) {

        AddWorkResponse response = objectFactory.createAddWorkResponse();

        Work workToSave = new Work(request.getWork().getTitle());

        long savedWorkId = workRepository.save(workToSave);
        response.setId(savedWorkId);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateWorkRequest")
    @ResponsePayload
    public UpdateWorkResponse updateWork(@RequestPayload UpdateWorkRequest request) {

        UpdateWorkResponse response = objectFactory.createUpdateWorkResponse();

        Work work = new Work(request.getWork().getId(), request.getWork().getTitle());

        work = workRepository.update(work);
        service.soap.gallery.Work workDto = new service.soap.gallery.Work();

        workDto.setTitle(work.getTitle());
        workDto.setId(work.getId());

        response.setWork(workDto);

        return response;
    }

}
