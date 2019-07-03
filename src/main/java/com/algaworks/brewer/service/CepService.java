package com.algaworks.brewer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.algaworks.brewer.dto.CepDTO;
import com.algaworks.brewer.exceptions.CepException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class CepService {

	@Value("${api.cep}")
	private String url;

	public CepDTO buscaCep(String cep) {

		CepDTO cepDto = null;

		try {

			String cepAux = cep.split("=")[1].replaceAll("\\.|-|", "");

			RestTemplate rest = new RestTemplate();
			ResponseEntity<String> response = rest.getForEntity(url + cepAux, String.class);

			Gson gson = new GsonBuilder().create();
			cepDto = gson.fromJson(response.getBody(), CepDTO.class);

		} catch (HttpClientErrorException e1) {
			throw new CepException("CEP não existe ");
		} catch (Exception e) {
			throw new CepException("Falha ao buscar CEP ");
		}

		return cepDto;

	}

}
