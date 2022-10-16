package demo.publisherListener.entity;

import lombok.Data;

import java.util.List;

@Data
public class ListWrapperEvent{

	List<SendMessageFlag> data;
}
