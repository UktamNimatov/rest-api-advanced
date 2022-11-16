package com.epam.esm.dto;

import com.epam.esm.entity.Order;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class UserDto extends RepresentationModel<UserDto> {

    private long id;
    private String name;
    private List<Order> orderList;

    public UserDto() {
    }

    public UserDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        if (!super.equals(o)) return false;
        UserDto userDto = (UserDto) o;
        return getId() == userDto.getId() &&
                Objects.equals(getName(), userDto.getName()) &&
                Objects.equals(getOrderList(), userDto.getOrderList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getName(), getOrderList());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
