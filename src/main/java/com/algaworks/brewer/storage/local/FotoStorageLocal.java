package com.algaworks.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

public class FotoStorageLocal implements FotoStorage {

	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);

	private Path localTemporario;
	private Path local;

	public FotoStorageLocal() {
		// this(getDefault().getPath(System.getenv("HOME"), ".brewerfotos")); Linux
		this(getDefault().getPath(System.getenv("HOMEDRIVE"), "/brewerfotos"));
	}

	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String nomeNovo = "";
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			nomeNovo = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(
						this.localTemporario.toAbsolutePath().toString() + getDefault().getSeparator() + nomeNovo));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando a fot na pasta temporária", e);
			}
		}
		return nomeNovo;
	}

	public Path getLocalTemporario() {
		return localTemporario;
	}

	public void setLocalTemporario(Path localTemporario) {
		this.localTemporario = localTemporario;
	}

	public Path getLocal() {
		return local;
	}

	public void setLocal(Path local) {
		this.local = local;
	}

	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);

			if (logger.isDebugEnabled()) {
				logger.debug("Pastas criadas para salvar fotos.");
				logger.debug("Pasta default: " + this.local.toAbsolutePath());
				logger.debug("Pasta default: " + this.localTemporario.toAbsolutePath());
			}

		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}

	}

	private String renomearArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}

}
