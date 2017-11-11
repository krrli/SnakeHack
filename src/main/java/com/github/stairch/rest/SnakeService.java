package com.github.stairch.rest;

import com.github.stairch.data.Gamearea;
import com.github.stairch.dtos.*;
import com.github.stairch.types.HeadType;
import com.github.stairch.types.Move;
import com.github.stairch.types.TailType;
import com.google.gson.*;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        Gamearea gamearea = new Gamearea(object.get("height").getAsInt(), object.get("width").getAsInt());
        gamearea.initGameField();


        JsonElement snakes = object.get("snakes");
        if(snakes != null){

            JsonArray snakesAsJsonArray = snakes.getAsJsonArray();
            snakesAsJsonArray.forEach(s -> {
                JsonArray coords = s.getAsJsonObject().getAsJsonArray("coords");
                coords.forEach(c -> {
                    JsonArray asJsonArray = c.getAsJsonArray();
                    int x = asJsonArray.get(0).getAsInt();
                    int y = asJsonArray.get(1).getAsInt();
                    gamearea.getField(x,y).setIsBusy(true);
                });
            });
        }

        final MoveResponseDTO moveResponse = new MoveResponseDTO();
        moveResponse.setMove(Move.left);


        final String responseBody = gson.toJson(moveResponse);
        return Response.status(Response.Status.OK).entity(responseBody).build();
    }
}