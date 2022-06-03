package org.example.module_1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.module_client.request.Module1Request;
import org.example.module_core.PrinterUtil;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiGatewayRequestHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    PrinterUtil printerUtil = new PrinterUtil();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(
            APIGatewayProxyRequestEvent event, Context context) {

        try {
            Module1Request request = new ObjectMapper().readValue(event.getBody(), Module1Request.class);
            printerUtil.print(request.getKey(), request.getValue());
        } catch (JsonProcessingException e) {
            System.out.println("An error has occurred!");
        }

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);
        response.setBody("Everything OK");

        return response;
    }

}
