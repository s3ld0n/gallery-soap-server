package gallery.soap.server.repository;

import gallery.soap.server.model.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component(value = "courseRepository")
public class WorkRepository {

    Logger LOG = LoggerFactory.getLogger(WorkRepository.class);

    private List<Work> repository = Stream.of(
                                            new Work(1L, "Goal"),
                                            new Work(2L, "Motivation"),
                                            new Work(3L, "Courage"))
                                          .collect(Collectors.toList());

    public Work findWorkByTitle(String title) {
        return repository
                       .stream()
                       .filter(work -> work.getTitle().equals(title))
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException(title));
    }

    public long save(Work work) {
        LOG.debug("getting id from Work");
        long id = repository.get(repository.size() - 1).getId() + 1;

        work.setId(id);
        repository.add(work);
        return id;
    }
}
