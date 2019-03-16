package com.example.whiteboardsp19.repository;

import com.example.whiteboardsp19.model.ListWidget;
import com.example.whiteboardsp19.model.ParagraphWidget;
import com.example.whiteboardsp19.model.Topic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParagraphWidgetRepository extends CrudRepository<ParagraphWidget,Integer> {

  @Query("SELECT paragraphWidget FROM ParagraphWidget paragraphWidget WHERE paragraphWidget.topic=:topic")
  public List<ParagraphWidget> findAllParagraphWidgets(@Param("topic") Topic topic);

}
