package gallery.soap.server.model;

public class Work {

    private Long id;
    private String title;

    public Work(String title) {
        this.title = title;
    }

    public Work(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
