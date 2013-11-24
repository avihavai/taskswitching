package taskswitching.view;

import java.util.List;

public class DataTablesOutputVO {

    private List<AggregateResultVO> aaData;

    public DataTablesOutputVO() {
    }

    public List<AggregateResultVO> getAaData() {
        return aaData;
    }

    public void setAaData(List<AggregateResultVO> aaData) {
        this.aaData = aaData;
    }
}
