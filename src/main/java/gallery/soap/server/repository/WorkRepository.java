package gallery.soap.server.repository;

import gallery.soap.server.model.Work;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Component(value = "courseRepository")
public class WorkRepository {

    private List<Work> repository = Arrays.asList(new Work(1L, "Goal"),
                                                  new Work(2L, "Motivation"),
                                                  new Work(3L, "Courage"));

    public Work findWorkByTitle(String title) {
        return repository
                   .stream()
                   .filter(work -> work.getTitle().equals(title))
                   .findFirst()
                   .orElseThrow(() -> new NoSuchElementException(title));
    }
}
