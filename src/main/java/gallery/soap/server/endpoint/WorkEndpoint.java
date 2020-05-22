package gallery.soap.server.endpoint;

import gallery.soap.server.model.Work;
import gallery.soap.server.repository.WorkRepository;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import service.soap.gallery.GetWorkRequest;
import service.soap.gallery.GetWorkResponse;
import service.soap.gallery.ObjectFactory;

import javax.annotation.Resource;

@Endpoint
public class WorkEndpoint {

    private static final String NAMESPACE_URI = "http://gallery.soap.service";

    @Resource
    private WorkRepository workRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWorkRequest")
    @ResponsePayload
    public GetWorkResponse getWork(@RequestPayload GetWorkRequest request) {
        ObjectFactory objectFactory = new ObjectFactory();
        GetWorkResponse response = objectFactory.createGetWorkResponse();

        Work workByTitle = workRepository.findWorkByTitle(request.getTitle());

        service.soap.gallery.Work objectFactoryWork = objectFactory.createWork();
        objectFactoryWork.setId((int) workByTitle.getId());
        objectFactoryWork.setTitle(workByTitle.getTitle());
        response.setWork(objectFactoryWork);
        return response;
    }

}
