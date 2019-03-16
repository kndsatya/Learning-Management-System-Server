package com.example.whiteboardsp19.repository;

import com.example.whiteboardsp19.model.LinkWidget;
import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.Topic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinkWidgetRepository extends CrudRepository<LinkWidget,Integer> {


  @Query("SELECT linkWidget FROM LinkWidget linkWidget WHERE linkWidget.topic=:topic")
  public List<LinkWidget> findAllLinkWidgets(@Param("topic") Topic topic);
}
