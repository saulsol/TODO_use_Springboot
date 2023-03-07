package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.todoRepository.TodoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TodoService {


    private TodoRepository todoRepository;
    // 생성
    public List<TodoEntity> create( final TodoEntity entity ){
        
        // 검증
        validate(entity);

        todoRepository.save(entity);
        log.info("Entity Id : {} is saved", entity.getId());

        return todoRepository.findByUserId(entity.getUserId());

    }
    // 조회
    public List<TodoEntity> retrieve(final String userId){
        return todoRepository.findByUserId(userId);
    }
    
    // 업데이트
    public List<TodoEntity> update(final TodoEntity entity){

        validate(entity);

        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());
        original.ifPresent( todo -> {

            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            todoRepository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    // 삭제
    public List<TodoEntity> delete(final TodoEntity entity){

        validate(entity);

      try {
          todoRepository.delete(entity);

      }catch (Exception e){
          log.error("error deleting entity ", entity.getId(), e);

          throw new RuntimeException("error deleting entity " + entity.getId());
      }

      return retrieve(entity.getUserId());

    }



    private void validate( final TodoEntity entity){
        // 검증 : 넘어온 엔티티가 유효한지 검사하는 로직
        if(entity == null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }

    }


}
