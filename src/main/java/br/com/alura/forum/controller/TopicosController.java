package br.com.alura.forum.controller;

import br.com.alura.forum.CursoRepository;
import br.com.alura.forum.TopicoRepository;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    TopicoRepository topicoRepository;
    @Autowired
    br.com.alura.forum.CursoRepository CursoRepository;

  @GetMapping
    public List<TopicoDto> lista(String nomeCurso){
        if (nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        }else {
            List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicoDto.converter(topicos);
        }
    }
    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter();
        topicoRepository.save(topico);
        URI URI = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        java.net.URI uri = null;
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }
}
