package iit.talon.model;
import org.springframework.data.annotation.Id;

public class Transcript {
    @Id
    public String id;
    public String transscript;

    public Transcript(String id, String transscript) {
        this.id = id;
        this.transscript = transscript;
    }

    @Override
    public String toString() {
        return String.format(
                "Transcript[id=%s, text='%s']",
                id, transscript);
    }
}