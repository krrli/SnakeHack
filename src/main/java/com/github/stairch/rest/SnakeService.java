package com.github.stairch.rest;

import com.github.stairch.data.Gamearea;
import com.github.stairch.data.PlayerDestinationBundle;
import com.github.stairch.dtos.*;
import com.github.stairch.logic.NextMoveFinder;
import com.github.stairch.logic.PathFinder;
import com.github.stairch.types.HeadType;
import com.github.stairch.types.Move;
import com.github.stairch.types.TailType;
import com.google.gson.*;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.awt.*;
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



        final int[] meX = {0};
        final int[] meY = {0};

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
        JsonObject moveRequest = element.getAsJsonObject();
        System.out.println(moveRequest);

        Gamearea gamearea = new Gamearea(moveRequest.get("height").getAsInt(), moveRequest.get("width").getAsInt());
        gamearea.initGameField();



        JsonElement snakes = moveRequest.get("snakes");
        if(snakes != null){

            JsonArray snakesAsJsonArray = snakes.getAsJsonArray();
            snakesAsJsonArray.forEach(s -> {
                JsonArray coords = s.getAsJsonObject().getAsJsonArray("coords");
                coords.forEach(c -> {
                    JsonArray asJsonArray = c.getAsJsonArray();
                    int x = asJsonArray.get(0).getAsInt();
                    int y = asJsonArray.get(1).getAsInt();

                    if(meX[0] == 0)
                        meX[0] = x;
                    if(meY[0] == 0)
                        meY[0] = y;
                    gamearea.getField(x,y).setIsBusy(true);
                });
            });
        }

        Point destination = new Point(gamearea.getGameArea().length, gamearea.getGameArea()[0].length);
        Point currentPosition = new Point();
        currentPosition.setLocation(meX[0], meY[0]);


        JsonElement food = moveRequest.get("food");
        if(food != null){

            JsonArray foodAsJsonArray = food.getAsJsonArray();
            foodAsJsonArray.forEach(f -> {
                JsonArray foods = f.getAsJsonArray();
                int foodX = foods.get(0).getAsInt();
                int foodY = foods.get(1).getAsInt();
                //if(this.isCloserToCurrentPosition(currentPosition, destination, new Point(foodX, foodY))){
                    destination.setLocation(foodX,foodY);
                //}
            }
            );
        }

        JsonElement deadSnake = moveRequest.get("dead_snakes");
        if(deadSnake != null){
            JsonArray deadSnakesAsJsonArray = deadSnake.getAsJsonArray();
            deadSnakesAsJsonArray.forEach(d ->{
                JsonArray deadSnakes = d.getAsJsonArray();

            });
        }

        NextMoveFinder nextMoveFinder = new NextMoveFinder(new PathFinder());
        Move move = nextMoveFinder.findNextMove(gamearea.getGameArea(), new PlayerDestinationBundle(currentPosition, destination));

        final MoveResponseDTO moveResponse = new MoveResponseDTO();
        moveResponse.setMove(move);


        final String responseBody = gson.toJson(moveResponse);
        return Response.status(Response.Status.OK).entity(responseBody).build();
    }

    private boolean isCloserToCurrentPosition(Point currentPosition, Point destination, Point newDestination) {
        int distanceOfCurrentDestination = this.getDistanceTo(destination, currentPosition);
        int distanceOfNewDestination = this.getDistanceTo(newDestination, currentPosition);

        return distanceOfNewDestination < distanceOfCurrentDestination;
    }

    private int getDistanceTo(Point destination, Point currentPosition) {
        int xDiff = (int) Math.abs(currentPosition.getX() - destination.getX());
        int yDiff = (int) Math.abs(currentPosition.getY() - destination.getY());

        return xDiff + yDiff;
    }
}