package com.example.demo.dto;

import com.example.demo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

    // 폼 데이터를 받아오는 DTO

    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity entity){ // 퍼시스턴스 -> 컨트롤러로 넘어올때 생성

        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDTO todoDTO){ // 컨트롤러 -> 퍼시스턴스
        return TodoEntity.builder()
                .id(todoDTO.getId())
                .title(todoDTO.getTitle())
                .done(todoDTO.isDone())
                .build();
    }



}
