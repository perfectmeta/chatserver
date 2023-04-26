package util;

import java.util.List;
import java.util.stream.Collectors;

public interface RecordBuilder<T> {
    T build();

    default <T1, E extends RecordBuilder<T1>> List<T1> arrayBuild(List<E> listBuilders) {
        return listBuilders.stream().map(RecordBuilder::build).collect(Collectors.toList());
    }
}
