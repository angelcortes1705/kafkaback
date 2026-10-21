package com.lta.backend.resources;

import com.lta.backend.services.ResponseListener;
import com.lta.backend.services.StringProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private StringProducerService producer;

    @GetMapping("/menu")
    public String sendMenu(@RequestParam String message) throws InterruptedException {
        return sendAndWait(message);
    }

    @GetMapping("/test")
    public String sendTest(@RequestParam String message) throws InterruptedException {
        return sendAndWait(message);
    }

    // NUEVO: consulta de clientes por Kafka
    @GetMapping("/clientes")
    public String consultarCliente(@RequestParam String message) throws InterruptedException {
        ResponseListener.response = null;
        producer.sendClientes(message);
        String respuesta = esperarRespuesta();
        return respuesta != null ? respuesta : "No hubo respuesta del listener de clientes";
    }

    // MÉTODO REUTILIZABLE (flujo original)
    private String sendAndWait(String message) throws InterruptedException {
        ResponseListener.response = null;
        producer.sendMessage(message);
        String respuesta = esperarRespuesta();
        return respuesta != null
                ? respuesta
                : "No response (posiblemente fue a partición 0)";
    }

    // Espera hasta 5 s (10 intentos x 500 ms) a que ResponseListener reciba la respuesta
    private String esperarRespuesta() throws InterruptedException {
        int attempts = 0;
        while (ResponseListener.response == null && attempts < 10) {
            Thread.sleep(500);
            attempts++;
        }
        return ResponseListener.response;
    }
}