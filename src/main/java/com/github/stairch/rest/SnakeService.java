package com.github.stairch.rest;

import com.github.stairch.dtos.*;
import com.github.stairch.types.HeadType;
import com.github.stairch.types.Move;
import com.github.stairch.types.TailType;
import com.google.gson.*;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.lang.reflect.Type;

import static com.github.stairch.RestInPeace.BASE_URI;

@Path("/")
public class SnakeService {

    /**
     * Used for json serialization/deserialization.
     */
    private final Gson gson = new Gson();
    //private Gamearea gamearea;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "yeaay, your starter snake is up and running :)";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/start")
    public final Response start(final StartRequestDTO startRequestDTO) {
        System.out.println(startRequestDTO);

        /*
        if(gamearea == null){
            gamearea = new Gamearea(startRequestDTO.getHeight(), startRequestDTO.getWidth());
        }
        */

        final StartResponseDTO startResponse = new StartResponseDTO();
        startResponse.setColor("black");
        startResponse.setHeadUrl(BASE_URI + "static/head.png");
        startResponse.setName("STAIR Java Snake");
        startResponse.setTaunt("Meep meep");

        startResponse.setHeadType(HeadType.getPixel());
        startResponse.setTailType(TailType.getBlockbum());
        final String responseBody = gson.toJson(startResponse);
        return Response.status(Response.Status.OK).entity(responseBody).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/move")
    public final Response move(final String startRequestDTO) {
        //System.out.println(startRequestDTO);
        /*
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(startRequestDTO);
        //String bla = gson.toJson(startRequestDTO);
        System.out.println(jo);
*/

        GsonBuilder gson_builder = new GsonBuilder();
        gson_builder.registerTypeAdapter(
                JsonElement.class,
                new JsonDeserializer<JsonElement>() {
                    @Override
                    public JsonElement deserialize(JsonElement arg0,
                                                   Type arg1,
                                                   JsonDeserializationContext arg2)
                            throws JsonParseException {

                        return arg0;
                    }
                } );

        Gson gson = gson_builder.create();
        JsonElement element = gson.fromJson(startRequestDTO, JsonElement.class);
        JsonObject object = element.getAsJsonObject();
        System.out.println(object);

        JsonElement snakes = object.get("snakes");
        if(snakes!= null){
            JsonElement bla = snakes.getAsJsonArray().get(Integer.parseInt("coords"));

            //JsonElement coords = jsonArray.getAsJsonObject().get("coords");


        }

        final MoveResponseDTO moveResponse = new MoveResponseDTO();
        moveResponse.setMove(Move.left);
/*
        Gamearea gamearea = new Gamearea(startRequestDTO.getHeight(), startRequestDTO.getWidth());
        gamearea.initGameField();

*/
        final String responseBody = gson.toJson(moveResponse);
        return Response.status(Response.Status.OK).entity(responseBody).build();
    }
}