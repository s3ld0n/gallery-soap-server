package gallery.soap.server.repository;

import gallery.soap.server.model.Work;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component(value = "courseRepository")
public class WorkRepository {

    private List<Work> repository = Stream
                                            .of(new Work(1L, "Joy"),
                                                new Work(2L, "Happiness"),
                                                new Work(3L, "Fear"))
                                            .collect(Collectors.toList());

    public Work findWorkByTitle(String title) {
        return repository
                       .stream()
                       .filter(work -> work.getTitle().equals(title))
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException(title));
    }

    public long save(Work work) {
        long id = repository.get(repository.size() - 1).getId() + 1;

        work.setId(id);
        repository.add(work);
        return id;
    }

    public Work update(Work work) {
        Optional<Work> optionalWork = repository.stream().filter(work1 -> work1.getId() == work.getId()).findFirst();
        if (optionalWork.isPresent()) {
            repository.set(repository.indexOf(optionalWork.get()), work);

        } else{
            repository.add(work);
        }

        return work;
    }
}
