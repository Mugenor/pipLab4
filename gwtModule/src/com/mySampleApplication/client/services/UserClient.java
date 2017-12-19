package com.mySampleApplication.client.services;

import com.mySampleApplication.client.data.*;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/rest/user")
public interface UserClient extends RestService{
    @POST
    @Path("/registration")
    void register(User user, MethodCallback<Status> callback);

    @POST
    @Path("/login")
    void loginIn(User user, MethodCallback<Data> callback);

    @POST
    @Path("/add")
    void addPoint(UserPoint point, MethodCallback<Status> callback);
}
