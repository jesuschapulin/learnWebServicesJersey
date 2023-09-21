package com.freeLearn;

import com.freeLearn.service.MessageService;


import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import com.freeLearn.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/service")
public class Service {
    controller API;
    public Service(){
        
        API=new controller();
    }
    @Inject
    @Named("local")
    private MessageService localBean;

    @Path("/hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return localBean.getHello();
    }
////////servicio para consumir una busuqeda de articulos
    @Path("getartsPage/{words}/{size}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getartsPage(@PathParam("words") String words,@PathParam("size") String size){
            String result="";
            String url="";		   

            try {
                    result = API.getartsPage(words,size);
                    System.out.println("Se consulto API de Doaj en GET:::"+ result);

                    //System.out.println("pagina compuesta::\n"+result);
            } catch (JsonProcessingException e) {
                    System.out.println("fallo al intentar obtener data de API Doaj:::"+ url);
                    e.printStackTrace();
            }catch(Exception ex){
                    System.out.println("error pagina:::"+ url);
                    ex.printStackTrace();
            }

            return Response.ok()
               .entity(result)
               .header("Access-Control-Allow-Origin", "*")
               .build();
    }
}
