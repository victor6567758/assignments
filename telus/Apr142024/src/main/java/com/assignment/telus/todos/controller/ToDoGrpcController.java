package com.assignment.telus.todos.controller;

import com.assignment.telus.todos.dto.Completion;
import com.assignment.telus.todos.dto.ToDoDtoResponse;
import com.assignment.telus.todos.dto.TodoDtoRequest;
import com.assignment.telus.todos.model.Dummy;
import com.assignment.telus.todos.model.ToDoCompletion;
import com.assignment.telus.todos.model.ToDoResponse;
import com.assignment.telus.todos.model.ToDoResponseList;
import com.assignment.telus.todos.model.ToDoServiceGrpc;
import com.assignment.telus.todos.model.TodoCreateRequest;
import com.assignment.telus.todos.model.TodoDeleteRequest;
import com.assignment.telus.todos.model.TodoFindRequest;
import com.assignment.telus.todos.model.TodoUpdateRequest;
import com.assignment.telus.todos.service.TodoService;
import io.grpc.stub.StreamObserver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class ToDoGrpcController extends ToDoServiceGrpc.ToDoServiceImplBase {

  private final TodoService todoService;

  @Override
  public void findById(TodoFindRequest request,
      StreamObserver<ToDoResponse> responseObserver) {

    ToDoDtoResponse toDoDtoResponse = todoService.findById(request.getId());

    responseObserver.onNext(toRpcToDoResponse(toDoDtoResponse));
    responseObserver.onCompleted();
  }

  @Override
  public void findAll(Dummy request,
      StreamObserver<ToDoResponseList> responseObserver) {

    List<ToDoDtoResponse> listDtoResponse = todoService.findAll();

    ToDoResponseList.Builder builder = ToDoResponseList.newBuilder();
    listDtoResponse.stream().map(ToDoGrpcController::toRpcToDoResponse).forEach(builder::addList);


    ToDoResponseList toDoResponseList = builder.build();
    responseObserver.onNext(toDoResponseList);
    responseObserver.onCompleted();
  }

  @Override
  public void create(TodoCreateRequest todoCreateRequest, StreamObserver<ToDoResponse> responseObserver) {

    ToDoDtoResponse toDoDtoResponse = todoService.create(new TodoDtoRequest(todoCreateRequest.getDescription(),
        fromRpcCompletion(todoCreateRequest.getCompletion())));

    responseObserver.onNext(toRpcToDoResponse(toDoDtoResponse));
    responseObserver.onCompleted();
  }

  @Override
  public void update(TodoUpdateRequest todoUpdateRequest, StreamObserver<ToDoResponse> responseObserver) {
    ToDoDtoResponse toDoDtoResponse = todoService.update(todoUpdateRequest.getId(),
        new TodoDtoRequest(todoUpdateRequest.getTodoCreateRequest().getDescription(),
        fromRpcCompletion(todoUpdateRequest.getTodoCreateRequest().getCompletion())));

    responseObserver.onNext(toRpcToDoResponse(toDoDtoResponse));
    responseObserver.onCompleted();

  }

  @Override
  public void delete(TodoDeleteRequest todoDeleteRequest,
      StreamObserver<Dummy> responseObserver) {

    todoService.delete(todoDeleteRequest.getId());
    responseObserver.onCompleted();
  }

  @Override
  public void clear(Dummy request,
      StreamObserver<Dummy> responseObserver) {

    todoService.clear();
    responseObserver.onCompleted();
  }


  private static ToDoResponse toRpcToDoResponse(ToDoDtoResponse toDoDtoResponse) {
    return ToDoResponse.newBuilder()
        .setDescription(toDoDtoResponse.getDescription())
        .setCompletion(toRpcCompletion(toDoDtoResponse.getCompletion()))
        .setId(toDoDtoResponse.getId()).build();
  }

  private static ToDoCompletion toRpcCompletion(Completion completion) {
    return completion == Completion.NOT_COMPLETED ?
        ToDoCompletion.NOT_COMPLETED :
        ToDoCompletion.COMPLETED;
  }

  private static Completion fromRpcCompletion(ToDoCompletion completion) {
    return completion == completion.COMPLETED  ? Completion.COMPLETED : Completion.NOT_COMPLETED;
  }
}
