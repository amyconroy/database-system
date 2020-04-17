package Commands;
import java.util.List;

public interface Command {
    void preformCommand(List<String> Query);
}
