package edu.upc.project.services;

import edu.upc.project.GameManager;
import edu.upc.project.GameManagerImpl;
import edu.upc.project.config.CORS;
import edu.upc.project.models.Problema;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Api(value = "/problema", description = "Endpoint to Problema Service")
@Path("/problema")
public class ProblemaService {

    private GameManager gm;

    public ProblemaService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @POST
    @CORS
    @ApiOperation(value = "Añadir un problema")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Problema añadido correctamente"),
            @ApiResponse(code = 400, message = "Datos de entrada no válidos"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProblema(Problema problema){
        if (problema.getFecha() == null ||problema.getMensaje() == null ||problema.getTitulo() == null ) {
            return Response.status(400).build();
        }

        try {
            gm.createProblema(problema.getFecha(), problema.getTitulo(), problema.getMensaje(), problema.getId());
            return Response.status(201).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @OPTIONS
    @Path("/")
    @CORS
    public Response optionsForProblema() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization, MediaType")
                .header("Access-Control-Max-Age", "86400")
                .build();
    }
}