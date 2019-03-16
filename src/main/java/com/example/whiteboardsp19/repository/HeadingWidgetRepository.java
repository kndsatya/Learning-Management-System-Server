package com.example.whiteboardsp19.repository;

import com.example.whiteboardsp19.model.HeadingWidget;
import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.Topic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeadingWidgetRepository extends CrudRepository<HeadingWidget,Integer> {

  @Query("SELECT headingWidget FROM HeadingWidget headingWidget WHERE headingWidget.topic=:topic")
  public List<HeadingWidget> findAllHeadingWidgets(@Param("topic") Topic topic);

}
