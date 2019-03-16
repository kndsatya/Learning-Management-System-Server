package com.example.whiteboardsp19.repository;

import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.Topic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListWidgetRepository extends CrudRepository<ListWidget,Integer> {


  @Query("SELECT listWidget FROM ListWidget listWidget WHERE listWidget.topic=:topic")
  public List<ListWidget> findAllListWidgets(@Param("topic") Topic topic);

}
