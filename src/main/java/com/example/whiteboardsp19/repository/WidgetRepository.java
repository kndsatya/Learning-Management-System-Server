package com.example.whiteboardsp19.repository;

import com.example.whiteboardsp19.model.ParagraphWidget;
import com.example.whiteboardsp19.model.Topic;
import com.example.whiteboardsp19.model.Widget;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.transaction.Transactional;

public interface WidgetRepository extends CrudRepository<Widget,Integer> {

  @Query("SELECT widget FROM Widget widget WHERE widget.topic.id =:#{#topic.id}")
  public List<Widget> findWidgetsByTopicId(@Param("topic") Topic topic);

}
