package br.com.proenix.toolsChallenge.dto.general;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDto<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
