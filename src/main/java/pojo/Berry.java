package pojo;

import lombok.Data;

import java.util.List;

@Data
public class Berry {

    private int count;
    private String next;
    private String previous;
    private List<BerryDetail> results;

}
