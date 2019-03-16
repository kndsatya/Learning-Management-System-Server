package com.example.whiteboardsp19.repository;

import com.example.whiteboardsp19.model.HeadingWidget;
import com.example.whiteboardsp19.model.ImageWidget;
import com.example.whiteboardsp19.model.Topic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageWidgetRepository extends CrudRepository<ImageWidget,Integer> {

  @Query("SELECT imageWidget FROM ImageWidget imageWidget WHERE imageWidget.topic=:topic")
  public List<ImageWidget> findAllImageWidgets(@Param("topic") Topic topic);
}
