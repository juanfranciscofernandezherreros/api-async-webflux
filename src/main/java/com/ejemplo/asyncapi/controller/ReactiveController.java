package com.ejemplo.asyncapi.controller;

import com.ejemplo.asyncapi.model.DatosEntrada;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/async")
public class ReactiveController {

    private static final int TIEMPO_ESPERA_SEGUNDOS = 3;

    @GetMapping("/health")
    public Mono<String> health() {
        return Mono.just("✓ API Asíncrona está funcionando correctamente");
    }

    @PostMapping("/procesar_datos")
    public Mono<String> procesarDatos(@RequestBody DatosEntrada datos) {
        System.out.printf("[%s] ⏱️  Petición recibida para: %s. Hilo: %s\n",
                LocalTime.now(), datos.getNombre(), Thread.currentThread().getName());

        int resultadoCalculado = datos.getDatosNumericos() * 2;

        Mono<Void> retardoNoBloqueante = Mono.delay(Duration.ofSeconds(TIEMPO_ESPERA_SEGUNDOS))
                .doOnSuccess(v -> System.out.printf("[%s] ⏳ Retardo completado para: %s\n",
                        LocalTime.now(), datos.getNombre())).then();

        return retardoNoBloqueante
                .then(Mono.just(resultadoCalculado))
                .map(resultado -> {
                    System.out.printf("[%s] ✅ Petición completada para: %s. Hilo: %s\n",
                            LocalTime.now(), datos.getNombre(), Thread.currentThread().getName());
                    return String.format("Procesamiento completado para %s. Resultado: %d", datos.getNombre(), resultado);
                });
    }

    @PostMapping("/procesar_rapido")
    public Mono<String> procesarRapido(@RequestBody DatosEntrada datos) {
        System.out.printf("[%s] 🚀 Petición rápida recibida para: %s. Hilo: %s\n",
                LocalTime.now(), datos.getNombre(), Thread.currentThread().getName());

        int resultadoCalculado = datos.getDatosNumericos() * 2;

        return Mono.just(String.format("Procesamiento rápido completado para %s. Resultado: %d", 
                datos.getNombre(), resultadoCalculado));
    }

    @PostMapping("/procesar_delay")
    public Mono<String> procesarConDelay(@RequestBody DatosEntrada datos, 
                                         @RequestParam(defaultValue = "3") int segundos) {
        System.out.printf("[%s] ⏱️  Petición con delay de %d segundos para: %s. Hilo: %s\n",
                LocalTime.now(), segundos, datos.getNombre(), Thread.currentThread().getName());

        int resultadoCalculado = datos.getDatosNumericos() * 2;

        return Mono.delay(Duration.ofSeconds(segundos))
                .then(Mono.just(String.format("Procesamiento completado para %s después de %d segundos. Resultado: %d", 
                        datos.getNombre(), segundos, resultadoCalculado)));
    }
}
