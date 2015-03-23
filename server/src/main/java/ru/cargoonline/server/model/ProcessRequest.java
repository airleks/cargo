package ru.cargoonline.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "process_requests")
public class ProcessRequest {

    @Id
    private String id;

    @Column(name = "processed")     // since 'offset' is not suitable name for db column
    private Integer offset = 0;

    public ProcessRequest() {
    }

    public ProcessRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessRequest that = (ProcessRequest) o;

        if (!id.equals(that.id)) return false;
        if (!offset.equals(that.offset)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + offset.hashCode();
        return result;
    }
}
